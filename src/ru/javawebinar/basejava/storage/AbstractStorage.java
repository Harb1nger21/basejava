package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        changeStorage(resume, getIndexIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveIn(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        return getOut(uuid, getIndexIfResumeExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(uuid, getIndexIfResumeExist(uuid));
    }

    private int getIndexIfResumeExist(String uuid) {
        int index = findIndex(uuid);
        if (index <= -1) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void changeStorage(Resume resume, int index);

    protected abstract void saveIn(Resume resume, int index);

    protected abstract Resume getOut(String uuid, int index);

    protected abstract void deleteResume(String uuid, int index);
}