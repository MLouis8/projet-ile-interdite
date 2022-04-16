package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.Grid;

public class Diver extends Player {

    private String name;
    private Coordinates coordinates;

    public Diver(String name) {
        super(name);
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

    public PlayerType getType() { return  PlayerType.DIVER; }
}

