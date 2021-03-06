package ru.javawebinar.basejava;

public class MainDeadLock {
    private static final String firstString = "первая строка";
    private static final String secondString = "вторая строка";

    private static final Thread firstThread = new Thread(() -> deadLock(firstString, secondString));
    private static final Thread secondThread = new Thread(() -> deadLock(secondString, firstString));

    public static void main(String[] args) {
        firstThread.start();
        secondThread.start();
    }

    private static void deadLock(String string1, String string2) {
        synchronized (string1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " captured " + string1.getClass().getName() + " \"" + string1 + "\"");
            System.out.println(Thread.currentThread().getName() + " waiting for capture " + string1.getClass().getName() + " \"" + string2 + "\"");
            synchronized (string2) {
                System.out.println("deadlock");
            }
        }
    }
}