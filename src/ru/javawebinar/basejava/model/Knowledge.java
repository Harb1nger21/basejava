package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Knowledge {
    private final Organization organization;
    private final String practice;
    private String position;

    public Knowledge(Organization organization, String practice, String position) {
        Objects.requireNonNull(organization, "organization must not be null");
        Objects.requireNonNull(practice, "practice must not be null");
        Objects.requireNonNull(practice, "practice must not be null");
        this.organization = organization;
        this.practice = practice;
        this.position = position;
    }

    public Knowledge(Organization organization, String practice) {
        Objects.requireNonNull(organization, "organization must not be null");
        Objects.requireNonNull(practice, "practice must not be null");
        this.organization = organization;
        this.practice = practice;
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getPractice() {
        return practice;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knowledge knowledge = (Knowledge) o;
        return organization.equals(knowledge.organization) &&
                practice.equals(knowledge.practice) &&
                position.equals(knowledge.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, practice, position);
    }
}