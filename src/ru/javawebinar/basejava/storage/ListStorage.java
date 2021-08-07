package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Integer findKey(String uuid) {
        for(int i = 0; i < storage.size(); i++){
            if(storage.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    protected void saveResume(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected Resume getOut(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteResume(Integer index) {
        storage.remove((int)index);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != -1;
    }

    @Override
    protected List<Resume> getAsList() {
        return storage;
    }
}