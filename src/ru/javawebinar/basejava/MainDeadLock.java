package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

public class MainDeadLock {
    private final static Resume firstResume = ResumeTestData.createResume("uuid1", "Антон");
    private final static Resume secondResume = ResumeTestData.createResume("uuid2", "Вика");

    private static final Thread firstThread = new Thread() {
        @Override
        public void run() {
            printResume(firstResume, secondResume);
        }
    };

    private static final Thread secondThread = new Thread(new Runnable() {
        @Override
        public void run() {
            printResume(secondResume, firstResume);
        }
    });


    private static synchronized void printResume(Resume firstResume, Resume secondResume) {
        String firstResumeInfo = firstResume.getUuid()
                + firstResume.getFullName()
                + firstResume.getContacts()
                + firstResume.getSections();

        String secondResumeInfo = secondResume.getUuid()
                + secondResume.getFullName()
                + secondResume.getContacts()
                + secondResume.getSections();

        System.out.println(firstResumeInfo);
        System.out.println(secondResumeInfo);
    }


    public static void main(String[] args) {
        firstThread.start();
        secondThread.start();
    }
}