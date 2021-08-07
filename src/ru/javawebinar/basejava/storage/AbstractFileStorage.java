package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] filesList = directory.listFiles();
        for (int i = 1; i < Objects.requireNonNull(filesList).length; i++) {
            filesList[i] = null;
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list()).length - 1;
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        File[] listFiles = directory.listFiles();
        for (int i = 1; i < Objects.requireNonNull(listFiles).length; i++) {
            if (listFiles[i].equals(file)) {
                try {
                    listFiles[i] = convertToFile(resume);
                } catch (IOException e) {
                    throw new StorageException("IO error", file.getName(), e);
                }
            }
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getOut(File resume) {
        Resume foundResumeFile = null;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.equals(resume)) {
                try {
                    foundResumeFile = convertToResume(file);
                } catch (IOException e) {
                    throw new StorageException("IO error", file.getName(), e);
                }
            }
        }
        return foundResumeFile;
    }

    @Override
    protected void deleteResume(File resume) {
        File[] listFiles = directory.listFiles();
        for (int i = 1; i < Objects.requireNonNull(listFiles).length; i++) {
            if (listFiles[i].equals(resume)) {
                listFiles[i].delete();
            }
        }
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAsList() {
        File[] listFiles = directory.listFiles();
        List<Resume> resumes = new ArrayList<>();
        for (int i = 1; i < Objects.requireNonNull(listFiles).length; i++) {
            try {
                resumes.add(convertToResume(listFiles[i]));
            } catch (IOException e) {
                throw new StorageException("IO error", listFiles[i].getName(), e);
            }
        }
        return resumes;
    }

    protected abstract void writeResume(Resume resume, File file) throws IOException;

    protected abstract Resume convertToResume(File file) throws IOException;

    protected abstract File convertToFile(Resume resume) throws IOException;
}
