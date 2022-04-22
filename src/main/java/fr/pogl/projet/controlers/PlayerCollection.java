package fr.pogl.projet.controlers;

import fr.pogl.projet.models.players.*;

import java.util.ArrayList;
import java.util.List;


public class PlayerCollection {

    private List<Player> players = new ArrayList<>();

    public void addPlayer(String name, PlayerType playerType) {
        Player player = new Explorator(name);
        switch (playerType) {
            case DIVER -> { player = new Diver(name); }
            case PILOT -> { player = new Pilot(name); }
            case ENGINEER -> { player = new Engineer(name); }
            case MESSENGER -> { player = new Messenger(name); }
            case NAVIGATOR -> { player = new Navigator(name); }
        }
        players.add(player);
    }

    public List<Player> get() {
        return players;
    }

    @Override
    public String toString() {
        String s = "";
        for (Player p : players) {
            s += p.getName();
            s += " ";
        }
        return s;
    }

}

