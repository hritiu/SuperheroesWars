package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Character;
import domain.Planet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONDataAccessServiceImpl implements DataAccessService{

    private ObjectMapper objectMapper;

    public JSONDataAccessServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Character> readCharacterData(String characterFilePath) throws IOException {
        return objectMapper.readValue(new File(characterFilePath), new TypeReference<List<Character>>() {});
    }

    public List<Planet> readPlanetData(String planetFilePath) throws IOException {
        return objectMapper.readValue(new File(planetFilePath), new TypeReference<List<Planet>>(){});
    }
}
