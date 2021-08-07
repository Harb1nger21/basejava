package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Organization {
    private final String name;
    private String webSite = "";

    public Organization(String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
    }

    public Organization(String name, String webSite) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.webSite = webSite;
    }

    public String getName() {
        return name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) &&
                Objects.equals(webSite, that.webSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, webSite);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", webSite='" + webSite + '\'' +
                '}';
    }
}
