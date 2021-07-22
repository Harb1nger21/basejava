package ru.javawebinar.basejava.model;

public class Knowledge {
    private final String organization;
    private final String practice;

    public Knowledge(String organization, String practice) {
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
}