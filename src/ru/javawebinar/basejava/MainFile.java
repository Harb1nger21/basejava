package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;

public class MainFile {

    public static void FilesList(File file) {
        if (file.isDirectory()) {
            System.out.println("\n" + file.getName() + " package");
            File[] files = file.listFiles();
            for (File fileName : files) {
                FilesList(fileName);
            }
        } else {
            System.out.println(file.getName() + " file");
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Victoriya\\Desktop\\Программирование\\java\\topjava\\basejava\\src\\ru\\javawebinar");
        System.out.println(file.getCanonicalPath());

        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
        System.out.println((file.isDirectory()));
        System.out.println(dir.getName());

        FilesList(dir);
    }
}
