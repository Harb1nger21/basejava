package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.JsonStrategy;

public class JsonStorageTest extends AbstractStorageTest {
    public JsonStorageTest() {
        super(new PathStorage(new JsonStrategy(), STORAGE_DIR.getAbsolutePath()));
    }
}