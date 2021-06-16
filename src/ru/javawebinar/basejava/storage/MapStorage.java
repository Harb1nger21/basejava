package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void changeStorage(Resume resume, Object element) {
        saveIn(resume, element);
    }

    @Override
    protected void saveIn(Resume resume, Object element) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getOut(Object element) {
        return storage.get((String) element);
    }

    @Override
    protected void deleteResume(Object element) {
        storage.remove((String) element);
    }

    @Override
    protected boolean isExist(Object element) {
        return element != null;
    }
}