package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;

public class Navigator extends Player {

    public Navigator(String name) { super(name); }

    public PlayerType getType() { return  PlayerType.NAVIGATOR; }

    @Override
    public void moveOther(Player p, Coordinates choseCoord, CellState[][] grid) {
        if (p.getName() != this.getName())
            p.move(choseCoord, grid);
        this.decreaseCounter();
    }
}
