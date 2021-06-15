package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countElements = 0;

    public final void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, countElements);
    }

    public final int size() {
        return countElements;
    }

    @Override
    protected void changeStorage(Resume resume, Object index) {
        storage[(int)index] = resume;
    }

    @Override
    protected void saveIn(Resume resume, Object element) {
        if (countElements == storage.length) {
            throw new StorageException("ERROR: ArrayStorage is already has 10000 resume", resume.getUuid());
        }
        add(resume, (int)element);
        countElements++;
    }

    @Override
    protected Resume getOut(String uuid, Object element) {
        return storage[(int)element];
    }

    @Override
    protected void deleteResume(String uuid, Object element) {
        int index = (int)element;
        System.arraycopy(storage, index + 1, storage, index, countElements - 1 - index);
        countElements--;
    }

    protected abstract void add(Resume resume, int index);
}