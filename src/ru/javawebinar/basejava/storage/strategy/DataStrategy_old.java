package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStrategy_old implements Strategy {
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

            dos.writeUTF(resume.getSection(SectionType.PERSONAL).toString());
            dos.writeUTF(resume.getSection(SectionType.OBJECTIVE).toString());

            writeListSection(dos, resume, SectionType.ACHIEVEMENT);
            writeListSection(dos, resume, SectionType.QUALIFICATIONS);
            writeOrganizationSection(dos, resume, SectionType.EXPERIENCE);
            writeOrganizationSection(dos, resume, SectionType.EDUCATION);
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
            resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
            resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
            resume.addSection(SectionType.ACHIEVEMENT, readListSection(dis));
            resume.addSection(SectionType.QUALIFICATIONS, readListSection(dis));
            resume.addSection(SectionType.EXPERIENCE, readOrganizationSection(dis));
            resume.addSection(SectionType.EDUCATION, readOrganizationSection(dis));

            return resume;
        }
    }

    private void writeListSection(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        List<String> list = ((ListSection) resume.getSection(type)).getItems();
        dos.writeInt(list.size());
        for (String item : list) {
            dos.writeUTF(item);
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, Resume resume, SectionType type) throws IOException {
        List<Organization> organizationList = ((OrganizationSection) resume.getSection(type)).getOrganizations();
        dos.writeInt(organizationList.size());
        for (Organization organization : organizationList) {
            dos.writeUTF(organization.getHomePage().getName());
            dos.writeUTF(organization.getHomePage().getUrl());
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
                dos.writeUTF(position.getDescription());
            }
        }
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        List<String> list = new ArrayList<>();
        int achieveCount = dis.readInt();
        for (int i = 0; i < achieveCount; i++) {
            String item = dis.readUTF();
            list.add(item);
        }
        return new ListSection(list);
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> list = new ArrayList<>();
        int organizationCount = dis.readInt();
        for (int i = 0; i < organizationCount; i++) {
            Organization organization = new Organization(new Link(dis.readUTF(), dis.readUTF()), getPositions(dis));
            list.add(organization);
        }
        return new OrganizationSection(list);
    }

    private List<Organization.Position> getPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int positionCount = dis.readInt();
        for (int i = 0; i < positionCount; i++) {
            Organization.Position position = new Organization.Position(
                    dis.readInt(),
                    Month.of(dis.readInt()),
                    dis.readInt(),
                    Month.of(dis.readInt()),
                    dis.readUTF(),
                    dis.readUTF());
            positions.add(position);
        }
        return positions;
    }
}
