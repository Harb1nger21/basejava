package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private SortedArrayStorage storage = new SortedArrayStorage();
    private final String TEST_UUID22 = "uuid22";
    private final Resume TEST_RESUME = new Resume(TEST_UUID22);

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void findIndex() {
        storage.save(TEST_RESUME);
        storage.save(new Resume("uuid5"));
        Assert.assertEquals(1, storage.findIndex("uuid5"));
        Assert.assertEquals(0,storage.findIndex(TEST_UUID22));
        Assert.assertEquals(-1, storage.findIndex("uuid1"));
    }

    @Test
    public void add() {
        storage.add(TEST_RESUME, storage.findIndex(TEST_UUID22));
        storage.countElements++;
        Assert.assertEquals(TEST_RESUME, storage.get(TEST_UUID22));

    }
}