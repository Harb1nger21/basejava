package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStrategy implements Strategy {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {

                final String title = entry.getKey().getTitle();
                dos.writeUTF(title);
                switch (title) {
                    case "Личные качества", "Позиция" -> dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    case "Достижения", "Квалификация" -> {
                        List<String> sectionList = ((ListSection) entry.getValue()).getItems();
                        dos.writeInt(sectionList.size());
                        for (String item : sectionList) {
                            dos.writeUTF(item);
                        }
                    }
                    case "Опыт работы", "Образование" -> {
                        List<Organization> organizationList = ((OrganizationSection) entry.getValue()).getOrganizations();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            dos.writeUTF(organization.getHomePage().getName());
                            String url = organization.getHomePage().getUrl();
                            dos.writeUTF(Objects.requireNonNullElse(url, "null"));
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                LocalDate start = position.getStartDate();
                                LocalDate end = position.getEndDate();
                                dos.writeInt(start.getYear());
                                dos.writeInt(start.getMonthValue());
                                dos.writeInt(end.getYear());
                                dos.writeInt(end.getMonthValue());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int contactCount = dis.readInt();
            for (int i = 0; i < contactCount; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionCount = dis.readInt();
            for (int i = 0; i < sectionCount; i++) {
                String title = dis.readUTF();
                switch (title) {
                    case "Личные качества", "Позиция" -> resume.addSection(getType(title), new TextSection(dis.readUTF()));
                    case "Достижения", "Квалификация" -> {
                        int achievCount = dis.readInt();
                        List<String> achievList = new ArrayList<>();
                        for (int j = 0; j < achievCount; j++) {
                            achievList.add(dis.readUTF());
                        }
                        resume.addSection(getType(title), new ListSection(achievList));
                    }
                    case "Опыт работы", "Образование" -> {
                        int orgCount = dis.readInt();
                        List<Organization> orglist = new ArrayList<>();
                        for (int k = 0; k < orgCount; k++) {
                            Organization organization = new Organization(new Link(dis.readUTF(), notNull(dis.readUTF())), readPositions(dis));
                            orglist.add(organization);
                            resume.addSection(getType(title), new OrganizationSection(orglist));
                        }
                    }
                }
            }
            return resume;
        }
    }

    private SectionType getType(String string) {
        return switch (string) {
            case "Личные качества" -> SectionType.PERSONAL;
            case "Позиция" -> SectionType.OBJECTIVE;
            case "Достижения" -> SectionType.ACHIEVEMENT;
            case "Квалификация" -> SectionType.QUALIFICATIONS;
            case "Опыт работы" -> SectionType.EXPERIENCE;
            case "Образование" -> SectionType.EDUCATION;
            default -> null;
        };
    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int positionCount = dis.readInt();
        for (int i = 0; i < positionCount; i++) {
            Organization.Position position = new Organization.Position(
                    dis.readInt(),
                    Month.of(dis.readInt()),
                    dis.readInt(),
                    Month.of(dis.readInt()),
                    dis.readUTF(),
                    notNull(dis.readUTF()));
            positions.add(position);
        }
        return positions;
    }

    private String notNull(String string) {
        if (string.equals("null")) {
            return null;
        }
        return string;
    }
}
