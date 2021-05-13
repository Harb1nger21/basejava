package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countElements, searchKey);
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index > 0) {
            System.out.printf("ERROR: resume with %s is already in\n", resume.getUuid());
        } else if (countElements == storage.length) {
            System.out.println("ERROR: ArrayStorage is already has 10000 resume");
        } else if (index < 0) {
            index = index * (-1) - 1;
            System.arraycopy(storage, index, storage, index + 1, countElements);
            storage[index] = resume;
            countElements++;
        }
    }
}
