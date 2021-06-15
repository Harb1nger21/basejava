package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        changeStorage(resume, getElementIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object element = findElement(resume.getUuid());
        existElementInStorage(element, resume.getUuid());
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
        notExistElementInStorage(element, uuid);
        return element;
    }

    protected abstract Object findElement(String uuid);

    protected abstract void changeStorage(Resume resume, Object element);

    protected abstract void saveIn(Resume resume, Object element);

    protected abstract Resume getOut(String uuid, Object element);

    protected abstract void deleteResume(String uuid, Object element);

    protected abstract void existElementInStorage(Object element, String uuid);

    protected abstract void notExistElementInStorage(Object element, String uuid);
}