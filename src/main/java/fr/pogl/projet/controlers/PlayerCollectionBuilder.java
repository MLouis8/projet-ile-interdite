package fr.pogl.projet.controlers;

import fr.pogl.projet.models.players.Explorator;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerType;

import java.util.ArrayList;
import java.util.Collection;


public class PlayerCollectionBuilder {

    private Collection<Player> players = new ArrayList<>();

    private String playerName;
    private PlayerType playerType;

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public void setPlayerType(PlayerType type) {
        this.playerType = type;
    }

    public void addPlayer() {
        Player player;
        switch (playerType) {
            case DIVER -> player = new Explorator();
            default -> player = null;
        }
        players.add(player);
    }

    public Collection<Player> build() {
        return players;
    }

}

