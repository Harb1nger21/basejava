package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainFile {
    private static int depth = 0;
    private final static String INDENTATION = "    ";

    public static void main(String[] args) throws IOException {
        File file = new File(".\\src\\ru\\javawebinar");
        System.out.println(file.getCanonicalPath());

        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
        System.out.println((file.isDirectory()));
        System.out.println(dir.getName());

        filesList(dir);
    }

    public static void filesList(File file) {
        if (file.isDirectory()) {
            System.out.println(INDENTATION.repeat(depth++) + file.getName() + " package");
            List<File> files = Arrays.asList(Objects.requireNonNull(file.listFiles()));
            files.sort(Comparator.comparing(File::isFile));
            if (files.size() == 0) {
                depth--;
            }
            for (int i = 0; i < files.size(); i++) {
                filesList(files.get(i));
                if (i == files.size() - 1) {
                    depth--;
                }
            }
        } else {
            System.out.println(INDENTATION.repeat(depth) + file.getName() + " file");
        }
    }
}
