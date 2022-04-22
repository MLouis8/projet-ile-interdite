package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;

public class Diver extends Player {

    private String name;
    private Coordinates coordinates;

    public Diver(String name) {
        super(name);
    }

    @Override
    public boolean moveTo(Coordinates choseCoord, CellState[][] grid) {
        if (isInRange(choseCoord)) {
            move(choseCoord, grid);
            this.decreaseCounter();
            return true;
        }
        return false;
    }

    public PlayerType getType() { return  PlayerType.DIVER; }
}

