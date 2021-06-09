package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        Resume[] allResume = new Resume[storage.size()];
        Iterator<Resume> iterator = storage.iterator();
        for (int i = 0; i < allResume.length; i++) {
            allResume[i] = iterator.next();
        }
        return allResume;
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void changeStorage(Resume resume) {
        storage.set(findIndex(resume.getUuid()), resume);
    }

    @Override
    protected void saveIn(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getOut(String uuid) {
        return storage.get(findIndex(uuid));
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(findIndex(uuid));
    }
}
