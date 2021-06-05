package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.exeption.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private final Resume[] testStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(testStorage, storage.getAll());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_2), new Resume(UUID_3)}, storage.getAll());
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(new Resume(UUID_4), storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void getExistStorageException() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void getStorageException() {
        storage.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException ignored) {
            Assert.fail("Переполнение");
        }
    }

}