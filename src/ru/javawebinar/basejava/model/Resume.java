package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
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
//        Comparator<Resume> comparator = Comparator.comparing(Resume::getUuid);
//        comparator.thenComparing(Resume::getFullName);
//        int result = comparator.compare(this,resume);
        int result = fullName.compareTo(resume.fullName);
        if (result == 0) {
            result = uuid.compareTo(resume.uuid);
        }
        return result;
    }
}