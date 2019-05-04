package services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import domain.Character;
import domain.Planet;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLDataAccessServiceImpl implements DataAccessService {

    private XmlMapper xmlMapper;

    public XMLDataAccessServiceImpl() {
        this.xmlMapper = new XmlMapper();
    }

    public List<Character> readCharacterData(String characterFilePath) throws IOException {
        List<Character> characters = new ArrayList<>();
        List<Object> values = xmlMapper.readValue(new File(characterFilePath), List.class);

        Map<Object, Object> value;
        Character character;

        for (int i = 0; i < values.size(); i++){
            value = (LinkedHashMap<Object, Object>) values.get(i);
            character = new Character(Integer.parseInt(value.get("id").toString()), value.get("name").toString(), value.get("description").toString(), Integer.parseInt(value.get("attack").toString()), Integer.parseInt(value.get("health").toString()), Boolean.parseBoolean(value.get("isVillain").toString()));
            characters.add(character);
        }

        return characters;
    }

    public List<Planet> readPlanetData(String planetFilePath) throws IOException {
        List<Planet> planets = new ArrayList<>();
        List<Object> values = xmlMapper.readValue(new File(planetFilePath), List.class);

        Map<Object, Object> value;
        Planet planet;

        for( int i = 0; i < values.size(); i++){
            value = (LinkedHashMap<Object, Object>) values.get(i);

            planet = new Planet(Integer.parseInt(value.get("id").toString()),
                    value.get("name").toString(),
                    value.get("description").toString(),
                    (LinkedHashMap<Object, Object>) value.get("modifiers"));
            planets.add(planet);
        }
        return planets;
    }
}
