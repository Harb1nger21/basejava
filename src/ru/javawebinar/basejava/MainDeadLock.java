package ru.javawebinar.basejava;

public class MainDeadLock {
    private static final int seven = 7;
    private static final int five = 5;
    private static final Thread firstThread = new Thread() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            incForFirstThread();
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }
    };

    private static final Thread secondThread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            incForSecondThread();
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }
    });

    private static synchronized int incForFirstThread() {
        return seven + incForSecondThread();
    }

    private static synchronized int incForSecondThread() {
        return five + incForFirstThread();
    }

    public static void main(String[] args) {
        firstThread.start();
        secondThread.start();
    }
}
