package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object findKey(String uuid) {
        for (Map.Entry<String, Resume> kv : storage.entrySet()) {
            Resume value = kv.getValue();
            if (value.getUuid().equals(uuid)) {
                return value;
            }
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
