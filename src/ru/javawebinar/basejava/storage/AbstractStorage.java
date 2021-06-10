package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        getNotExistStorageException(resume.getUuid());
        changeStorage(resume, findIndex(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveIn(resume, findIndex(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        getNotExistStorageException(uuid);
        return getOut(uuid, findIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        getNotExistStorageException(uuid);
        deleteResume(uuid, findIndex(uuid));
    }

    private void getNotExistStorageException(String uuid) {
        if (findIndex(uuid) <= -1) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void changeStorage(Resume resume, int index);

    protected abstract void saveIn(Resume resume, int index);

    protected abstract Resume getOut(String uuid, int index);

    protected abstract void deleteResume(String uuid, int index);

}
