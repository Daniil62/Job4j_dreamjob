package ru.job4j.dreamjob.model;

import java.time.LocalDate;
import java.util.Objects;

public class Post {

    private int id;
    private String name;
    private String description;
    private LocalDate created;

    public Post() { }

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.created = LocalDate.now();
    }

    public Post(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = LocalDate.now();
    }

    public Post(int id, String name, String description, LocalDate created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
