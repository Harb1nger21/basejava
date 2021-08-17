package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.abstractClass.AbstractMapStorage;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getResume(Object key) {
        return (Resume) key;
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove(((Resume) key).getUuid());
    }
}
