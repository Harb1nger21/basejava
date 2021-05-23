package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countElements, searchKey);
    }

    protected void add(Resume resume, int index) {
        index = index * (-1) - 1;
        if (countElements + 1 < storage.length) {
            System.arraycopy(storage, index, storage, index + 1, countElements);
        } else if (countElements - index >= 0) {
            System.arraycopy(storage, index, storage, index + 1, countElements - index);
        }
        storage[index] = resume;
    }
}
