package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer findKey(String uuid) {
        Resume searchKey = new Resume(uuid, "tempName");
        Comparator<Resume> comparator = Comparator.comparing(Resume::getUuid);
        return Arrays.binarySearch(storage, 0, countElements, searchKey, comparator);
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