package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.Coordinates;

public class Engineer extends Player {

    private String name;
    private Coordinates coordinates;

    public Engineer(String name) {
        super(name);
    }
}
