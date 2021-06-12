package ru.javawebinar.basejava.storage;

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
    protected int findIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void changeStorage(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void saveIn(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume getOut(String uuid, int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        storage.remove(index);
    }
}