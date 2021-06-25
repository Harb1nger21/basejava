package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {
    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update " + resume);
        doUpdate(resume, getKeyIfResumeExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save " + resume);
        SearchKey key = getKeyIfResumeNotExist(resume.getUuid());
        saveIn(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        return getOut(getKeyIfResumeExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        deleteResume(getKeyIfResumeExist(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("GetAllSorted");
        List<Resume> sortedList = convertToList();
        sortedList.sort(Resume::compareTo);
        return sortedList;
    }

    private SearchKey getKeyIfResumeExist(String uuid) {
        SearchKey key = findKey(uuid);
        if (!isExist(key)) {
            LOGGER.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SearchKey getKeyIfResumeNotExist(String uuid) {
        SearchKey key = findKey(uuid);
        if (isExist(key)) {
            LOGGER.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> convertToList();

    protected abstract SearchKey findKey(String uuid);

    protected abstract void doUpdate(Resume resume, SearchKey key);

    protected abstract void saveIn(Resume resume, SearchKey key);

    protected abstract Resume getOut(SearchKey key);

    protected abstract void deleteResume(SearchKey key);

    protected abstract boolean isExist(SearchKey key);
}