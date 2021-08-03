package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class TimePeriod {
    private final YearMonth start;
    private final YearMonth finish;

    public TimePeriod(YearMonth start, YearMonth finish) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(finish, "finish must not be null");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod that = (TimePeriod) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(finish, that.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish);
    }
}