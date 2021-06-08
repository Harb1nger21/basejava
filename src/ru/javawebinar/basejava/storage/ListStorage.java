package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        checkNotExistStorageException(resume.getUuid());
        storage.set(findIndex(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        checkExistStorageException(resume);
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkNotExistStorageException(uuid);
        return storage.get(findIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        checkNotExistStorageException(uuid);
        storage.remove(findIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        Resume[] allResume = new Resume[storage.size()];
        Iterator<Resume> iterator = storage.iterator();
        for (int i = 0; i < allResume.length; i++) {
            allResume[i] = iterator.next();
        }
        return allResume;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }
}
