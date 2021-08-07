package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;

public class MainFile {

    public static void filesList(File file) {
        if (file.isDirectory()) {
            System.out.println("\n" + file.getName() + " package");
            File[] files = file.listFiles();
            assert files != null;
            for (File fileName : files) {
                filesList(fileName);
            }
        } else {
            String name = file.getName();
            System.out.println("    " + name + " file");
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File(".\\src\\ru\\javawebinar");
        System.out.println(file.getCanonicalPath());

        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
        System.out.println((file.isDirectory()));
        System.out.println(dir.getName());

        filesList(dir);
    }
}
