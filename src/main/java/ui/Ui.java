package ui;

import domain.Character;
import domain.Planet;
import controller.Controller;
import services.FightDetails;
import java.io.IOException;
import java.util.*;

public class Ui {
    private final int MIN_NO_OF_AVENGERS = 2;

    private Controller controller;
    private Scanner scanner;

    public Ui(String charactersPathJSON, String charactersPathXML, String planetsPathJSON, String planetsPathXMl){
        this.controller = new Controller(charactersPathJSON, charactersPathXML, planetsPathJSON, planetsPathXMl);
        this.scanner = new Scanner(System.in);
    }

    private boolean isInteger(String stringNumber){
        try {
            Integer.parseInt(stringNumber);
        }catch (NumberFormatException numberFormatException){
            return false;
        }

        return true;
    }

    private int readCommand(){
        boolean valid = false;
        int value = -1;
        String command = "";

        while (!valid){
            System.out.print(">>>>>>>>>>COMMAND: ");
            command = scanner.nextLine();

            if(this.isInteger(command)){
                value = Integer.parseInt(command);
                valid = true;
            }
            else
                System.out.println("Invalid command! Please try again.");
        }

        return value;
    }

    private void inputFileMenu(String entityType) throws IOException {
        Random random = new Random();
        int value = -1;
        boolean valid = false;

        System.out.println("1. XML");
        System.out.println("2. JSON");
        System.out.println("3. Random");

        while (!valid){
            value = this.readCommand();
            if(value < 1 || value > 3)
                System.out.println("This is not a valid option!");
            else
                valid = true;
        }

        if(value == 3)
            value = random.nextInt(2) + 1;

        if(Objects.equals(entityType, "character")){
            if(value == 1)
                this.controller.readCharactersXMLData();
            else
                this.controller.readCharactersJSONData();
        }
        else if(Objects.equals(entityType, "planet")) {
            if(value == 1)
                this.controller.readPlanetsXMLData();
            else
                this.controller.readPlanetsJSONData();
        }

        System.out.println();
    }

