package ru.javawebinar.basejava.exeption;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("ERROR: resume with " + uuid + " is not found\n", uuid);
    }
}
