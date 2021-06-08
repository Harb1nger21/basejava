package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exeption.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countElements = 0;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public final void update(Resume resume) {
        checkNotExistStorageException(resume.getUuid());
        storage[findIndex(resume.getUuid())] = resume;
    }

    public final void save(Resume resume) {
        checkExistStorageException(resume);
        if (countElements == storage.length) {
            throw new StorageException("ERROR: ArrayStorage is already has 10000 resume", resume.getUuid());
        }
        add(resume, findIndex(resume.getUuid()));
        countElements++;
    }

    public final Resume get(String uuid) {
        checkNotExistStorageException(uuid);
        return storage[findIndex(uuid)];
    }

    public final void delete(String uuid) {
        checkNotExistStorageException(uuid);
        int index = findIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, countElements - 1 - index);
        countElements--;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
    }

    public final int size() {
        return countElements;
    }

    protected abstract void add(Resume resume, int index);

}
