package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        changeStorage(resume, getElementIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object element = findElement(resume.getUuid());
        if (isInteger(element)) {
            if ((int) element > -1) {
                throw new ExistStorageException(resume.getUuid());
            }
        } else {
            if (element == null) {
                throw new ExistStorageException(resume.getUuid());
            }
        }
        saveIn(resume, element);
    }

    @Override
    public Resume get(String uuid) {
        return getOut(uuid, getElementIfResumeExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(uuid, getElementIfResumeExist(uuid));
    }

    private Object getElementIfResumeExist(String uuid) {
        Object element = findElement(uuid);
        if (isInteger(element)) {
            if ((int) element <= -1) {
                throw new NotExistStorageException(uuid);
            }
        } else {
            if (element != null) {
                throw new NotExistStorageException(uuid);
            }
        }
        return element;
    }

    private boolean isInteger(Object element) {
        try {
            int index = (int) element;
            return true;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    protected abstract Object findElement(String uuid);

    protected abstract void changeStorage(Resume resume, Object element);

    protected abstract void saveIn(Resume resume, Object element);

    protected abstract Resume getOut(String uuid, Object element);

    protected abstract void deleteResume(String uuid, Object element);
}