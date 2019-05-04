package repository;

import domain.Character;
import domain.Planet;
import services.JSONDataAccessServiceImpl;
import services.XMLDataAccessServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private List<Character> characters;
    private List<Planet> planets;
    private List<Character> villains;
    private List<Character> heroes;
    private JSONDataAccessServiceImpl jsonDataAccessService;
    private XMLDataAccessServiceImpl xmlDataAccessService;

    public Repository() {
        this.jsonDataAccessService = new JSONDataAccessServiceImpl();
        this.xmlDataAccessService = new XMLDataAccessServiceImpl();
    }

    public List<Character> getVillains() { return villains; }

    public List<Character> getHeroes() { return heroes; }

    public List<Planet> getPlanets() {
        return this.planets;
    }

    public void readCharactersFromJSON(String filePath) throws IOException {
        characters = new ArrayList<>();
        characters = jsonDataAccessService.readCharacterData(filePath);
    }

    public void readPlanetsFromJSON(String filePath) throws IOException {
        planets = new ArrayList<>();
        planets = jsonDataAccessService.readPlanetData(filePath);
    }

    public void readCharactersFromXML(String filePath) throws IOException {
        characters = xmlDataAccessService.readCharacterData(filePath);
    }

    public void readPlanetsFromXML(String filePath) throws IOException {
        planets = xmlDataAccessService.readPlanetData(filePath);
    }

    public Character getCharacter(int characterID){
        int position = 0;
        boolean found = false;
        Character character = null;

        while (position < this.characters.size() && found == false){
            if(this.characters.get(position).getId() == characterID){
                character = characters.get(position);
                found=true;
            }
            else
                position += 1;
        }
        return character;
    }

    public Planet getPlanet(int planetID){
        int position = 0;

        while (position < this.planets.size()){
            if(this.planets.get(position).getId() == planetID)
                return planets.get(position);
            else
                position += 1;
        }

        return null;
    }

    public void splitCharacters(){
        this.villains = new ArrayList<>();
        this.heroes = new ArrayList<>();

        this.characters.stream().forEach(character -> {
            if(character.getIsVillain())
                this.villains.add(character);
            else
                this.heroes.add(character);
        });
    }

    public void updateHero(Character hero){
        int position = 0;

        while (this.heroes.get(position).getId() != hero.getId() && position < this.heroes.size())
            position += 1;

        if(position < this.heroes.size()){
            this.heroes.set(position, hero);
        }
    }

    public void updateVillain(Character villain){
        int position = 0;

        while (this.villains.get(position).getId() != villain.getId() && position < this.villains.size())
            position += 1;

        if(position < this.villains.size()){
            this.villains.set(position, villain);
        }
    }
}
