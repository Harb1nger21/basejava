package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exeption.ExistStorageException;
import ru.javawebinar.basejava.exeption.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public class ListStorageTest {
    private ListStorage storage = new ListStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    @Before
    public void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void updateTest() {
        storage.update(RESUME_2);
        Assert.assertEquals(RESUME_2.getUuid(), storage.get(UUID_2).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4.getUuid(), storage.get(UUID_4).getUuid());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(RESUME_1);
    }

    @Test
    public void getTest() {
        Resume a = storage.get(UUID_1);
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_3);
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllTest() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}