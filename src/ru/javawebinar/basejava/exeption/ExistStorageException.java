package ru.javawebinar.basejava.exeption;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: resume with %s is already in\n", uuid);
    }
}
