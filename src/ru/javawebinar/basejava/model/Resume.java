package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private final String fullName;
    private final Map<ContactType, ContactList> contactMap = new HashMap<>();
    private final Map<SectionType, Section> sectionsMap = new HashMap<>();

    public Resume(String fullName) {

        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid,
                  String fullName) {

        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        contactMap.put(ContactType.PHONE, new ContactList());
        contactMap.put(ContactType.SOCIAL, new ContactList());
        contactMap.put(ContactType.EMAIL, new ContactList());
        sectionsMap.put(SectionType.PERSONAL, new Section.TextSection());
        sectionsMap.put(SectionType.OBJECTIVE, new Section.TextSection());
        sectionsMap.put(SectionType.ACHIEVEMENT, new Section.ListSection());
        sectionsMap.put(SectionType.QUALIFICATIONS, new Section.ListSection());
        sectionsMap.put(SectionType.EXPERIENCE, new Section.MapSection());
        sectionsMap.put(SectionType.EDUCATION, new Section.MapSection());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<SectionType, Section> getSectionsMap() {
        return sectionsMap;
    }

    public Map<ContactType, ContactList> getContactMap() {
        return contactMap;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume resume) {
        int result = fullName.compareTo(resume.fullName);
        if (result == 0) {
            result = uuid.compareTo(resume.uuid);
        }
        return result;
    }
}