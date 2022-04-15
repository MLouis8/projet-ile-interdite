package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

public class Diver extends Player {

    private String name;
    private Coordinates coordinates;

    public Diver(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }

    @Override
    public boolean moveTo(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        if (isInRange(choseCoord)) {
            this.coordinates.set(choseCoord);
            this.decreaseCounter();
            return true;
        }
        return false;
    }
}

