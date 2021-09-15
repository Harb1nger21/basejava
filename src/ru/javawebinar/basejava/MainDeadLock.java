package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        firstThread.start();
        secondThread.start();
    }

    private static String firstString = "первая строка";
    private static String secondString = "вторая строка";

    private static final Thread firstThread = new Thread() {
        @Override
        public void run() {
            runFirstThread("новая");
        }
    };
    private static final Thread secondThread = new Thread(new Runnable() {
        @Override
        public void run() {
            runSecondThread("обновленная");
        }
    });

    private static synchronized void runFirstThread(String string) {
        firstString = string + " " + firstString;
        runSecondThread(string);
    }

    private static synchronized void runSecondThread(String string) {
        secondString = string + " " + secondString;
        runFirstThread(string);
    }
}