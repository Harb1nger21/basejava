package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getTest() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAllTest() {
        Resume[] testStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] some = storage.getAll();
        Assert.assertArrayEquals(testStorage, storage.getAll());
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        Assert.assertArrayEquals(new Resume[]{new Resume(UUID_2), new Resume(UUID_3)}, storage.getAll());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void updateTest() {
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume(UUID_4));
    }

    @Test
    public void saveTest() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(new Resume(UUID_4), storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(new Resume(UUID_1));
    }
}