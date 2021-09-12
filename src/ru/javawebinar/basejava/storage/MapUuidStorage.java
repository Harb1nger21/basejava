package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.abstractClass.AbstractMapStorage;

public class MapUuidStorage extends AbstractMapStorage {
    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((String) key);
    }
}