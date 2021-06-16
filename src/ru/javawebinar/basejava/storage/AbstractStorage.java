package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        changeStorage(resume, getKeyIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object element = findKey(resume.getUuid());
        if (isExist(element)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveIn(resume, element);
    }

    @Override
    public Resume get(String uuid) {
        return getOut(getKeyIfResumeExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getKeyIfResumeExist(uuid));
    }

    private Object getKeyIfResumeExist(String uuid) {
        Object key = findKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract Object findKey(String uuid);

    protected abstract void changeStorage(Resume resume, Object element);

    protected abstract void saveIn(Resume resume, Object element);

    protected abstract Resume getOut(Object element);

    protected abstract void deleteResume(Object element);

    protected abstract boolean isExist(Object element);
}