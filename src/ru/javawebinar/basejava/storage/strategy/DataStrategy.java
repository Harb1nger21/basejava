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
            writeEachWithIOException(contacts.entrySet(), dos, contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeEachWithIOException(sections.entrySet(), dos, section -> {
                SectionType type = section.getKey();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> sectionList = ((ListSection) section.getValue()).getItems();
                        writeEachWithIOException(sectionList, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = ((OrganizationSection) section.getValue()).getOrganizations();
                        writeEachWithIOException(organizationList, dos, organization -> {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(Objects.requireNonNullElse(link.getUrl(), "null"));
                            List<Organization.Position> positions = organization.getPositions();
                            writeEachWithIOException(positions, dos, position -> {
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readEachWithIOException(resume, dis, update -> update.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readEachWithIOException(resume, dis, update -> {
                SectionType title = SectionType.valueOf(dis.readUTF());
                switch (title) {
                    case PERSONAL, OBJECTIVE -> update.setSection(title, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> achievList = new ArrayList<>();
                        readEachWithIOException(resume, dis, achiev -> achievList.add(dis.readUTF()));
                        update.setSection(title, new ListSection(achievList));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orglist = new ArrayList<>();
                        readEachWithIOException(resume, dis, org -> {
                            Organization organization = new Organization(new Link(dis.readUTF(), convert(dis.readUTF())), readPositions(resume, dis));
                            orglist.add(organization);
                        });
                        update.setSection(title, new OrganizationSection(orglist));
                    }
                }
            });
            return resume;
        }
    }

    private <T> void writeEachWithIOException(Collection<T> collection, DataOutputStream dos, CollectionConsumer<? super T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T entry : collection) {
            action.accept(entry);
        }
    }

    private void readEachWithIOException(Resume resume, DataInputStream dis, CollectionConsumer<Resume> action) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            action.accept(resume);
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private String convert(String string) {
        return (!string.equals("null")) ? string : null;
    }

    private List<Organization.Position> readPositions(Resume resume, DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        readEachWithIOException(resume, dis, pos -> {
            Organization.Position position = new Organization.Position(
                    readDate(dis),
                    readDate(dis),
                    dis.readUTF(),
                    convert(dis.readUTF()));
            positions.add(position);
        });
        return positions;
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
