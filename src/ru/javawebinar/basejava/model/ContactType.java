package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("телефон"),
    SOCIAL("социальные сети"),
    EMAIL("электронная почта");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}