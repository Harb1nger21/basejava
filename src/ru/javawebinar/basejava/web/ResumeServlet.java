package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "CustomerController", urlPatterns = "/processCustomer")
public class ResumeServlet extends HttpServlet {
    private SqlStorage storage;

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
            case "view", "edit" -> resume = storage.get(uuid);
            case "add" -> resume = new Resume(uuid, "");
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
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String content = request.getParameter(type.name());
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    if (content != null && content.trim().length() != 0) {
                        resume.addSection(type, new TextSection(content));
                    } else {
                        resume.getSections().remove(type);
                    }
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    if (content.length() != 0) {
                        List<String> list = Arrays.stream(content.split("\r\n")).filter(s -> !s.equals("")).collect(Collectors.toList());
                        resume.addSection(type, new ListSection(list));
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    Map<String, String[]> map = request.getParameterMap(); //удалить!

                    RequestCustomer consumer = new RequestCustomer();
                    List<Link> linkList = consumer.getLinks(request, type.name());
                    List<Organization> orgList = new ArrayList<>();
                    for (int i = 0; i < linkList.size(); i++) {
                        String prefix = type.name() + "_" + i;
                        orgList.add(new Organization(linkList.get(i), consumer.getPositions(request, prefix)));
                    }
                    if (orgList.get(0).getHomePage().getName().equals("")) {
                        orgList.remove(0);
                    }
                    resume.addSection(type, new OrganizationSection(orgList));
                }
            }
        }
        if (!fullName.equals("")) {
            switch (action) {
                case "add" -> storage.save(resume);
                case "edit" -> storage.update(resume);
            }
        }
        response.sendRedirect("resume");
    }

    private static class RequestCustomer {
        protected RequestCustomer() {
        }

        private List<Link> getLinks(HttpServletRequest request, String prefix) {
            List<String> orgNames = Arrays.asList(request.getParameterValues(prefix + "_orgName"));
            List<String> siteNames = Arrays.asList(request.getParameterValues(prefix + "_siteName"));

            List<Link> links = new ArrayList<>();
            for (int i = 0; i < orgNames.size(); i++) {
                links.add(new Link(orgNames.get(i), siteNames.get(i)));
            }

            return links;
        }

        private List<LocalDate> getDateList(List<String> dates) {
            List<LocalDate> dateList = new ArrayList<>();
            for (String date : dates) {
                if (date.equals("настоящее время")) {
                    date = "3000-01";
                }
                dateList.add(DateUtil.of(Integer.parseInt(date.substring(0, 4)), Month.of(Integer.parseInt(date.substring(5)))));
            }
            return dateList;
        }

        public List<Organization.Position> getPositions(HttpServletRequest request, String prefix) {
            List<String> startDate = Arrays.asList(request.getParameterValues(prefix + "_startDate"));
            List<String> endDate = Arrays.asList(request.getParameterValues(prefix + "_endDate"));
            List<String> title = Arrays.asList(request.getParameterValues(prefix + "_title"));
            List<String> description = Arrays.asList(request.getParameterValues(prefix + "_description"));

            for (int i = 0; i < startDate.size(); i++) {
                if (startDate.get(i).equals("")) {
                    startDate = startDate.subList(i + 1, startDate.size());
                    endDate = endDate.subList(i + 1, endDate.size());
                    title = title.subList(i + 1, title.size());
                    description = description.subList(i + 1, description.size());
                }
            }

            List<LocalDate> sDates = getDateList(startDate);
            List<LocalDate> eDates = getDateList(endDate);
            List<Organization.Position> positions = new ArrayList<>();

            for (int i = 0; i < startDate.size(); i++) {
                positions.add(new Organization.Position(
                        sDates.get(i).getYear(),
                        sDates.get(i).getMonth(),
                        eDates.get(i).getYear(),
                        eDates.get(i).getMonth(),
                        title.get(i),
                        description.get(i)));
            }
            return positions;
        }
    }
}