import ui.Ui;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        String charactersJSON = "characters.json";
//        String charactersXML = "characters.xml";
//        String planetsJSON = "planets.json";
//        String planetsXML = "planets.xml";


        String charactersJSON = "D:\\Faculta\\Info\\Other Stuff\\CoreBuild\\SuperheroesWars\\src\\main\\resources\\characters.json";
        String charactersXML = "D:\\Faculta\\Info\\Other Stuff\\CoreBuild\\SuperheroesWars\\src\\main\\resources\\characters.xml";
        String planetsJSON = "D:\\Faculta\\Info\\Other Stuff\\CoreBuild\\SuperheroesWars\\src\\main\\resources\\planets.json";
        String planetsXML = "D:\\Faculta\\Info\\Other Stuff\\CoreBuild\\SuperheroesWars\\src\\main\\resources\\planets.xml";


        Ui ui;
        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File file = new File(jarFile.getParentFile().getParent(), "characters.json");
            ui = new Ui(charactersJSON, charactersXML, planetsJSON, planetsXML);
            ui.menu();
        }catch(FileNotFoundException fne){
            System.out.println("One of the input files is missing.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("One of the input files might be corrupted.");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
