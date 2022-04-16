package fr.pogl.projet.controlers;

import fr.pogl.projet.models.players.Explorator;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerType;

import java.util.ArrayList;
import java.util.List;


public class PlayerCollection {

    private List<Player> players = new ArrayList<>();

    public void addPlayer(String name, PlayerType playerType) {
        Player player;
        switch (playerType) {
            case EXPLORATOR -> player = new Explorator(name);
            default -> player = null;
        }
        players.add(player);
    }

    public List<Player> get() {
        return players;
    }

}

