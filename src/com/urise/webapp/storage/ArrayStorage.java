package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public final Resume[] storage = new Resume[10000];
    private int countElements = 0;

    public void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public void save(Resume r) {
        if (this.get(r.getUuid()) != null) {
            System.out.println("ERROR: this resume is already in");
        } else if (countElements == 10000) {
            System.out.println("ERROR: ArrayStorage is already has 10000 resume");
        } else {
            storage[countElements] = r;
            countElements++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("This resume is not found");
        return null;
    }

    public void delete(String uuid) {
        if (this.get(uuid) == null) {
            System.out.println("ERROR: this resume is not found");
        } else {
            for (int i = 0; i < countElements; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    for (int j = i; j < countElements; j++){
                        storage[j] = storage[j+1];
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
        if (this.get(resume.getUuid()) == null) {
            System.out.println("ERROR: this resume is not found");
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
