package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Artefacts;
import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;
import fr.pogl.projet.models.SpecialActions;
import org.jetbrains.annotations.NotNull;

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
    private HashMap<SpecialActions, Integer> inventory;

    public void choseAction(@NotNull PlayerAction action, Coordinates choseCoord, Grid g) {
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

    public void increaseCounter() { this.actionCounter++; }

    public boolean isDrowning(Grid.WaterLevel[][] waterLevels) {
        return waterLevels[this.coordinates.getX()/9][this.coordinates.getY()%9] == Grid.WaterLevel.SUBMERGED;
    }

    public boolean hasKey(Artefacts artefact) {
        return this.artefactsKeys[artefact.ordinal()];
    }

    public void gainKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = true; }

    public void removeKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = false; }

    public boolean hasSandBag() { return this.inventory.get(SpecialActions.SAND_BAG) > 0; }

    public boolean hasHelicopter() { return this.inventory.get(SpecialActions.HELICOPTER) > 0; }

    public boolean isInRange(@NotNull Coordinates coord) {
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

    public boolean moveTo(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        if (isInRange(choseCoord)) {
            if (waterLevels[choseCoord.getX()][choseCoord.getY()] == Grid.WaterLevel.SUBMERGED) {
                System.out.println("Impossible de se deplacer sur une case submergee !");
            } else {
                this.coordinates.set(choseCoord);
                this.decreaseCounter();
                return true;
            }
        }
        return false;
    }

    public boolean helicopter(Coordinates choseCoord, Grid.WaterLevel[][] waterLevels) {
        boolean dry = waterLevels[choseCoord.getX()][choseCoord.getY()] != Grid.WaterLevel.SUBMERGED;
        boolean inGrid = choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1;
        if (dry && inGrid) {
            this.coordinates.set(choseCoord);
            return true;
        }
        return false;
    }

    public boolean dry(Coordinates choseCoord, Grid.WaterLevel[][] waterLevelGrid) {
        if (isInRange(choseCoord)) {
            switch (waterLevelGrid[choseCoord.getX()][choseCoord.getY()]) {
                case DRY -> {
                    this.decreaseCounter();
                    return true;
                }
                case FLOOD -> {
                    waterLevelGrid[choseCoord.getX()][choseCoord.getY()] = Grid.WaterLevel.DRY;
                    this.decreaseCounter();
                    return true;
                }
                case SUBMERGED -> {
                    System.out.println("Impossible d'assecher une case submergee !");
                    break;
                }
            }
        }
        return false;
    }

    public boolean sandBag(Coordinates choseCoord, Grid.WaterLevel[][] waterLevelGrid) {
        if (dry(choseCoord, waterLevelGrid)) {
            this.increaseCounter();
            return true;
        }
        return false;
    }

    public boolean pick(@NotNull HashMap<Coordinates, Artefacts> artefactsMap) {
        if (artefactsMap.containsKey(this.coordinates)) {
            Artefacts a = artefactsMap.get(this.coordinates);
            if (this.hasKey(a)) {
                System.out.println("Bien joue a l'aide de votre cle, vous avez recupere l'artefact de " + a);
                artefactsMap.remove(a);
                this.artefacts++;
                this.decreaseCounter();
                return true;
            } else {
                System.out.println("Vous ne pouvez pas recuperer l'artefact de " + a + " vous n'avez pas la bonne cle.");
            }
        }
        return false;
    }

    public void searchKey(@NotNull HashMap<Coordinates, Artefacts> keysMap, Grid g) {
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
            double r = Math.random();
            if (r < 1/3) {
                System.out.println("Oh non la case est s'inonde !");
                g.flood(this.coordinates);
            } else if (r < 3/6) {
                this.inventory.put(SpecialActions.SAND_BAG, 1 + this.inventory.get(SpecialActions.SAND_BAG));
                System.out.println("Tu as tout de meme trouve un sac de sable !");
            } else if (r < 4/6) {
                this.inventory.put(SpecialActions.HELICOPTER, 1 + this.inventory.get(SpecialActions.HELICOPTER));
                System.out.println("Tu as tout de meme trouve un helicoptere !");
            }
        }
    }

    public void exchangeKey(@NotNull Player other, Artefacts a) {
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
