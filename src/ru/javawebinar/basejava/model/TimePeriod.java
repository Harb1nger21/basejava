package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class TimePeriod {
    private final YearMonth start;
    private final YearMonth finish;

    public TimePeriod(YearMonth start, YearMonth finish) {
        this.start = start;
        this.finish = finish;
    }

    public YearMonth getStart() {
        return start;
    }

    public YearMonth getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "start=" + start +
                ", finish=" + finish +
                '}';
    }
}