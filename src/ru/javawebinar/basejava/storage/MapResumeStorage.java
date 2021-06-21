package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object findKey(String uuid) {
        if(storage.containsKey(uuid)){
            return storage.get(uuid);
        }
        return null;
    }

    @Override
    protected Resume getOut(Object key) {
        return storage.get(((Resume) key).getUuid());
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove(((Resume) key).getUuid());
    }
}
