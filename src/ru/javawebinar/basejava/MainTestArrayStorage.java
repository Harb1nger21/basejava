package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
//        Resume r1 = new Resume("uuid1", personal, personal, objective);
//        Resume r2 = new Resume("uuid2", personal, personal, objective);
//        Resume r3 = new Resume("uuid3", personal, personal, objective);
//        Resume r4 = new Resume("uuid4", personal, personal, objective);
//        Resume r5 = new Resume("uuid4", personal, personal, objective);
//        Resume r6 = new Resume("uuid6", personal, personal, objective);

//        ARRAY_STORAGE.save(r4);
//        ARRAY_STORAGE.save(r1);
//        ARRAY_STORAGE.save(r3);
//        ARRAY_STORAGE.save(r2);
//        ARRAY_STORAGE.save(r5);
//        ARRAY_STORAGE.save(r6);

       // System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

       // System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //printAll();
     //   ARRAY_STORAGE.delete(r1.getUuid());
        //printAll();
//        ARRAY_STORAGE.update(r4);
        //printAll();
        ARRAY_STORAGE.clear();
        //printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

//    static void printAll() {
//        System.out.println("\nGet All");
//        for (Resume r : ARRAY_STORAGE.getAll()) {
//            System.out.println(r);
//        }
//    }
}