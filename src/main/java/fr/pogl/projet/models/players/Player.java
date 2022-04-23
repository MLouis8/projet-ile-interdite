package fr.pogl.projet.models.players;

import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Artefacts;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.SpecialActions;
import fr.pogl.projet.models.gridManager.WaterLevel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public abstract class Player {

    private final String name;
    private Coordinates coordinates;
    private int actionCounter;
    private boolean[] artefactsKeys;
    private int artefacts;
    private HashMap<SpecialActions, Integer> inventory;
    private boolean alive;

    public Player(String name) {
        this.name = name;
        this.coordinates = new Coordinates(0, 4);
        resetCounter();
        inventory = new HashMap<SpecialActions, Integer>();
        inventory.put(SpecialActions.HELICOPTER, 0);
        inventory.put(SpecialActions.SAND_BAG, 0);
        alive = true;
    }

    public boolean isAlive() { return alive; }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public abstract PlayerType getType();

    public String getName() {
        return name;
    }

    public int getArtefacts() { return  this.artefacts; }

    public int getActionsLeft() { return this.actionCounter; }

    public Coordinates getCoordinates() { return coordinates; }

    public void resetCounter() { this.actionCounter = 3; }

    public void decreaseCounter() { this.actionCounter--; }

    public void increaseCounter() { this.actionCounter++; }

    public boolean hasKey(Artefacts artefact) {
        return this.artefactsKeys[artefact.ordinal()];
    }

    public void gainKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = true; }

    public void removeKey(Artefacts a) { this.artefactsKeys[a.ordinal()] = false; }

    public boolean hasSandBag() { return this.inventory.get(SpecialActions.SAND_BAG) > 0; }

    public boolean hasHelicopter() { return this.inventory.get(SpecialActions.HELICOPTER) > 0; }

    public boolean isInRange(@NotNull Coordinates coord) {
        if (this.coordinates.absDiff(coord) > 1) {
            System.out.println("Assechement impossible : case injoignable");
            return false;
        } else {
            return true;
        }
    }

    public void move(Coordinates choseCoord, CellState[][] grid) {
        grid[coordinates.getX()][coordinates.getY()].removePlayer(this);
        this.coordinates.set(choseCoord);
        grid[coordinates.getX()][coordinates.getY()].addPlayer(this);
    }

    public void moveTo(Coordinates choseCoord, CellState[][] grid) {
        if (isInRange(choseCoord)) {
            if (grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel() == WaterLevel.SUBMERGED) {
                System.out.println("Impossible de se deplacer sur une case submergee !");
            } else {
                move(choseCoord, grid);
                this.decreaseCounter();
            }
        }
    }

    public void helicopter(Coordinates choseCoord, CellState[][] grid) {
        if (hasHelicopter() && grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel() != WaterLevel.SUBMERGED) {
            move(choseCoord, grid);
            this.inventory.replace(SpecialActions.HELICOPTER, this.inventory.get(SpecialActions.HELICOPTER) - 1);
            return;
        }
        System.out.println("Tu n'as pas d'helicopteres !");
    }

    public boolean dry(Coordinates choseCoord, CellState[][] grid) {
        if (isInRange(choseCoord)) {
            switch (grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel()) {
                case DRY -> {
                    System.out.println("Case deja sèche !");
                    return false;
                }
                case FLOOD -> {
                    grid[choseCoord.getX()][choseCoord.getY()].setWaterLevel(WaterLevel.DRY);
                    this.decreaseCounter();
                }
                case SUBMERGED -> System.out.println("Impossible d'assécher une case submerge !");
            }
            return true;
        }
        return false;
    }

    public boolean sandBag(Coordinates choseCoord, CellState[][] grid) {
        if (hasSandBag() && dry(choseCoord, grid)) {
            this.increaseCounter();
            this.inventory.replace(SpecialActions.SAND_BAG, this.inventory.get(SpecialActions.SAND_BAG) - 1);
            return true;
        }
        System.out.println("Tu n'as pas de sac de sable !");
        return false;
    }

    public void pick(CellState[][] grid) {
        CellState cell = grid[coordinates.getX()][coordinates.getY()];
        if (cell.hasArtefact()) {
            Artefacts a = cell.getArtefact();
            if (this.hasKey(a)) {
                System.out.println("Bien joue a l'aide de votre cle, vous avez recupere l'artefact de " + a);
                cell.removeArtefacts();
                this.artefacts++;
                this.decreaseCounter();
            } else {
                System.out.println("Vous ne pouvez pas recupere l'artefact de " + a + " vous n'avez pas la bonne cle.");
            }
        } else {
            System.out.println("Dommage la cle n'est pas la");
        }
    }


    public void searchKey(CellState[][] grid) {
        CellState cell = grid[this.coordinates.getX()][this.coordinates.getY()];
        if (cell.hasKey()) {
            Artefacts a = cell.getArtefact();
            cell.removeArtefacts();
            if (hasKey(a)) {
                System.out.println("La cle de " + a + " est deja en votre possession !");
            } else {
                System.out.println("Bien joue ! Vous avez recupere la cle de l'artefact de " + a);
                this.gainKey(a);
            }
        } else {
            System.out.println("Il n'y a pas de cles sur cette case !");
            double r = Math.random();
            if (r < 1 / 3) {
                System.out.println("Oh non la case est s'inonde !");
                cell.flood();
            } else if (r < 3 / 6) {
                this.inventory.replace(SpecialActions.SAND_BAG, 1 + this.inventory.get(SpecialActions.SAND_BAG));
                System.out.println("Tu as tout de meme trouve un sac de sable !");
            } else if (r < 4 / 6) {
                this.inventory.replace(SpecialActions.HELICOPTER, 1 + this.inventory.get(SpecialActions.HELICOPTER));
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
