import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.IOException;

public class LeagueManager {

  public static void main(String[] args) throws IOException {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    Prompter o = new Prompter(players);
      try {
          o.run();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}
