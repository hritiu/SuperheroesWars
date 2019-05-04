package domain;

public class Character {

    private int id;
    private String name;
    private String description;
    private int attack;
    private int health;
    private boolean isVillain;

    public Character(int id, String name, String description, int attack, int health, boolean isVillain) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attack = attack;
        this.health = health;
        this.isVillain = isVillain;
    }

    public Character(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean getIsVillain() {
        return this.isVillain;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attack=" + attack +
                ", health=" + health +
                ", isVillain=" + isVillain +
                '}';
    }
}
