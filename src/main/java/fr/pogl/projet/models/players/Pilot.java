package fr.pogl.projet.models.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Pilot extends Player {

    private final String name;
    private int xCoordinate;
    private int yCoordinate;
    private int actionCounter;

    public Pilot(String name) {
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.name = name;
        resetCounter();
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public void choseAction(PlayerAction action) {
    }
}
