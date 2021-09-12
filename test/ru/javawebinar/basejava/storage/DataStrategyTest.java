package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.DataStrategy;

public class DataStrategyTest extends AbstractStorageTest {
    public DataStrategyTest() {
        super(new PathStorage(new DataStrategy(), STORAGE_DIR.getAbsolutePath()));
    }
}
