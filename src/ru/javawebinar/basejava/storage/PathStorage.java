package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.abstractClass.AbstractStorage;
import ru.javawebinar.basejava.storage.strategy.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path path;
    private final Strategy strategy;

    protected PathStorage(Strategy strategy, String dir) {
        path = Paths.get(dir);
        Objects.requireNonNull(path, "directory must not be null");
        if (!Files.isDirectory(path) || !Files.isWritable(path)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        getAsStream(path).forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getAsStream(path).count();
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
        return path.toFile().exists();
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toAbsolutePath().toString(), path.getFileName().toString(), e);
        }
        updateResume(resume, path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return strategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> getAsList() {
        return getAsStream(path).map(this::getResume).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private Stream<Path> getAsStream(Path path) {
        Stream<Path> stream;
        try {
            stream = Files.list(path);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return stream;
    }
}