package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.exeption.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

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

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)}, storage.getAll());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_2), new Resume(UUID_3)}, storage.getAll());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(new Resume("uuid4"), storage.get("uuid4"));
    }

    @Test
    public void getStorageException() {
        storage.clear();
        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume("uuid" + i));
        }
        try {
            storage.save(new Resume("uuid10001"));
            Assert.fail("Переполнение");
        } catch (StorageException ignored) {
        }
    }


    @Test(expected = ExistStorageException.class)
    public void getExistStorageException() {
        storage.save(new Resume("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}