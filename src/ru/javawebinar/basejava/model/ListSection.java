package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection<List<String>> {
    public ListSection() {
        information = new ArrayList<>();
    }
}
