package services;

public class Round {

    private String heroName;
    private int heroAttack;
    private int heroHealth;
    private int heroDamageTaken;
    private String villainName;
    private int villainAttack;
    private int villainHealth;
    private int villainDamageTaken;

    public Round(String heroName, int heroAttack, int heroHealth, int heroDamageTaken, String villainName, int villainAttack, int villainHealth, int villainDamageTaken) {
        this.heroName = heroName;
        this.heroAttack = heroAttack;
        this.heroHealth = heroHealth;
        this.heroDamageTaken = heroDamageTaken;
        this.villainName = villainName;
        this.villainAttack = villainAttack;
        this.villainHealth = villainHealth;
        this.villainDamageTaken = villainDamageTaken;
    }

    public void printRound(){
        System.out.println();
        System.out.println("<<<<< HERO >>>>>");
        System.out.println("    NAME: " + this.heroName);
        System.out.println("    ATTACK: " + this.heroAttack);
        System.out.println("    HEALTH: " + this.heroHealth);
        System.out.println("    DAMAGE TAKEN: " + this.heroDamageTaken);

        System.out.println();
        System.out.println("<<<<< VILLAIN >>>>>");
        System.out.println("    NAME: " + this.villainName);
        System.out.println("    ATTACK: " + this.villainAttack);
        System.out.println("    HEALTH: " + this.villainHealth);
        System.out.println("    DAMAGE TAKEN: " + this.villainDamageTaken);
    }

}
