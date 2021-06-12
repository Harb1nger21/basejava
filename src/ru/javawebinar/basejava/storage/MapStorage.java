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
    protected int findIndex(String uuid) {
        if(storage.containsKey(uuid)){
            return 0;
        }
        return -1;
    }

    @Override
    protected void changeStorage(Resume resume, int index) {
        saveIn(resume,index);
    }

    @Override
    protected void saveIn(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getOut(String uuid, int index) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        storage.remove(uuid);
    }
}