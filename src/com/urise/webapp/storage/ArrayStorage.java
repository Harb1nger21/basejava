package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.printf("ERROR: resume with %s is already in\n", resume.getUuid());
        } else if (countElements == storage.length) {
            System.out.println("ERROR: ArrayStorage is already has 10000 resume");
        } else {
            storage[countElements] = resume;
            countElements++;
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
