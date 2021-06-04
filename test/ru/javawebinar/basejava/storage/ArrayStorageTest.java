package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;


public class ArrayStorageTest extends AbstractArrayStorageTest {
    private ArrayStorage storage = new ArrayStorage();
    private final String TEST_UUID = "uuid22";
    private final Resume TEST_RESUME = new Resume(TEST_UUID);


    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    public void add() {
        storage.add(TEST_RESUME, 0);
        storage.countElements++;
        Assert.assertEquals(TEST_RESUME, storage.get(TEST_UUID));
    }

    @Test
    public void findIndex() {
        storage.save(TEST_RESUME);
        Assert.assertEquals(0, storage.findIndex(TEST_UUID));
        Assert.assertEquals(-1, storage.findIndex("uuid4"));
    }
}