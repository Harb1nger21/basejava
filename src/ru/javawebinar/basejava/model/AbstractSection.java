package ru.javawebinar.basejava.model;

import java.util.*;

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

        return "{" +
                "information=" + information +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSection<?> that = (AbstractSection<?>) o;
        return Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information);
    }
}