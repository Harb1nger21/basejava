package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countElements = 0;

    public final int size() {
        return countElements;
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("ERROR: resume with %s is not found\n", uuid);
        return null;
    }

    public final void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);

        if (index == -1) {
            System.out.printf("ERROR: resume with %s is not found\n", uuid);
        } else if (countElements - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, countElements - index);
            countElements--;
        }
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index == -1) {
            System.out.printf("ERROR: resume with %s is not found\n", resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Update success");
        }
    }

    public void save(Resume resume, int index) {
        if (index > -1) {
            System.out.printf("ERROR: resume with %s is already in\n", resume.getUuid());
        } else if (countElements == storage.length) {
            System.out.println("ERROR: ArrayStorage is already has 10000 resume");
        }else{
            add(resume);
            countElements++;
        }
    }

    protected abstract void add(Resume resume);

    protected abstract int findIndex(String uuid);
}
