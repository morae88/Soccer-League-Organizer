import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by morgan.welch on 3/26/2017.
 */
public class Prompter {
    private Set<Player> mPlayersList;
    private BufferedReader mReader;
    private Teams mTeams = new Teams();
    private Player[] mPlayers;
    private Map<String, String> mMenu;
    private Map<String, String> mTeamMenu;

    public Prompter(Player[] players) {
        mPlayers = players;
        mPlayersList = new TreeSet<>(Arrays.asList(mPlayers));
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new LinkedHashMap<>();
        mTeamMenu = new LinkedHashMap<>();
        mMenu.put("add", "Add a new team");
        mMenu.put("team", "Choose a team to modify/review");
        mMenu.put("report", "League Balance Report");
        mMenu.put("quit", "Exit");
        mTeamMenu.put("add", "Add players to a team");
        mTeamMenu.put("remove", "Remove players from a team");
        mTeamMenu.put("report", "Report of players by height");
        mTeamMenu.put("roster", "Player roster");
        mTeamMenu.put("return", "Return to main menu");
    }

    private String promptAction(Map<String, String> menu) throws IOException {
        System.out.println();
        for (Map.Entry<String, String> option : menu.entrySet()) {
            System.out.printf("%s - %s %n",
                    option.getKey(),
                    option.getValue());
        }
        System.out.print("\nWhat would you like to do:  ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run() throws IOException {
        String choice = "";
        boolean keepGoing;
        do {
            boolean existingTeams = !mTeams.getTeams().isEmpty();
            try {
                choice = promptAction(mMenu);
                switch (choice) {
                    case "add":
                        if (mTeams.getNumberTeams() < mPlayersList.size()) {
                            Team team = promptNewTeam();
                            mTeams.add(team);
                            System.out.println();
                        } else {
                            System.out.println("Maximum number of teams reached!");
                        }
                        break;
                    case "team":
                        if(existingTeams) {
                        String teamChoice;
                        Team teamToModify;
                            do {
                                teamChoice = promptAction(mTeamMenu);
                                switch (teamChoice) {
                                    case "add":
                                        teamToModify = promptForTeam(teamChoice) ;
                                        System.out.printf("You chose:  %s %n", teamToModify.getTeamName());
                                        if (teamToModify.getNumberPlayers() < 11) {
                                            do {
                                                Player playerToAdd = promptForPlayer("add", teamToModify);
                                                teamToModify.addPlayer(playerToAdd);
                                                System.out.println("Players on team " + teamToModify.getTeamName() + ":");
                                                teamToModify.getPlayers();
                                                mPlayersList.remove(playerToAdd);
                                                if (teamToModify.getNumberPlayers() < 11) {
                                                    System.out.print("\nAdd another player? Y/N: ");
                                                    keepGoing = promptForContinue(teamChoice);
                                                } else {
                                                    System.out.println("Maximum number of players reached for this team!");
                                                    keepGoing = false;
                                                }
                                            }
                                            while (keepGoing);
                                        } else {
                                            System.out.println(teamToModify.getTeamName() + " is full.");
                                        }

                                        break;
                                    case "remove":
                                        teamToModify = promptForTeam(teamChoice);
                                        System.out.printf("You chose:  %s %n", teamToModify.getTeamName());
                                        do {
                                            if (teamToModify.getPlayerList().size() > 0) {
                                            Player playerToRemove = promptForPlayer("remove", teamToModify);
                                            teamToModify.removePlayer(playerToRemove);
                                            System.out.println("Players on " + teamToModify.getTeamName() + ":");
                                            mPlayersList.add(playerToRemove);
                                            teamToModify.getPlayers();
                                            System.out.print("Remove another player? Y/N: ");
                                            keepGoing = promptForContinue(teamChoice);
                                            }
                                            else {
                                                System.out.println("There are no players on team " + teamToModify.getTeamName() + " to remove.");
                                                keepGoing = false;
                                            }
                                        } while (keepGoing);
                                        break;
                                    case "report":
                                        teamToModify = promptForTeam(teamChoice);
                                        if (toReport(teamToModify)) {
                                            teamToModify.sortedByHeight();
                                        }
                                        else {
                                            System.out.println("There are no players on team " + teamToModify.getTeamName() + " to report.");
                                        }
                                        break;
                                    case "roster":
                                        teamToModify = promptForTeam(teamChoice);
                                        if(toReport(teamToModify)) {
                                        teamToModify.printRoster();
                                        }
                                        else {
                                            System.out.println("There are no players on team " + teamToModify.getTeamName() + " to report.");
                                        }
                                        break;
                                    case "return":
                                        System.out.println("Done modifying team.");
                                        break;
                                    default:
                                        System.out.println("Not a valid choice.");
                                        break;
                                }
                            } while (!teamChoice.equals("return"));
                        }
                        else {
                            System.out.println("There are no existing teams to modify. Please add a team to continue.");
                        }
                        break;
                    case "report":
                        if (mTeams.getTeams().size() > 0) {
                            mTeams.leagueReport();
                        }
                        else {
                            System.out.println("There are no teams to report on. Please add a team to continue.");
                        }
                        break;
                    case "quit":
                        System.out.println("Thank you!");
                        break;
                    default:
                        System.out.println("Not a valid choice.");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));

    }

    private boolean toReport(Team teamToModify) {
        System.out.printf("You chose:  %s %n%n", teamToModify.getTeamName());
        if (teamToModify.getPlayerList().size() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean promptForContinue(String choice) throws IOException {
        boolean valid;
        boolean addRemove;
        do {
            String continueAdding = mReader.readLine().toUpperCase();
            switch (continueAdding) {
                case "N":
                    addRemove = false;
                    valid = true;
                    break;
                case "Y":
                    addRemove = true;
                    valid = true;
                    break;
                default:
                    System.out.println("Not a valid choice.");
                    System.out.print(choice.substring(0,1).toUpperCase() + choice.substring(1) + " another player? ");
                    addRemove = false;
                    valid = false;
                    break;
            }
        } while (!valid);
        return addRemove;
    }

    private Team promptForTeam(String choice) throws IOException {
        System.out.println("Available teams:  ");
        List<Team> teams = new ArrayList<>(mTeams.getTeams());
        String teamAsString;
        int counter = 1;
        boolean valid = false;
        for (Team t : teams) {
            System.out.println(counter + ".) " + t.getTeamName());
            counter++;
        }
        do {
        switch (choice){
            case "add":
                System.out.print("\nWhich team would you like to add players to? (Enter a number): ");
                break;
            case "remove":
                System.out.print("\nWhich team would you like to remove players from? (Enter a number): ");
                break;
            case "report":
                System.out.print("\nWhich team would you like to see a height report for? (Enter a number): ");
                break;
            case "roster":
                System.out.print("\nWhich team would you like to see a roster for? (Enter a number): ");
                break;

        }
            teamAsString = mReader.readLine();
            if (!teamAsString.matches("[-+]?\\d*\\.?\\d+")) {
                System.out.println("Invalid entry. Not a number.");
            }
            else if(Integer.parseInt(teamAsString) <= 0 || Integer.parseInt(teamAsString) > mTeams.getTeams().size()){
                System.out.println("Invalid entry. Please select a number that corresponds to a player.");
            }
            else {
                valid = true;
            }
        }while(!valid);

        return mTeams.getTeam(Integer.parseInt(teamAsString) - 1);
    }


    private Team promptNewTeam() throws IOException {
        System.out.print("\nEnter coach's name: ");
        String coach = mReader.readLine();
        System.out.print("Enter team name: ");
        String team = mReader.readLine();
        return new Team(coach, team);
    }

    private Player promptForPlayer(String choice, Team team) throws IOException {
        Map<String, Player> playerMap = showPlayers(choice, team);
        boolean valid = false;
        String playerAsString;
        do {
            System.out.print("\nWhich player would you like to " + choice + "? (Enter a number) ");
            playerAsString = mReader.readLine();
            if (!playerAsString.matches("[-+]?\\d*\\.?\\d+")) {
                System.out.println("Invalid entry. Not a number");
            }
            else if(choice.equals("add") && (Integer.parseInt(playerAsString) <= 0 || Integer.parseInt(playerAsString) > mPlayersList.size())){
                System.out.println("Invalid entry. Please select a number that corresponds to a player.");
            }
            else if(choice.equals("remove") && (Integer.parseInt(playerAsString) <= 0 || Integer.parseInt(playerAsString) > team.getPlayerList().size())){
                System.out.println("Invalid entry. Please select a number that corresponds to a player.");
            }
            else {
                valid = true;
            }
        }while(!valid);

        return playerMap.get(playerAsString);
    }

    private Map<String, Player> showPlayers(String choice, Team team) {
        Set<Player> players = new TreeSet<>(mPlayersList);
        Set<Player> playersOnTeam = new TreeSet<>(team.getPlayerList());
        Set<Player> iterator = new TreeSet<>();
        Map<String, Player> playerMap = new TreeMap<>();
        if (choice.equals("add")) {
            iterator = players;
        }
        if (choice.equals("remove")) {
            iterator = playersOnTeam;
        }
        int counter = 1;
        for (Player p : iterator) {
            String experience;
            int feet = p.getHeightInInches() / 12;
            int inches = p.getHeightInInches() % 12;
            if (p.isPreviousExperience()) {
                experience = "Yes";
            } else {
                experience = "No";
            }
            playerMap.put(Integer.toString(counter), p);
            System.out.println(counter + ".) " + p.getFirstName() + " " + p.getLastName() + "; Height: " + feet + "\'" + inches + "\";" + " Prior Experience: " + experience);
            counter++;
        }
        return playerMap;
    }

}
