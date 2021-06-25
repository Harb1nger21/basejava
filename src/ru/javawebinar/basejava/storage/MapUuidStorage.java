package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {
    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getOut(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((String) key);
    }
}