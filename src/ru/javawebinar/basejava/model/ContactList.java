package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ContactList {
    private final List<String> contacts = new ArrayList<>();

    public List<String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "ContactList{" +
                "contacts=" + contacts +
                '}';
    }
}