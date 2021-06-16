package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
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
    protected Object findElement(String uuid) {
        return storage.containsKey(uuid) ? null : uuid;
    }

    @Override
    protected void changeStorage(Resume resume, Object element) {
        saveIn(resume, element);
    }

    @Override
    protected void saveIn(Resume resume, Object element) {
        storage.put((String) element, resume);
    }

    @Override
    protected Resume getOut(String uuid, Object element) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid, Object element) {
        storage.remove(uuid);
    }
}