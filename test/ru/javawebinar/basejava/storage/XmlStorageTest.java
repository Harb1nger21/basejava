package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.XmlStrategy;

public class XmlStorageTest extends AbstractStorageTest {
    public XmlStorageTest() {
        super(new PathStorage(new XmlStrategy(),STORAGE_DIR.getAbsolutePath()));
    }
}
