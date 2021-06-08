package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected void checkNotExistStorageException(String uuid){
        int index = findIndex(uuid);

        if (index <= -1) {
            throw new NotExistStorageException(uuid);
        }
    }
    protected void checkExistStorageException(Resume resume){
        if (findIndex(resume.getUuid()) > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void clear() {
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    protected abstract int findIndex(String uuid);
}
