package fr.pogl.projet.models;

import fr.pogl.projet.controlers.PlayerCollection;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.view.Display;

public class Game {

    private PlayerCollection playerCollection;
    private Display display;
    private GameState state;

    public void start() {
        this.playerCollection = new PlayerCollection();
        this.display = new Display(playerCollection, this);
        display.showCreatePlayerMenu();
    }

    int index = 0;

    public Player doPlayerTurn() {
        Player player = playerCollection.get().get(index);
        index = (index + 1) % playerCollection.get().size();
        return player;
    }

}
