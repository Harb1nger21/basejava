package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.CollectionConsumer;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStrategy implements Strategy {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            forEachWithIOException(contacts.entrySet(), contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });

//            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
//                dos.writeUTF(entry.getKey().name());
//                dos.writeUTF(entry.getValue());
//            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());

            forEachWithIOException(sections.entrySet(), section -> {
                SectionType type = section.getKey();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> sectionList = ((ListSection) section.getValue()).getItems();
                        dos.writeInt(sectionList.size());
                        forEachWithIOException(sectionList, dos::writeUTF);
//                        for (String item : sectionList) {
//                            dos.writeUTF(item);
//                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = ((OrganizationSection) section.getValue()).getOrganizations();
                        dos.writeInt(organizationList.size());
                        forEachWithIOException(organizationList, organization -> {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(Objects.requireNonNullElse(link.getUrl(), "null"));
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            forEachWithIOException(positions, position -> {
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                            });
                        });
//                        for (Organization organization : organizationList) {
//                            Link link = organization.getHomePage();
//                            dos.writeUTF(link.getName());
//                            dos.writeUTF(Objects.requireNonNullElse(link.getUrl(), "null"));
//                            List<Organization.Position> positions = organization.getPositions();
//                            dos.writeInt(positions.size());
//                            for (Organization.Position position : positions) {
//                                writeDate(dos, position.getStartDate());
//                                writeDate(dos, position.getEndDate());
//                                dos.writeUTF(position.getTitle());
//                                String description = position.getDescription();
//                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
//                            }
//                        }
                    }
                }
            });

//            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
//
//                SectionType type = entry.getKey();
//                dos.writeUTF(type.name());
//                switch (type) {
//                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entry.getValue()).getContent());
//                    case ACHIEVEMENT, QUALIFICATIONS -> {
//                        List<String> sectionList = ((ListSection) entry.getValue()).getItems();
//                        dos.writeInt(sectionList.size());
//                        for (String item : sectionList) {
//                            dos.writeUTF(item);
//                        }
//                    }
//                    case EXPERIENCE, EDUCATION -> {
//                        List<Organization> organizationList = ((OrganizationSection) entry.getValue()).getOrganizations();
//                        dos.writeInt(organizationList.size());
//                        for (Organization organization : organizationList) {
//                            Link link = organization.getHomePage();
//                            dos.writeUTF(link.getName());
//                            dos.writeUTF(Objects.requireNonNullElse(link.getUrl(), "null"));
//                            List<Organization.Position> positions = organization.getPositions();
//                            dos.writeInt(positions.size());
//                            for (Organization.Position position : positions) {
//                                writeDate(dos, position.getStartDate());
//                                writeDate(dos, position.getEndDate());
//                                dos.writeUTF(position.getTitle());
//                                String description = position.getDescription();
//                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
//                            }
//                        }
//                    }
//                }
//            }
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
                SectionType title = SectionType.valueOf(dis.readUTF());
                switch (title) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(title, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int listSecCount = dis.readInt();
                        List<String> achievList = new ArrayList<>();
                        for (int j = 0; j < listSecCount; j++) {
                            achievList.add(dis.readUTF());
                        }
                        resume.addSection(title, new ListSection(achievList));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgCount = dis.readInt();
                        List<Organization> orglist = new ArrayList<>();
                        for (int k = 0; k < orgCount; k++) {
                            Organization organization = new Organization(new Link(dis.readUTF(), convert(dis.readUTF())), readPositions(dis));
                            orglist.add(organization);
                            resume.addSection(title, new OrganizationSection(orglist));
                        }
                    }
                }
            }
            return resume;
        }
    }

    private <T> void forEachWithIOException(Collection<T> entrySet, CollectionConsumer<? super T> action) throws IOException {
        for (T entry : entrySet) {
            action.accept(entry);
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private String convert(String string) {
        if (!string.equals("null")) {
            return string;
        }
        return null;
    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int positionCount = dis.readInt();
        for (int i = 0; i < positionCount; i++) {
            Organization.Position position = new Organization.Position(
                    readDate(dis),
                    readDate(dis),
                    dis.readUTF(),
                    convert(dis.readUTF()));
            positions.add(position);
        }
        return positions;
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
