package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getOut(Object key) {
        return (Resume) key;
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove(((Resume) key).getUuid());
    }
}
