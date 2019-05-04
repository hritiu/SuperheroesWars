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

    /**
     * Constructor
     * @param charactersPathJSON = the path to characters stored in a JSON file
     * @param charactersPathXML = the path to characters stored in a XML file
     * @param planetsPathJSON = the path to planets  stored in a JSON file
     * @param planetsPathXMl = the path to planets stored in a XML file
     */
    public Controller(String charactersPathJSON, String charactersPathXML, String planetsPathJSON, String planetsPathXMl) {
        this.charactersPathJSON = charactersPathJSON;
        this.charactersPathXML = charactersPathXML;
        this.planetsPathJSON = planetsPathJSON;
        this.planetsPathXMl = planetsPathXMl;
        this.repository = new Repository();
    }

    /**
     * Planet getter
     * @param planetID = the id of the planet to be returned
     * @return the planet which has the id corresponding to the given id
     */
    public Planet getPlanet(int planetID){return this.repository.getPlanet(planetID);}

    /**
     * Character getter
     * @param characterID = the id of the character to be returned
     * @return the character which has the id corresponding to the given id
     */
    public Character getCharacter(int characterID) {return this.repository.getCharacter(characterID);}

    /**
     * Villains getter
     * @return a list of characters containing all the villains
     */
    public List<Character> getVillains(){
        return this.repository.getVillains();
    }

    /**
     * Heroes getter
     * @return a list of characters containing all the heroes
     */
    public List<Character> getHeroes(){
        return this.repository.getHeroes();
    }

    /**
     * Planets getter
     * @return a list of planets containing all the planets
     */
    public List<Planet> getPlanets(){
        return this.repository.getPlanets();
    }

    /**
     * Splits the characters into a list of heroes and a list o villains. This two lists correspond to the repository fields
     * heroes and villains
     */
    private void splitCharacters(){
        this.repository.splitCharacters();
    }

    /**
     * Reads the characters from a JSON file
     * @throws IOException if the file is corrupted
     */
    public void readCharactersJSONData() throws IOException {
        this.repository.readCharactersFromJSON(charactersPathJSON);
        this.splitCharacters();
    }

    /**
     * Reads the plantes from a JSON file
     * @throws IOException if the file is corrupted
     */
    public void readPlanetsJSONData() throws IOException {
        this.repository.readPlanetsFromJSON(planetsPathJSON);
    }

    /**
     * Reads the characters from a XML file
     * @throws IOException if the file is corrupted
     */
    public void readCharactersXMLData() throws IOException {
        this.repository.readCharactersFromXML(charactersPathXML);
        this.splitCharacters();
    }

    /**
     * Reads the planets from a XML file
     * @throws IOException if the file is corrupted
     */
    public void readPlanetsXMLData() throws IOException {
        this.repository.readPlanetsFromXML(planetsPathXMl);
    }

    /**
     * Generates a random number between 60 and 100
     * @return an integer number
     */
    private int randomAttack(){
        Random random = new Random();
        float percentage;
        int randomNumber;

        randomNumber = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;
        percentage = (float) randomNumber / 100;

        return Math.round(percentage);
    }

    /**
     * Simulates a fight between a hero and a villain
     * @param hero = the hero which will fight
     * @param villain = the villain which will fight
     * @param planet = the planet on which the fight will take place
     * @return an object of type FightDetails which contains all the details of the battle
     */
    public FightDetails heroVsVillainFight(Character hero, Character villain, Planet planet){
        FightDetails fightDetails = new FightDetails();
        Round round;

        //the hero's health and attack are modified according to the planet on which the fight takes place
        hero.setAttack(hero.getAttack() + Integer.parseInt(planet.getModifiers().get("heroAttackModifier").toString()));
        hero.setHealth(hero.getHealth() + Integer.parseInt(planet.getModifiers().get("heroHealthModifier").toString()));

        //the villain's health and attack are modified according to the planet on which the fight takes place
        villain.setAttack(villain.getAttack() + Integer.parseInt(planet.getModifiers().get("villainAttackModifier").toString()));
        villain.setHealth(villain.getHealth() + Integer.parseInt(planet.getModifiers().get("villainHealthModifier").toString()));

        //the stats of the characters before the fight are saved
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

    /**
     * Counts the number of heroes alive
     * @param heroes = a list of characters containing all the heroes
     * @return an integer number
     */
    public int nbOfHeroesAlive(List<Character> heroes){
        int nbOfHeroes = 0;

        for (Character character : heroes) {
            if(!character.getIsVillain() && character.getHealth() > 0)
                nbOfHeroes += 1;
        }

        return nbOfHeroes;
    }

    /**
     * Checks if all heroes are dead
     * @param characters = a list of characters
     * @return true if all characters are dead; false otherwise
     */
    public boolean allHeroesDead(List<Character> characters){
        for (Character character : characters) {
            if (!character.getIsVillain() && character.getHealth() > 0)
                return false;
        }
        return true;
    }

    /**
     * Checks if all villains are dead
     * @param villains = a list of characters containing all the villains
     * @return true if all villains are dead; false otherwise
     */
    public boolean allVillainsDead(List<Character> villains){
        for (Character character: villains) {
            if(character.getIsVillain() && character.getHealth() > 0)
                return false;
        }
        return true;
    }

    /**
     * Simulates a fight between a team of avengers and a villain
     * @param avengers = a list of characters containing a team of heroes
     * @param villain =  the villain which will fight
     * @param planet = the planet on which the fight will take place
     * @return an object of type FightDetails which contains all the details of the battle
     */
    public FightDetails avengersVsVillainFight(List<Character> avengers, Character villain, Planet planet){
        int heroPositionInTeam = 0, heroAttack = 0, villainAttack = 0;
        Character hero;

        FightDetails fightDetails = new FightDetails();
        Round round;

        //the heroes' and the villain's health and attack will be modified according to the planet on which the fight will take place
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
