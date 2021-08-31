package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Вика Красивая");
    private static final Resume RESUME_2 = new Resume(UUID_2,"Антон Сладкий");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Катя Хитрая");
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Григорий Кислин");
    private static final Resume RESUME_5 = ResumeTestData.createResume(UUID_5, "Рома Вредный");
    protected final Storage storage;
    protected static final File STORAGE_DIR = new File("C:\\Users\\Victoriya\\Desktop\\Программирование\\java\\storage");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_4);
    }

    @Test
    public void sizeTest() {
        assertEquals(4, storage.size());
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
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_4);
        expectedResumes.sort(Resume::compareTo);
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        List<Resume> expectedResumes = Arrays.asList(RESUME_2, RESUME_3, RESUME_4);
        expectedResumes.sort(Resume::compareTo);
        assertEquals(expectedResumes, storage.getAllSorted());
        assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_5);
    }

    @Test
    public void updateTest() {
        Resume expectedResume = ResumeTestData.createResume(UUID_2, "что-то новое");
        storage.update(expectedResume);
        assertEquals(expectedResume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_5);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_5);
        assertEquals(RESUME_5, storage.get(UUID_5));
        assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(RESUME_1);
    }
}