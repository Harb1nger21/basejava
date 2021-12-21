package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Вика Красивая");
    public static final Resume RESUME_2 = new Resume(UUID_2, "Антон Сладкий");
    public static final Resume RESUME_3 = new Resume(UUID_3, "Катя Хитрая");
    public static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Рома Вредный");
}
