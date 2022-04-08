package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Messenger extends Player {

    private String name;
    private Coordinates coordinates;
    private int actionCounter;

    public Messenger(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public void choseAction(PlayerAction action) {
    }
}

