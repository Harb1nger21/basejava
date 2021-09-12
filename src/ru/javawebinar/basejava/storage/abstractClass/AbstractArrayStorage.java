package ru.javawebinar.basejava.storage.abstractClass;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countElements = 0;

    public final void clear() {
        Arrays.fill(storage, 0, countElements, null);
        countElements = 0;
    }

    public final int size() {
        return countElements;
    }

    @Override
    protected void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void saveResume(Resume resume, Integer index) {
        if (countElements == storage.length) {
            throw new StorageException("ERROR: ArrayStorage is already has 10000 resume", resume.getUuid());
        }
        add(resume, index);
        countElements++;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, countElements - 1 - index);
        countElements--;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index > -1;
    }

    @Override
    protected List<Resume> getAsList() {
        return Arrays.asList(Arrays.copyOf(storage, countElements));
    }

    protected abstract void add(Resume resume, int index);
}