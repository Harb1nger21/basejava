package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void add(Resume resume, int index) {
        storage[countElements] = resume;
    }

    @Override
    protected Object findElement(String uuid) {
        for (int i = 0; i < countElements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void existElementInStorage(Object element, String uuid) {
        if((int)element > -1){
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    protected void notExistElementInStorage(Object element, String uuid) {
        if((int)element <= -1){
            throw new NotExistStorageException(uuid);
        }
    }
}