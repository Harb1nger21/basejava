package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Section<Type> {
    protected Type information;

    public Type getInformation() {
        return information;
    }

    public void setInformation(Type information) {
        this.information = information;
    }


    public static class TextSection extends Section<String> {
        public TextSection() {
            information = "";
        }
    }

    public static class ListSection extends Section<List<String>> {
        public ListSection() {
            information = new ArrayList<>();
        }
    }

    public static class MapSection extends Section<Map<TimePeriod, Knowledge>> {
        public MapSection() {
            information = new HashMap<>();
        }
    }
}