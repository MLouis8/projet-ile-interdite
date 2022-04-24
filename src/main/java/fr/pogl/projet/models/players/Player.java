package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Artefacts;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.WaterLevel;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public abstract class Player {

    private final String name;
    private Coordinates coordinates;
    private int actionCounter;
    private boolean[] artefactsKeys;
    private int artefacts;
    private HashMap<PlayerAction, Integer> inventory;
    private boolean alive;
    private List<Consumer<Player>> onDeath;

    public Player(String name) {
        this.name = name;
        this.coordinates = new Coordinates(0, 4);
        resetCounter();
        inventory = new HashMap<>();
        inventory.put(PlayerAction.HELICOPTER, 0);
        inventory.put(PlayerAction.SAND_BAG, 0);
        alive = true;
        this.onDeath = new ArrayList<>();
        this.artefactsKeys = new boolean[]{false, false, false, false};
    }

    public List<Consumer<Player>> getOnDeathEvents() {
        return onDeath;
    }

    public void kill() {
        onDeath.forEach((Consumer<Player> a) -> a.accept(this));
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public abstract PlayerType getType();

    public String getName() {
        return name;
    }

    public int getArtefacts() {
        return this.artefacts;
    }

    public int getActionsLeft() {
        return this.actionCounter;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void resetCounter() {
        this.actionCounter = 3;
    }

    public void decreaseCounter() {
        this.actionCounter--;
    }

    public void increaseCounter() {
        this.actionCounter++;
    }

    public boolean hasKey(Artefacts artefact) {
        return this.artefactsKeys[artefact.ordinal()];
    }

    public void gainKey(Artefacts a) {
        this.artefactsKeys[a.ordinal()] = true;
    }

    public void removeKey(Artefacts a) {
        this.artefactsKeys[a.ordinal()] = false;
    }

    public boolean hasSandBag() {
        return this.inventory.get(PlayerAction.SAND_BAG) > 0;
    }

    public int sandBagNumbers() {
        return this.inventory.get(PlayerAction.SAND_BAG);
    }

    public int helicoptersNumbers() {
        return this.inventory.get(PlayerAction.HELICOPTER);
    }

    public boolean hasHelicopter() {
        return this.inventory.get(PlayerAction.HELICOPTER) > 0;
    }

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
                throw new IllegalArgumentException("Impossible de se deplacer sur une case submergee !");
            } else {
                move(choseCoord, grid);
                this.decreaseCounter();
            }
        } else {
            throw new IllegalArgumentException("Impossible de se deplacer sur une case inaccessible !");
        }
    }

    public void helicopter(Coordinates choseCoord, CellState[][] grid) {
        if (hasHelicopter() && grid[choseCoord.getX()][choseCoord.getY()].getWaterLevel() != WaterLevel.SUBMERGED) {
            move(choseCoord, grid);
            this.inventory.replace(PlayerAction.HELICOPTER, this.inventory.get(PlayerAction.HELICOPTER) - 1);
            return;
        }
        throw new IllegalStateException("Tu n'as pas d'hélicoptère !");
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

    public void sandBag(Coordinates choseCoord, CellState[][] grid) {
        if (hasSandBag() && dry(choseCoord, grid)) {
            this.increaseCounter();
            this.inventory.replace(PlayerAction.SAND_BAG, this.inventory.get(PlayerAction.SAND_BAG) - 1);
            return;
        }
        throw new IllegalStateException("Tu n'as pas de sac de sable ou c'est déjà sec!");
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
                throw new IllegalStateException("Vous ne pouvez pas recupere l'artefact de " + a + " vous n'avez pas la bonne cle.");
            }
        } else {
            throw new IllegalStateException("Dommage la cle n'est pas la");
        }
    }


    public void searchKey(CellState[][] grid) {
        CellState cell = grid[this.coordinates.getX()][this.coordinates.getY()];
        if (cell.hasKey()) {
            Artefacts a = cell.getKey();
            cell.removeArtefacts();
            if (hasKey(a)) {
                throw new IllegalStateException("La cle de " + a + " est deja en votre possession !");
            } else {
                System.out.println("Bien joue ! Vous avez recupere la cle de l'artefact de " + a);
                this.gainKey(a);
            }
        } else {
            double r = Math.random();
            if (r < 1. / 3) {
                System.out.println("Oh non la case est s'inonde !");
                cell.flood();
            } else if (r < 3. / 6) {
                this.inventory.replace(PlayerAction.SAND_BAG, 1 + this.inventory.get(PlayerAction.SAND_BAG));
                System.out.println("Tu as tout de meme trouve un sac de sable !");
            } else if (r < 4. / 6) {
                this.inventory.replace(PlayerAction.HELICOPTER, 1 + this.inventory.get(PlayerAction.HELICOPTER));
                System.out.println("Tu as tout de meme trouve un helicoptere !");
            }
        }
    }

    public void exchangeKey(Player other, Artefacts a) {
        if (this.coordinates.absDiff(other.getCoordinates()) != 0) {
            throw new IllegalStateException("Les joueurs ne sont pas sur la meme case !");
        }
        if (this.hasKey(a)) {
            this.removeKey(a);
            other.gainKey(a);
        } else if (other.hasKey(a)) {
            other.removeKey(a);
            this.gainKey(a);
        } else {
            throw new IllegalStateException("Erreur la cle n'est possede par aucun des deux joueurs");
        }
    }

    public void moveOther(Player p, Coordinates choseCoord, CellState[][] grid) {
    }
}
