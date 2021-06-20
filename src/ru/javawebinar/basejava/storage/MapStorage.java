package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = Arrays.asList((storage.values()).toArray(new Resume[0]));
        sortedList.sort(Resume::compareTo);
        return sortedList;
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        saveIn(resume, key);
    }

    @Override
    protected void saveIn(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getOut(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }
}