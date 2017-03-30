package com.teamtreehouse.model;

import java.util.*;

/**
 * Created by morgan.welch on 3/26/2017.
 */
public class Team {

    private String mCoachName;
    private String mTeamName;
    private Set<Player> mPlayers = new TreeSet<>();
    private int mNumberPlayers;


    public Team(String coachName, String teamName){
        mCoachName = coachName;
        mTeamName = teamName;
    }


    public String getCoachName() {
        return mCoachName;
    }

    public String getTeamName() {
        return mTeamName.substring(0,1).toUpperCase()+ mTeamName.substring(1);
    }

    public void addPlayer(Player player){
        mPlayers.add(player);
        mNumberPlayers +=1;

    }

    @Override
    public String toString() {
        return String.format("Team: %s Coached by: %n", mTeamName, mCoachName.substring(0,1).toUpperCase() + mCoachName.substring(1));
    }

    public Set<Player> getPlayerList() {
        return mPlayers;
    }



    public void getPlayers() {
        for(Player p: getPlayerList()){
            System.out.print(p);
        }
    }

    public void removePlayer(Player playerToRemove) {
        mPlayers.remove(playerToRemove);
    }

    public int getNumberPlayers() {

        return mNumberPlayers;
    }

    public void sortedByHeight() {
        Map<String, Set<Player>> playersByHeight = new TreeMap<>();
        Set<Player> lowPlayers = new TreeSet<>();
        Set<Player> midPlayers = new TreeSet<>();
        Set<Player> highPlayers = new TreeSet<>();
        for (Player p : mPlayers){
            if (p.getHeightInInches() <= 40){
                 lowPlayers.add(p);
            }
            if(p.getHeightInInches() > 40 && p.getHeightInInches() <= 46){
                midPlayers.add(p);
            }
            if (p.getHeightInInches() > 46 && p.getHeightInInches() <= 50){
                highPlayers.add(p);
            }
        }
        playersByHeight.put("Players in 35-40 inch height range", lowPlayers);
        playersByHeight.put("Players in 41-46 inch height range", midPlayers);
        playersByHeight.put("Players in 47-50 inch height range", highPlayers);
        for (Map.Entry<String, Set<Player>> entry : playersByHeight.entrySet()){
            System.out.println(entry.getKey());
            System.out.println("=====================================");
                for (Player p: entry.getValue()){
                    System.out.print(p);
                }

            System.out.println("\nNumber of players in this range: " + entry.getValue().size()+ "\n");
        }


    }

    public void printRoster() {
        for (Player p : mPlayers){
            System.out.printf("%s %s%n",p.getFirstName(),p.getLastName());
        }
    }
}



