package fr.pogl.projet.controlers;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Artefacts;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;
import fr.pogl.projet.view.Display;

import static java.lang.Math.random;

public class Game {

    private PlayerCollection playerCollection;
    private Coordinates heliport;
    private CellState[][] grid;
    private int nbKeys;

    public void start() {
        this.playerCollection = new PlayerCollection();
        this.heliport = new Coordinates(8, 4);
        Display display = new Display(playerCollection, this);
        display.showCreatePlayerMenu();
    }

    public void setNbKeys(int n) { nbKeys = n; }

    public int getNumberPlayers() { return playerCollection.get().size(); }

    public CellState[][] getGrid() { return grid; }

    int index = 0;

    public void initializeGrid() {
        System.out.println("Start init");
        CellState[][] newGrid = new CellState[9][9];
        Coordinates[] a = randomCoords(8);
        System.out.println("End random");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newGrid[i][j] = new CellState(playerCollection.get().size());
            }
        }

        for (int i = 0; i < this.playerCollection.get().size(); i++)
            newGrid[0][4].addPlayer(this.playerCollection.get().get(i));

        newGrid[a[0].getX()][a[0].getY()].setArtefacts(Artefacts.EARTH, Artefacts.NULL);
        newGrid[a[1].getX()][a[1].getY()].setArtefacts(Artefacts.NULL, Artefacts.EARTH);
        newGrid[a[2].getX()][a[2].getY()].setArtefacts(Artefacts.FIRE, Artefacts.NULL);
        newGrid[a[3].getX()][a[3].getY()].setArtefacts(Artefacts.NULL, Artefacts.FIRE);
        newGrid[a[4].getX()][a[4].getY()].setArtefacts(Artefacts.WIND, Artefacts.NULL);
        newGrid[a[5].getX()][a[5].getY()].setArtefacts(Artefacts.NULL, Artefacts.WIND);
        newGrid[a[6].getX()][a[6].getY()].setArtefacts(Artefacts.WATER, Artefacts.NULL);
        newGrid[a[7].getX()][a[7].getY()].setArtefacts(Artefacts.NULL, Artefacts.WATER);

        newGrid[heliport.getX()][heliport.getY()].setHeliport();

        this.grid = newGrid;
        System.out.println("End init");
    }

    public Player doPlayerTurn() {
        Player player = playerCollection.get().get(index);
        index = (index + 1) % playerCollection.get().size();
        player.resetCounter();
        return player;
    }

    public Coordinates[] randomCoords(int nbCoords) {
        Coordinates[] coords = new Coordinates[nbCoords];
        coords[0] = new Coordinates((int)(random()*9), (int)(random()*9));

        for (int i = 0; i < nbCoords; i++) {
            Coordinates cell = new Coordinates((int)(random()*9), (int)(random()*9));
            for (int j = 0; j < i; j++) {
                while ((coords[j].absDiff(cell) == 0) || (coords[j].absDiff(this.heliport) == 0))
                    cell.set(new Coordinates((int)(random()*9), (int)(random()*9)));
            }
            coords[i] = cell;
        }

        return coords;
    }

    public void randomFlood() {
        Coordinates[] c = randomCoords(3);
        this.grid[c[0].getX()][c[0].getY()].flood();
        this.grid[c[1].getY()][c[1].getY()].flood();
        this.grid[c[2].getX()][c[2].getY()].flood();
    }

    public boolean isWon() {
        int artefactCounter = 0;
        for (Player p : playerCollection.get()) {
            if (p.getCoordinates().absDiff(this.heliport) != 0)
                return false;
            artefactCounter += p.getArtefacts();
        }
        return artefactCounter == 4;
    }

    public void activate(Coordinates c, PlayerAction a, Player p) {
        if (p.getActionsLeft() > 0) {
            switch (a) {
                case MOVE -> p.moveTo(c, grid);
                case DRY_OUT -> p.dry(c, grid);
                case PICK_ARTEFACT -> p.pick(grid);
                default -> throw new IllegalStateException("Unexpected value: " + a);
            }
        }
    }
}
