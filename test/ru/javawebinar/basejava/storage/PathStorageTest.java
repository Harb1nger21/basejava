package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectStreamStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(new ObjectStreamStrategy(), STORAGE_DIR.getAbsolutePath()));
    }
}