package domain;

import java.util.Map;

public class Planet {

    private int id;
    private String name;
    private String description;
    private Map<Object, Object> modifiers;

    public Planet(int id, String name, String description, Map<Object, Object> modifiers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modifiers = modifiers;
    }

    public Planet() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Object, Object> getModifiers() {
        return modifiers;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modifiers=" + modifiers +
                '}';
    }
}
