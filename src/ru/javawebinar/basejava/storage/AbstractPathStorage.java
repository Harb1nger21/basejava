package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
   if(!Files.isDirectory(directory) || !Files.isWritable(directory)){
       throw new IllegalArgumentException(dir + " is not directory or is not writable");
   }
    }

    @Override
    public void clear(){
        try {
            Files.list(directory).forEach(this::deleteResume);
        }catch(IOException e){
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path findKey(String uuid) {
        return Paths.get(directory.toString()+"\\"+uuid);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
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
            path.toFile().createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toFile().getAbsolutePath(), path.getFileName().toString(), e);
        }
        updateResume(resume, path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        if (!path.toFile().delete()) {
            throw new StorageException("File delete error", path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> getAsList() {
        File[] files = directory.toFile().listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(getResume(file.toPath()));
        }
        return list;
    }

    protected abstract void writeResume(Resume r, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}