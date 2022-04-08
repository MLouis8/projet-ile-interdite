package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

public class Navigator extends Player {

    private String name;
    private Coordinates coordinates;

    public Navigator(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }
}
