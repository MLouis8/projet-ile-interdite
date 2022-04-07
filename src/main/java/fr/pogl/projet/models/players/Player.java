package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

import java.util.Collection;
import java.util.HashMap;

public abstract class Player {

    private String name;
    Coordinates coordinates;

    private int actionCounter;

    boolean hasActionsLeft(){ return actionCounter > 0; }

    Grid.Artefacts[] artefactsKeys;

    abstract public Collection<PlayerAction> getAvailableActions();

    public void choseAction(PlayerAction action, Coordinates choseCoord, Grid g) {
        switch (action) {
            case MOVE -> moveTo(choseCoord, g.waterLevels);
            case DRY_OUT -> dry(choseCoord, g.waterLevels);
            case PICK_ARTEFACT -> pick(g.artefactsMap);
        }
    }

    public String getName() {
        return name;
    }

    public void resetCounter() { this.actionCounter = 3; }

    public void decreaseCounter() { this.actionCounter--; }

    public boolean isInRange(Coordinates coord) {
        return true;
    }

    public void dry(Coordinates choseCoord, Grid.WaterLevel[][] waterLevelGrid) {
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Assechement impossible : hors grille");
        } else if (this.coordinates.absDiff(choseCoord) > 1) {
            System.out.println("Assechement impossible : case injoignable");
        } else {
            switch (waterLevelGrid[choseCoord.getX()][choseCoord.getY()]) {
                case DRY -> {
                    break;
                }
                case FLOOD -> {
                    waterLevelGrid[choseCoord.getX()][choseCoord.getY()] = Grid.WaterLevel.DRY;
                    break;
                }
                case SUBMERGED -> {
                    System.out.println("Impossible d'assecher une case submergee !");
                    break;
                }
            }
        }
    }

    public boolean hasKey(Grid.Artefacts artefact) {
        for (Grid.Artefacts a : this.artefactsKeys) {
            if (a == artefact)
                return true;
        }
        return false;
    }

    public void pick(HashMap<Coordinates, Grid.Artefacts> artefactsMap) {
        if (artefactsMap.containsKey(this.coordinates)) {
            Grid.Artefacts a = artefactsMap.get(this.coordinates);
            if (this.hasKey(a)) {
                System.out.println("Bien joue a l'aide de votre cle, vous avez recupere l'artefact de " + a);
            } else {
                System.out.println("Vous ne pouvez pas recuperer l'artefact de " + a + " vous n'avez pas la bonne cle.");
            }
        }
    }

    public void moveTo(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Deplacement impossible : hors grille");
        } else if (this.coordinates.absDiff(choseCoord) > 1) {
            System.out.println("Deplacement impossible : case injoignable");
        } else if (waterLevels[choseCoord.getX()][choseCoord.getY()] == Grid.WaterLevel.SUBMERGED) {
            System.out.println("Impossible de se deplacer sur une case submergee !");
        } else {
            this.coordinates.set(choseCoord);
        }
    }
}
