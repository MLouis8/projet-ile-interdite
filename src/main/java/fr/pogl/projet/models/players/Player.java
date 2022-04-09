package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Artefacts;
import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public abstract class Player {

    private String name;
    private Coordinates coordinates;
    private int actionCounter;
    private boolean[] artefactsKeys;
    private int artefacts;

    public void choseAction(PlayerAction action, Coordinates choseCoord, Grid g) {
        switch (action) {
            case MOVE -> moveTo(choseCoord, g.waterLevels);
            case DRY_OUT -> dry(choseCoord, g.waterLevels);
            case PICK_ARTEFACT -> pick(g.artefactsMap);
        }
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public String getName() {
        return name;
    }

    public int getArtefacts() { return  this.artefacts; }

    public int getActionsLeft() { return this.actionCounter; }

    public Coordinates getCoordinates() { return coordinates; }

    public void resetCounter() { this.actionCounter = 3; }

    public void decreaseCounter() { this.actionCounter--; }

    public boolean isDrowning(Grid.WaterLevel[][] waterLevels) {
        return waterLevels[this.coordinates.getX()/9][this.coordinates.getY()%9] == Grid.WaterLevel.SUBMERGED;
    }

    public boolean hasKey(Artefacts artefact) {
        return this.artefactsKeys[artefact.ordinal()];
    }

    public void gainKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = true; }

    public void removeKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = false; }

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

    public void pick(HashMap<Coordinates, Artefacts> artefactsMap) {
        if (artefactsMap.containsKey(this.coordinates)) {
            Artefacts a = artefactsMap.get(this.coordinates);
            if (this.hasKey(a)) {
                System.out.println("Bien joue a l'aide de votre cle, vous avez recupere l'artefact de " + a);
                artefactsMap.remove(a);
                this.artefacts++;
            } else {
                System.out.println("Vous ne pouvez pas recuperer l'artefact de " + a + " vous n'avez pas la bonne cle.");
            }
        }
    }

    public void searchKey(HashMap<Coordinates, Artefacts> keysMap) {
        if (keysMap.containsKey(this.coordinates)) {
            Artefacts a = keysMap.get(this.coordinates);
            if (hasKey(a)) {
                System.out.println("La cle de " + a + " est deja en votre possession !");
            } else {
                System.out.println("Bien joue ! Vous avez recupere la cle de l'artefact de " + a);
                this.gainKey(a);
            }
        } else {
            System.out.println("Il n'y a pas de cles sur cette case !");
        }
    }

    public void exchangeKey(Player other, Artefacts a) {
        if (this.coordinates.absDiff(other.getCoordinates()) != 0) {
            System.out.println("Les joueurs ne sont pas sur la meme case !");
            return;
        }
        if (this.hasKey(a)) {
            this.removeKey(a);
            other.gainKey(a);
        } else if (other.hasKey(a)) {
            other.removeKey(a);
            this.gainKey(a);
        } else {
            System.out.println("Erreur la cle n'est possede par aucun des deux joueurs");
        }

    }
}
