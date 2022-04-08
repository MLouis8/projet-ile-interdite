package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

import java.util.Collection;
import java.util.HashMap;

public abstract class Player {

    private String name;
    private Coordinates coordinates;
    private int actionCounter;
    boolean[] artefactsKeys;

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

    boolean hasActionsLeft(){ return actionCounter > 0; }

    public void resetCounter() { this.actionCounter = 3; }

    public void decreaseCounter() { this.actionCounter--; }

    public boolean isDrowning(Grid.WaterLevel[][] waterLevels) {
        return waterLevels[this.coordinates.getX()/9][this.coordinates.getY()%9] == Grid.WaterLevel.SUBMERGED;
    }

    public boolean hasKey(Grid.Artefacts artefact) {
        return this.artefactsKeys[artefact.ordinal()];
    }

    public boolean isInRange(Coordinates coord) {
        if (coord.getX() < 9 && coord.getY() < 9 && coord.getX() > -1 && coord.getY() > -1) {
            System.out.println("Action impossible : hors grille");
            return false;
        } else if (this.coordinates.absDiff(coord) > 1) {
            System.out.println("Assechement impossible : case injoignable");
            return false;
        } else {
            return true;
        }
    }

    public void moveTo(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        if (isInRange(choseCoord)) {
            if (waterLevels[choseCoord.getX()][choseCoord.getY()] == Grid.WaterLevel.SUBMERGED) {
                System.out.println("Impossible de se deplacer sur une case submergee !");
            } else {
                this.coordinates.set(choseCoord);
            }
        }
    }

    public void dry(Coordinates choseCoord, Grid.WaterLevel[][] waterLevelGrid) {
        if (isInRange(choseCoord)) {
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

    public void searchKey(HashMap<Coordinates, Grid.Artefacts> keysMap) {
        if (keysMap.containsKey(this.coordinates)) {
            Grid.Artefacts a = keysMap.get(this.coordinates);
            if (hasKey(a)) {
                System.out.println("La cle de " + a + " est deja en votre possession !");
            } else {
                System.out.println("Bien joue ! Vous avez recupere la cle de l'artefact de " + a);
                this.artefactsKeys[a.ordinal()] = true;
            }
        } else {
            System.out.println("Il n'y a pas de cles sur cette case !");
        }
    }
}