    private void printPlanets(List<Planet> planets){
        System.out.println("<><><><><><><><><><><><><><><><>");
        planets.stream().forEach(planet -> {
            System.out.println("ID: " + planet.getId());
            System.out.println("Name: " + planet.getName());
            System.out.println("Description: " + planet.getDescription());
            System.out.println("Modifiers: ");
            System.out.println("    HERO ATTACK MODIFIER: " + planet.getModifiers().get("heroAttackModifier").toString());
            System.out.println("    HERO HEALTH MODIFIER: " + planet.getModifiers().get("heroHealthModifier").toString());
            System.out.println("    VILLAIN ATTACK MODIFIER: " + planet.getModifiers().get("villainAttackModifier").toString());
            System.out.println("    VILLAIN HEALTH MODIFIER: " + planet.getModifiers().get("villainHealthModifier").toString());
            System.out.println("<><><><><><><><><><><><><><><><>");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void printCharacters(List<Character> characters){
        System.out.println("<><><><><><><><><><><><><><><><>");
        characters.stream().forEach(character -> {
            System.out.println("ID: " + character.getId());
            System.out.println("Name: " + character.getName());
            System.out.println("Description: " + character.getDescription());
            System.out.println("Attack: " + character.getAttack());
            System.out.println("Health: " + character.getHealth());
            System.out.println("Is villain: " + character.getIsVillain());
            System.out.println("<><><><><><><><><><><><><><><><>");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private Planet selectPlanet(){
        boolean valid = false;
        Planet planet = new Planet();
        int value = -1;

        System.out.println();
        System.out.println("Enter the ID of the planet you want to select: ");
        this.printPlanets(this.controller.getPlanets());

        while (!valid){
            value = this.readCommand();
            planet = this.controller.getPlanet(value);

            if(planet == null)
                System.out.println("There is no planet with this ID! Please try again.");
            else
                valid = true;
        }

        System.out.println();
        return planet;
    }

    private Character selectCharacter(){
        boolean valid = false;
        Character character = new Character();
        int value = -1;

        while (!valid){
            value = this.readCommand();
            character = this.controller.getCharacter(value);

            if(character == null)
                System.out.println("There is no character with this ID! Please try again.");
            else if(character.getHealth() <= 0)
                System.out.println("This character is dead. Please choose another one.");
            else
                valid = true;

        }

        return character;
    }

    private Character selectHero(List<Character> heroes){
        boolean valid = false;
        Character hero = null;

        System.out.println();
        System.out.println("Enter the ID of the superhero that you want to choose: ");
        this.printCharacters(heroes);

        while (!valid) {
            hero = this.selectCharacter();
            if(!hero.getIsVillain())
                valid = true;
            else
                System.out.println("You have to choose a hero, not a villain. Please try again.");
        }

        return hero;
    }

    private Character selectVillain(List<Character> villains){
        boolean valid = false;
        Character villain = null;

        System.out.println();
        System.out.println("Enter the ID of the villain that you want to choose: ");
        this.printCharacters(villains);

        while (!valid){
            villain =  this.selectCharacter();
            if(villain.getIsVillain())
                valid = true;
            else
                System.out.println("You have to choose a villain, not a hero. Please try again.");
        }

        return villain;
    }

    private void printFightersInAction(){
        System.out.println("                  \\\\  //                     ");
        System.out.println("     @@@@@@@       \\\\//        @@@@@@@       ");
        System.out.println("     @ o o @        //         @ o o @         ");
        System.out.println("     @   - @       //\\\\        @ -   @       ");
        System.out.println("     @@@@@@@      //  \\\\       @@@@@@@       ");
        System.out.println(" @@@@@@@@@@@@@@@----  -----@@@@@@@@@@@@@@      ");
        System.out.println("      @@@@      //      \\\\      @@@@         ");
        System.out.println("      @@@@                      @@@@           ");
        System.out.println("      @@@@                      @@@@           ");
        System.out.println("      @@@@                      @@@@           ");
        System.out.println("     @@  @@                    @@  @@          ");
        System.out.println("    @@    @@                  @@    @@         ");
        System.out.println("   @@      @@                @@      @@        ");
        System.out.println("------------------------------------------------------------------------ \n\n");
    }

    private void printFightersInPause(){
        System.out.println("                      //                    \\\\                       ");
        System.out.println("     @@@@@@@         //                      \\\\       @@@@@@@        ");
        System.out.println("     @ o o @        //                        \\\\      @ o o @        ");
        System.out.println("     @   - @       //                          \\\\     @ -   @        ");
        System.out.println("     @@@@@@@      //                            \\\\    @@@@@@@        ");
        System.out.println(" @@@@@@@@@@@@@@@----                          -----@@@@@@@@@@@@@@      ");
        System.out.println("      @@@@      //                                \\\\   @@@@          ");
        System.out.println("      @@@@                                             @@@@            ");
        System.out.println("      @@@@                                             @@@@            ");
        System.out.println("      @@@@                                             @@@@            ");
        System.out.println("     @@  @@                                           @@  @@           ");
        System.out.println("    @@    @@                                         @@    @@          ");
        System.out.println("   @@      @@                                       @@      @@         ");
        System.out.println("------------------------------------------------------------------------ \n\n");
    }

    private void fight(Planet planet, Character villain, Character hero) throws InterruptedException {
        FightDetails fightDetails = this.controller.heroVsVillainFight(hero, villain, planet);

        fightDetails.getRoundByRoundNumber(0).printRound();
        this.printFightersInPause();

        for (int i = 1; i < fightDetails.getRoundsNumber(); i++) {
            System.out.println("\nROUND <<<<" + i + ">>>>");
            Thread.sleep(4000);

            this.printFightersInAction();
            Thread.sleep(400);
            this.printFightersInPause();
            Thread.sleep(400);

            fightDetails.getRoundByRoundNumber(i).printRound();
        }

        if(hero.getHealth() <= 0 && villain.getHealth() <= 0)
            System.out.println("\n >>>>> BOTH " + hero.getName() + " AND " + villain.getName() + " ARE DEAD. \n");
        else if(hero.getHealth() <= 0){
            System.out.println("\n>>>>> WINNER: " + villain.getName() + " <<<<<");
            System.out.println(">>>>> LOSER: " + hero.getName() + " <<<<<\n");
        }
        else{
            System.out.println("\n>>>>> WINNER: " + hero.getName() + " <<<<<");
            System.out.println(">>>>> LOSER: " + villain.getName() + " <<<<<\n");
        }
    }

    private void printHeader(){
        System.out.println(" -----------------------------------------------");
        System.out.println("| M A R V E L   S U P E R H E R O E S   W A R S |");
        System.out.println(" -----------------------------------------------\n");
    }

    private void printMenu(){
        System.out.println("1.Fight");
        System.out.println("2.Create Avengers team and fight against a villain");
        System.out.println("0.Exit");
    }

    private void printAvengersMenu(){
        System.out.println("1.Add a hero to the Avengers team");
        System.out.println("2.Choose a villain");
        System.out.println("3.Choose a planet and start the fight");
        System.out.println("0.Go back");
    }

    private void printAvengersFight(List<Character> avengers, Character villain, Planet planet) throws InterruptedException {
        int heroAttackModifier = 0, heroHealthModifier = 0, villainAttackModifier = 0, villainHealthModifier = 0;
        boolean deadHeroes = false;

        heroAttackModifier = Integer.parseInt(planet.getModifiers().get("heroAttackModifier").toString());
        heroHealthModifier = Integer.parseInt(planet.getModifiers().get("heroHealthModifier").toString());
        villainAttackModifier = Integer.parseInt(planet.getModifiers().get("villainAttackModifier").toString());
        villainHealthModifier = Integer.parseInt(planet.getModifiers().get("villainHealthModifier").toString());

        System.out.println();
        System.out.println("<<<<< AVENGERS TEAM >>>>>>");
        for (Character character : avengers) {
            System.out.println();
            System.out.println("    NAME: " + character.getName());
            System.out.println("    ATTACK: " + (character.getAttack() + heroAttackModifier));
            System.out.println("    HEALTH: " + (character.getHealth() + heroHealthModifier));
            Thread.sleep(200);
        }

        System.out.println();
        System.out.println("<<<<< VILLAIN >>>>>");
        System.out.println("    NAME: " + villain.getName());
        System.out.println("    ATTACK: " + (villain.getAttack() + villainAttackModifier));
        System.out.println("    HEALTH: " + (villain.getHealth() + villainHealthModifier));
        Thread.sleep(10000);

        FightDetails fightDetails = this.controller.avengersVsVillainFight(avengers, villain, planet);

        for(int roundNumber = 0; roundNumber < fightDetails.getRoundsNumber(); roundNumber ++){
            System.out.println("\nROUND <<<<" + (roundNumber + 1) + ">>>>");
            Thread.sleep(4000);

            this.printFightersInAction();
            Thread.sleep(400);
            this.printFightersInPause();
            Thread.sleep(400);

            fightDetails.getRoundByRoundNumber(roundNumber).printRound();
        }

        if(villain.getHealth() > 0){
            System.out.println("\n>>>>> WINNER: THE VILLAIN <<<<<");
            System.out.println(">>>>> LOSER: THE AVENGERS TEAM <<<<<\n");
        }
        else if(this.controller.nbOfHeroesAlive(avengers) > 0){
            System.out.println("\n>>>>> WINNER: THE AVENGERS TEAM <<<<<");
            for(Character character : avengers)
                if(character.getHealth() <= 0){
                    System.out.print(character.getName() + " " );
                    deadHeroes = true;
                }
            if(deadHeroes)
                System.out.println(" died during the fight.");
            System.out.println(">>>>> LOSER: THE VILLAIN <<<<<\n");
        }
        else
            System.out.println("\n>>>>> BOTH THE VILLAIN AND THE AVENGERS TEAM DIED DURING THE FIGHT <<<<<\n");
    }

    private void avengersVsVillainMenu(List<Character> heroes, List<Character> villains) throws InterruptedException {
        boolean stop = false;
        int value = -1;

        if(this.controller.nbOfHeroesAlive(this.controller.getHeroes()) < 2)
            System.out.println("You cannot creat an Avengers team because there are no sufficient heroes alive.");
        else {
            boolean villainIsSelected = false;
            List<Character> avengers = new ArrayList<>();
            Character hero;
            Character villain = null;

            while(!stop){
                System.out.println();
                this.printAvengersMenu();
                value = this.readCommand();

                if(value == 1){
                    hero = this.selectHero(heroes);
                    if(avengers.contains(hero))
                        System.out.println("This hero is already in the Avengers team.");
                    else
                        avengers.add(hero);
                }
                else if(value == 2){
                    if(villainIsSelected)
                        System.out.println("A villain was already selected.");
                    else {
                        villain = this.selectVillain(villains);
                        villainIsSelected = true;
                    }
                }
                else if(value == 3){
                    if(villain == null)
                        System.out.println("You must select a villain.");
                    else if(avengers.size() < MIN_NO_OF_AVENGERS)
                        System.out.println("You must select at least 2 heroes.");
                    else{
                        Planet planet = this.selectPlanet();
                        this.printAvengersFight(avengers, villain, planet);
                        stop = true;
                    }
                }
                else if(value == 0)
                    stop = true;
                else
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public void menu() throws IOException, InterruptedException {
        boolean stop = false;
        int value = -1;
        Character hero = null, villain = null;
        Planet planet = null;

        this.printHeader();

        System.out.println("Please choose an input file for characters: ");
        this.inputFileMenu("character");
        System.out.println("Please choose an input file for planets: ");
        this.inputFileMenu("planet");

        while (!stop){
            this.printMenu();
            value = this.readCommand();

            if(value == 1){
                planet = this.selectPlanet();
                hero = this.selectHero(this.controller.getHeroes());
                villain = this.selectVillain(this.controller.getVillains());
                System.out.println("");
                this.fight(planet, villain, hero);
            }
            else if(value == 2){
                this.avengersVsVillainMenu(this.controller.getHeroes(), this.controller.getVillains());
            }
            else if(value == 0)
                stop = true;
            else
                System.out.println("Invalid option! Please try again.");

            if(this.controller.allHeroesDead(this.controller.getHeroes())){
                System.out.println(">>>>> All heroes are dead. <<<<<");
                stop = true;
            }
            else if(this.controller.allVillainsDead(this.controller.getVillains())){
                System.out.println(">>>>> All villains are dead. <<<<<");
                stop = true;
            }
        }
    }
}