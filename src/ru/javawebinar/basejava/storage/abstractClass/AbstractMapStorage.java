package ru.javawebinar.basejava.storage.abstractClass;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage<Object> {
    protected Map<String, Resume> storage = new LinkedHashMap<>();

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResume(Resume resume, Object key) {
        saveResume(resume, key);
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return Arrays.asList((storage.values()).toArray(new Resume[0]));
    }
}
