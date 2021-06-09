package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (findIndex(resume.getUuid()) <= -1) {
            throw new NotExistStorageException(resume.getUuid());
        }
        changeStorage(resume);
    }

    @Override
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveIn(resume);
    }

    @Override
    public Resume get(String uuid) {
        if (findIndex(uuid) <= -1) {
            throw new NotExistStorageException(uuid);
        }
        return getOut(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index <= -1) {
            throw new NotExistStorageException(uuid);
        }

        deleteResume(uuid);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void changeStorage(Resume resume);

    protected abstract void saveIn(Resume resume);

    protected abstract Resume getOut(String uuid);

    protected abstract void deleteResume(String uuid);

}
