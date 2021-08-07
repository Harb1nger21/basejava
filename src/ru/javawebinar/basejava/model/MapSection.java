package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.TimePeriod;

import java.util.HashMap;
import java.util.Map;

public class MapSection extends AbstractSection<Map<TimePeriod, Knowledge>> {
    public MapSection() {
        information = new HashMap<>();
    }
}
