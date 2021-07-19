package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class TimePeriod {
    private final LocalDate start;
    private final LocalDate finish;

    public TimePeriod(LocalDate start, LocalDate finish) {
        this.start = start;
        this.finish = finish;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getFinish() {
        return finish;
    }
}