package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Object findElement(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void changeStorage(Resume resume, Object element) {
        storage.set((int)element, resume);
    }

    @Override
    protected void saveIn(Resume resume, Object element) {
        storage.add(resume);
    }

    @Override
    protected Resume getOut(String uuid, Object index) {
        return storage.get((int)index);
    }

    @Override
    protected void deleteResume(String uuid, Object element) {
        storage.remove((int) element);
    }

    @Override
    protected void existElementInStorage(Object element, String uuid) {
        if((int)element > -1){
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    protected void notExistElementInStorage(Object element, String uuid) {
        if((int)element <= -1){
            throw new NotExistStorageException(uuid);
        }
    }
}