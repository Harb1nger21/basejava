package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countElements, searchKey);
    }

    @Override
    protected void add(Resume resume, int index) {
        index = -index - 1;
        if (countElements - index >= 0) {
            System.arraycopy(storage, index, storage, index + 1, countElements - index);
        }
        storage[index] = resume;
    }
}