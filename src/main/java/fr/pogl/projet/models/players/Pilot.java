package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.WaterLevel;

public class Pilot extends Player {

    private String name;
    private Coordinates coordinates;

    public Pilot(String name) {
        super(name);
    }

    @Override
    public boolean moveTo(Coordinates choseCoord, CellState[][] grid) {
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Action impossible : hors grille");
        } else {
            if (grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel() != WaterLevel.SUBMERGED) {
                System.out.println("Impossible de se deplacer sur une case submergee !");
            } else {
                move(choseCoord, grid);
                this.decreaseCounter();
                return true;
            }
        }
        return false;
    }

    public PlayerType getType() { return  PlayerType.PILOT; }
}
