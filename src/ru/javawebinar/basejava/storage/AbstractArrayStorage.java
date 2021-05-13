package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countElements = 0;

    public int size() {
        return countElements;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("ERROR: resume with %s is not found\n", uuid);
        return null;
    }

    public void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
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

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index == -1) {
            System.out.printf("ERROR: resume with %s is not found\n", resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Update success");
        }
    }

    protected abstract int findIndex(String uuid);
}
