package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.TestData.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
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
        assertEquals(RESUME_1, storage.get(UUID_1));
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
    public void getAllSortedTest() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        expectedResumes.sort(Resume::compareTo);
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        List<Resume> expectedResumes = Arrays.asList(RESUME_2, RESUME_3);
        expectedResumes.sort(Resume::compareTo);
        assertEquals(expectedResumes, storage.getAllSorted());
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void updateTest() {
        Resume expectedResume1 = ResumeTestData.createResume(UUID_1, "что-то новое1");
        Resume expectedResume2 = ResumeTestData.createResume(UUID_2, "что-то новое2");
        Resume expectedResume3 = new Resume(UUID_3, "что-то новое3");
        storage.update(expectedResume1);
        storage.update(expectedResume2);
        storage.update(expectedResume3);
        Resume actual = storage.get(UUID_1);
        assertEquals(expectedResume1, actual);
        assertEquals(expectedResume2, storage.get(UUID_2));
        assertEquals(expectedResume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(RESUME_2);
    }
}