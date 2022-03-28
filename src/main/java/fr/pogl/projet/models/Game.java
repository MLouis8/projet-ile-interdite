package fr.pogl.projet.models;

import fr.pogl.projet.controlers.PlayerCollectionBuilder;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.view.Display;

import java.util.Collection;

public class Game {

    private PlayerCollectionBuilder inputsManager;
    private Display display;
    private Collection<Player> players;
    private GameState state;

    public void start() {
        this.inputsManager = new PlayerCollectionBuilder();
        this.display = new Display(inputsManager, this);
        display.showCreatePlayerMenu();
    }

    private void nextRound() {

    }

}
