package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.abstractClass.AbstractStorage;
import ru.javawebinar.basejava.storage.strategy.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path path;
    private final Strategy strategy;

    protected PathStorage(Strategy strategy, String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        path = Paths.get(dir);
        this.strategy = strategy;
        if (!Files.isDirectory(path) || !Files.isWritable(path)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        getAsStream().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getAsStream().count();
    }

    @Override
    protected Path findKey(String uuid) {
        return path.resolve(uuid);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            strategy.writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, getFileName(path), e);
        }
        updateResume(resume, path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return strategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    protected List<Resume> getAsList() {
        return getAsStream().map(this::getResume).collect(Collectors.toList());
    }

    private Stream<Path> getAsStream() {
        try {
            return Files.list(path);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}