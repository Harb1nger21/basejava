package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: resume with " + uuid + " is already in\n", uuid);
    }
}