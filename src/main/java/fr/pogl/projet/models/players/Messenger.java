package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.Coordinates;

public class Messenger extends Player {

    private String name;
    private Coordinates coordinates;

    public Messenger(String name) {
        super(name);
    }

    public PlayerType getType() { return  PlayerType.MESSENGER; }
}

