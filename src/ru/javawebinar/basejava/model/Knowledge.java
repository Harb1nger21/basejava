package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Knowledge {
    private final String organization;
    private final String practice;

    public Knowledge(String organization, String practice) {
        Objects.requireNonNull(organization, "organization must not be null");
        Objects.requireNonNull(practice, "practice must not be null");
        this.organization = organization;
        this.practice = practice;
    }

    public String getOrganization() {
        return organization;
    }

    public String getPractice() {
        return practice;
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "organization='" + organization + '\'' +
                ", practice='" + practice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knowledge knowledge = (Knowledge) o;
        return organization.equals(knowledge.organization) &&
                practice.equals(knowledge.practice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, practice);
    }
}