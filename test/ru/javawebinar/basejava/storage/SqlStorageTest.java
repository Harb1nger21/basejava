package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}