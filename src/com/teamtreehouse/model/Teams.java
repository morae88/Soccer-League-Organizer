package com.teamtreehouse.model;

import java.util.*;

/**
 * Created by morgan.welch on 3/26/2017.
 */
public class Teams {
    private List<Team> mTeams = new ArrayList<>();
    private int mNumberTeams;

    public void add(Team team) {
        mTeams.add(team);
        mNumberTeams += 1;
    }

    public List<Team> getTeams() {
        mTeams.sort((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            return o1.getTeamName().compareTo(o2.getTeamName());
        });
        return mTeams;
    }

    public Team getTeam(int index) {
        return getTeams().get(index);
    }


    public int getNumberTeams() {
        return mNumberTeams;
    }

    public void leagueReport() {
        Map<Team, Map<String, Set<Player>>> experienceReport = new LinkedHashMap<>();
        Map<Team, Integer> numberExperienced = new HashMap<>();
        for (Team t : mTeams) {
            Map<String, Set<Player>> players = new TreeMap<>();
            Set<Player> experienced = new TreeSet<>();
            Set<Player> inexperienced = new TreeSet<>();
            for (Player p : t.getPlayerList()) {
                if (p.isPreviousExperience()) {
                    experienced.add(p);
                }
                if (!p.isPreviousExperience()) {
                    inexperienced.add(p);
                }
            }
            players.put("experienced players", experienced);
            players.put("inexperienced players", inexperienced);
            experienceReport.put(t, players);
            numberExperienced.put(t, experienced.size());
        }
            for (Map.Entry<Team, Map<String, Set<Player>>> teamMapEntry : experienceReport.entrySet()) {
                System.out.println("\n\nPlayers on " + teamMapEntry.getKey().getTeamName());
                System.out.print("=================================================\n");
                for (Map.Entry<String, Set<Player>> playerMapEntry : teamMapEntry.getValue().entrySet()) {
                    System.out.println("\n" + playerMapEntry.getKey().substring(0, 1).toUpperCase() + playerMapEntry.getKey().substring(1));
                    System.out.print("================================================\n");
                    for (Player p : playerMapEntry.getValue()) {
                        System.out.print(p);
                    }
                    System.out.println("Number of " + playerMapEntry.getKey() + " on team " + teamMapEntry.getKey().getTeamName() + ": "
                            + playerMapEntry.getValue().size());
                }
            }

        System.out.println("\nPercentage of experienced players per team: ");
        System.out.println("============================================");
        for(Map.Entry<Team, Integer> entry: numberExperienced.entrySet()){
            double percentage  = ((double)entry.getValue()/(double)entry.getKey().getPlayerList().size()) * 100;
            System.out.printf("%s: %d%%%n",entry.getKey().getTeamName(), (int)percentage);
        }

        }
    }
