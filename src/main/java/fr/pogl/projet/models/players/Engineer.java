package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.WaterLevel;

public class Engineer extends Player {

    private boolean secondAction;

    public Engineer(String name) {
        super(name);
        secondAction = true;
    }

    @Override
    public boolean dry(Coordinates choseCoord, CellState[][] grid) {
        if (isInRange(choseCoord)) {
            switch (grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel()) {
                case DRY -> {
                    System.out.println("Case deja sèche !");
                    return false;
                }
                case FLOOD -> {
                    grid[choseCoord.getX()][choseCoord.getY()].setWaterLevel(WaterLevel.DRY);
                    if (!secondAction) {
                        this.decreaseCounter();
                        secondAction = true;
                    } else {
                        secondAction = false;
                    }
                    return true;
                }
                case SUBMERGED -> System.out.println("Impossible d'assécher une case submerge !");
            }
        }
        return false;
    }

    public PlayerType getType() { return  PlayerType.ENGINEER; }
}
