package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

public class Engineer extends Player {

    private String name;
    private Coordinates coordinates;

    public Engineer(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }
}
