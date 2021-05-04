package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public final Resume[] storage = new Resume[10_000];
    private int countElements = 0;

    public void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public void save(Resume resume) {
        if (get(resume.getUuid()) != null) {
            System.out.printf("ERROR: resume with %s is already in\n", resume.getUuid());
        } else if (countElements == storage.length) {
            System.out.println("ERROR: ArrayStorage is already has 10000 resume");
        } else {
            storage[countElements] = resume;
            countElements++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.printf("Resume with %s is not found\n", uuid);
        return null;
    }

    public void delete(String uuid) {
        if (get(uuid) == null) {
            System.out.printf("ERROR: resume with %s is not found\n", uuid);
        } else {
            for (int i = 0; i < countElements; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    for (int j = i; j < countElements; j++) {
                        storage[j] = storage[j + 1];
                    }
                    countElements--;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
    }

    public int size() {
        return countElements;
    }

    public void update(Resume resume) {
        if (get(resume.getUuid()) == null) {
            System.out.printf("ERROR: resume with %s is not found\n", resume.getUuid());
        } else {
            for (int i = 0; i < countElements; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    storage[i] = resume;
                    System.out.println("Update success");
                }
            }
        }
    }
}
