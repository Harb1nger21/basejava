package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSection<Type> {
    protected Type information;

    public Type getInformation() {
        return information;
    }

    public void setInformation(Type information) {
        this.information = information;
    }

    @Override
    public String toString() {

        return  "{" +
                "information=" + information +
                '}';
    }


    public static class TextSection extends AbstractSection<String> {
        public TextSection() {
            information = "";
        }
    }

    public static class ListSection extends AbstractSection<List<String>> {
        public ListSection() {
            information = new ArrayList<>();
        }
    }

    public static class MapSection extends AbstractSection<Map<TimePeriod, Knowledge>> {
        public MapSection() {
            information = new HashMap<>();
        }
    }
}