package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getProps().getProperty("db.url"),
                Config.get().getProps().getProperty("db.user"),
                Config.get().getProps().getProperty("db.password")));
    }
}