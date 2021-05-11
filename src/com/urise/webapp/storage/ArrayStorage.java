package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

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

    public void delete(String uuid) {
        int index = findIndex(uuid);

        if (index == -1) {
            System.out.printf("ERROR: resume with %s is not found\n", uuid);
        } else if (countElements - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, countElements - index);
            countElements--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index == -1) {
            System.out.printf("ERROR: resume with %s is not found\n", resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Update success");
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
