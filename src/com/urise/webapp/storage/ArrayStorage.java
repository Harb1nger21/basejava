package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int countElements = 0;

    public void clear() {
        for (int i = 0; i < countElements; i++) {
            storage[i] = null;
        }
        countElements = 0;
    }

    public void save(Resume r) {
        storage[countElements] = r;
        countElements++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                for (int j = i; j < countElements - 1; j++) {
                    storage[i] = storage[j + 1];
                }
                countElements--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResumes = new Resume[countElements];
        for (int i = 0; i < countElements; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    public int size() {
        return countElements;
    }

    public void update(Resume resume){
    }
}
