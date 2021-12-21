package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "CustomerController", urlPatterns = "/processCustomer")
public class ResumeServlet extends HttpServlet {
    private SqlStorage storage;
    private boolean isCreate = false;

    @Override
    public void init() {
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> resume = storage.get(uuid);
            case "add" -> {
                resume = Resume.EMPTY;
                isCreate = true;
            }
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT, QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE, EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyOrganizations = new ArrayList<>();
                            emptyOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Position> emptyPositions = new ArrayList<>();
                                    emptyPositions.add(Organization.Position.EMPTY);
                                    emptyPositions.addAll(org.getPositions());
                                    emptyOrganizations.add(new Organization(org.getHomePage(), emptyPositions));
                                }
                            }
                            section = new OrganizationSection(emptyOrganizations);
                            break;
                    }
                    resume.setSection(type, section);
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        String action = request.getParameter("action");

        Resume resume = new Resume(uuid, fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && (values != null && values.length < 2)) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.setSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = List.of(value.split("\r\n"));
                        list = list.stream()
                                .filter(s -> !s.equals(""))
                                .collect(Collectors.toList());
                        resume.setSection(type, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgs = new ArrayList<>();
                        List<Link> links = getLinks(request, type.name());
                        for (int i = 0; i < links.size(); i++) {
                            String name = links.get(i).getName();
                            if (!HtmlUtil.isEmpty(name)) {
                                String prefix = type.name() + "_" + i;
                                List<Organization.Position> positions = getPositions(request, prefix);
                                orgs.add(new Organization(links.get(i), positions));
                            }
                        }
                        if (orgs.size() > 0) {
                            resume.setSection(type, new OrganizationSection(orgs));
                        }
                    }
                }
            }
        }
        if (!HtmlUtil.isEmpty(fullName)) {
            switch (action) {
                case "add" -> storage.save(resume);
                case "edit" -> storage.update(resume);
            }
        }
        response.sendRedirect("resume");
    }

    private List<Link> getLinks(HttpServletRequest request, String prefix) {
        String[] orgNames = request.getParameterValues(prefix + "_orgName");
        String[] siteNames = request.getParameterValues(prefix + "_siteName");
        List<Link> links = new ArrayList<>();
        for (int i = 0; i < orgNames.length; i++) {
            links.add(new Link(orgNames[i], siteNames[i]));
        }
        return links;
    }

    private List<Organization.Position> getPositions(HttpServletRequest request, String prefix) {
        String[] startDates = request.getParameterValues(prefix + "_startDate");
        String[] endDates = request.getParameterValues(prefix + "_endDate");
        String[] titles = request.getParameterValues(prefix + "_title");
        String[] descriptions = request.getParameterValues(prefix + "_description");
        List<Organization.Position> positions = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            if (!HtmlUtil.isEmpty(titles[i]) && !HtmlUtil.isEmpty(startDates[i])) {
                positions.add(new Organization.Position(
                        DateUtil.parse(startDates[i]),
                        DateUtil.parse(endDates[i]),
                        titles[i],
                        descriptions[i])
                );
            }
        }
        return positions;
    }
}