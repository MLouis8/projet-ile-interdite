package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;

public class Diver extends Player {

    public Diver(String name) {
        super(name);
    }

    @Override
    public void moveTo(Coordinates choseCoord, CellState[][] grid) {
        if (isInRange(choseCoord)) {
            move(choseCoord, grid);
            this.decreaseCounter();
        }
    }

    public PlayerType getType() { return  PlayerType.DIVER; }
}

