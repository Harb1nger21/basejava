package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        doUpdate(resume, getKeyIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object key = getKeyIfResumeNotExist(resume.getUuid());
        saveIn(resume, key);
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
    private Object getKeyIfResumeNotExist(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract Object findKey(String uuid);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract void saveIn(Resume resume, Object key);

    protected abstract Resume getOut(Object key);

    protected abstract void deleteResume(Object key);

    protected abstract boolean isExist(Object key);
}