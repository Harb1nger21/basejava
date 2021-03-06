package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStrategy implements Strategy {

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);

        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
