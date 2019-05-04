package controller;

import domain.Character;
import domain.Planet;
import repository.Repository;
import services.FightDetails;
import services.Round;

import java.io.IOException;
import java.util.*;

public class Controller {

    private String charactersPathJSON;
    private String charactersPathXML;
    private String planetsPathJSON;
    private String planetsPathXMl;
    private Repository repository;
    private final int MAX_DAMAGE = 100;
    private final int MIN_DAMAGE = 60;

    public Controller(String charactersPathJSON, String charactersPathXML, String planetsPathJSON, String planetsPathXMl) {
        this.charactersPathJSON = charactersPathJSON;
        this.charactersPathXML = charactersPathXML;
        this.planetsPathJSON = planetsPathJSON;
        this.planetsPathXMl = planetsPathXMl;
        this.repository = new Repository();
    }

    public Planet getPlanet(int planetID){return this.repository.getPlanet(planetID);}

    public Character getCharacter(int characterID) {return this.repository.getCharacter(characterID);}

    public List<Character> getVillains(){
        return this.repository.getVillains();
    }

    public List<Character> getHeroes(){
        return this.repository.getHeroes();
    }

    public List<Planet> getPlanets(){
        return this.repository.getPlanets();
    }

    private void splitCharacters(){
        this.repository.splitCharacters();
    }

    public void readCharactersJSONData() throws IOException {
        this.repository.readCharactersFromJSON(charactersPathJSON);
        this.splitCharacters();
    }

    public void readPlanetsJSONData() throws IOException {
        this.repository.readPlanetsFromJSON(planetsPathJSON);
    }

    public void readCharactersXMLData() throws IOException {
        this.repository.readCharactersFromXML(charactersPathXML);
        this.splitCharacters();
    }

    public void readPlanetsXMLData() throws IOException {
        this.repository.readPlanetsFromXML(planetsPathXMl);
    }

    private int randomAttack(){
        Random random = new Random();
        float percentage;
        int randomNumber;

        randomNumber = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;
        percentage = (float) randomNumber / 100;

        return Math.round(percentage);
    }

    public FightDetails heroVsVillainFight(Character hero, Character villain, Planet planet){
        FightDetails fightDetails = new FightDetails();
        Round round;

        hero.setAttack(hero.getAttack() + Integer.parseInt(planet.getModifiers().get("heroAttackModifier").toString()));
        hero.setHealth(hero.getHealth() + Integer.parseInt(planet.getModifiers().get("heroHealthModifier").toString()));

        villain.setAttack(villain.getAttack() + Integer.parseInt(planet.getModifiers().get("villainAttackModifier").toString()));
        villain.setHealth(villain.getHealth() + Integer.parseInt(planet.getModifiers().get("villainHealthModifier").toString()));

        round = new Round(hero.getName(), hero.getAttack(), hero.getHealth(), 0, villain.getName(), villain.getAttack(), villain.getHealth(), 0);
        fightDetails.addRound(round);

        int heroAttack, villainAttack;

        while ( hero.getHealth() > 0 && villain.getHealth() > 0){
            heroAttack = this.randomAttack() * hero.getAttack();
            villainAttack = this.randomAttack() * villain.getAttack();

            hero.setHealth(hero.getHealth() - villainAttack);
            villain.setHealth(villain.getHealth() - heroAttack);
            round = new Round(hero.getName(), hero.getAttack(), hero.getHealth(), villainAttack, villain.getName(), villain.getAttack(), villain.getHealth(), heroAttack);
            fightDetails.addRound(round);
        }

        this.repository.updateHero(hero);
        this.repository.updateVillain(villain);

        return fightDetails;
    }

    public int nbOfHeroesAlive(List<Character> heroes){
        int nbOfHeroes = 0;

        for (Character character : heroes) {
            if(!character.getIsVillain() && character.getHealth() > 0)
                nbOfHeroes += 1;
        }

        return nbOfHeroes;
    }

    public boolean allHeroesDead(List<Character> characters){
        for (Character character : characters) {
            if (!character.getIsVillain() && character.getHealth() > 0)
                return false;
        }
        return true;
    }

    public boolean allVillainsDead(List<Character> villains){
        for (Character character: villains) {
            if(character.getIsVillain() && character.getHealth() > 0)
                return false;
        }
        return true;
    }

    public FightDetails avengersVsVillainFight(List<Character> avengers, Character villain, Planet planet){
        int heroPositionInTeam = 0, heroAttack = 0, villainAttack = 0;
        Character hero;

        FightDetails fightDetails = new FightDetails();
        Round round;

        for (Character character : avengers) {
            character.setAttack(character.getAttack() + Integer.parseInt(planet.getModifiers().get("heroAttackModifier").toString()));
            character.setHealth(character.getHealth() + Integer.parseInt(planet.getModifiers().get("heroHealthModifier").toString()));
        }

        villain.setAttack(villain.getAttack() + Integer.parseInt(planet.getModifiers().get("villainAttackModifier").toString()));
        villain.setHealth(villain.getHealth() + Integer.parseInt(planet.getModifiers().get("villainHealthModifier").toString()));

        while (this.nbOfHeroesAlive(avengers) > 0 && villain.getHealth() > 0){
            hero = avengers.get(heroPositionInTeam);
            while (hero.getHealth() <= 0){
                heroPositionInTeam += 1;
                hero = avengers.get(heroPositionInTeam);
            }

            heroAttack = this.randomAttack() * hero.getAttack();
            villainAttack = this.randomAttack() * villain.getAttack();

            hero.setHealth(hero.getHealth() - villainAttack);
            villain.setHealth(villain.getHealth() - heroAttack);

            round = new Round(hero.getName(), heroAttack, hero.getHealth(), villainAttack, villain.getName(), villainAttack, villain.getHealth(), heroAttack);
            fightDetails.addRound(round);

            if(heroPositionInTeam == avengers.size() - 1)
                heroPositionInTeam = 0;
            else
                heroPositionInTeam += 1;
        }

        for (Character character : avengers) {
            this.repository.updateHero(character);
        }
        this.repository.updateVillain(villain);

        return fightDetails;
    }
}
