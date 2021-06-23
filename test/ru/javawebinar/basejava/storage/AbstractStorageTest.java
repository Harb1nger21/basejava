package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume("Вика Красивая");
    private static final Resume RESUME_2 = new Resume("Антон Сладкий");
    private static final Resume RESUME_3 = new Resume("Катя Хитрая");
    private static final Resume RESUME_4 = new Resume("Рома Вредный");
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getTest() {
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllTest() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_2, RESUME_1, RESUME_3);
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    public void deleteTest() {
        storage.delete(RESUME_1.getUuid());
        assertEquals(Arrays.asList(RESUME_2, RESUME_3), storage.getAllSorted());
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void updateTest() {
        Resume expectedResume = new Resume(RESUME_2.getUuid(), "что-то новое");
        storage.update(expectedResume);
        assertEquals(expectedResume, storage.get(RESUME_2.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(RESUME_1);
    }
}