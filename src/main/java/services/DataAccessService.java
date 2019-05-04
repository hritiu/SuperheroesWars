package services;

import domain.Character;
import domain.Planet;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public interface DataAccessService {

    List<Character> readCharacterData(String characterFilePath) throws IOException, SAXException;
    List<Planet> readPlanetData(String planetFilePath) throws IOException, SAXException;
}
