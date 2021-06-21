package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Object findKey(String uuid) {
        int index = -1;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                index = storage.indexOf(r);
            }
        }
        return index;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    protected void saveIn(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Resume getOut(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index != -1;
    }

    @Override
    protected List<Resume> convertToList() {
        return storage;
    }
}