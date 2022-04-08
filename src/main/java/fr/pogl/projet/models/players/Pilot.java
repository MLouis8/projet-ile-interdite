package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Pilot extends Player {

    private String name;
    private Coordinates coordinates;
    private int actionCounter;

    public Pilot(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public void choseAction(PlayerAction action) {
    }

    @Override
    public void moveTo(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Action impossible : hors grille");
        } else {
            if (waterLevels[choseCoord.getX()][choseCoord.getY()] == Grid.WaterLevel.SUBMERGED) {
                System.out.println("Impossible de se deplacer sur une case submergee !");
            } else {
                this.coordinates.set(choseCoord);
            }
        }
    }
}
