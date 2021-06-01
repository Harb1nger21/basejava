package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.exeption.StorageException;
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
        throw new NotExistStorageException(uuid);
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
            throw new NotExistStorageException(uuid);
        } else if (countElements - 1 - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, countElements - 1 - index);
        }
        countElements--;
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index == -1) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Update success");
        }
    }

    public final void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else if (countElements == storage.length) {
            throw new StorageException("ERROR: ArrayStorage is already has 10000 resume", resume.getUuid());
        } else {
            add(resume, index);
            countElements++;
        }
    }

    protected abstract void add(Resume resume, int index);

    protected abstract int findIndex(String uuid);
}
