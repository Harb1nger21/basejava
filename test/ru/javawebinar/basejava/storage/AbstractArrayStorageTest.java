package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exeption.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void getStorageExceptionTest() {
        getStorage().clear();
        try {
            for (int i = 0; i < 10000; i++) {
                getStorage().save(new Resume("uuid" + i));
            }
        } catch (StorageException ignored) {
            Assert.fail("Переполнение произошло раньше времени");
        }
        getStorage().save(new Resume("uuid20000"));
    }
}