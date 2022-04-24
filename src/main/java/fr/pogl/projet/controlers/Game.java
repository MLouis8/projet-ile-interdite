package fr.pogl.projet.controlers;

import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Artefacts;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;
import fr.pogl.projet.models.players.PlayerType;
import fr.pogl.projet.view.Display;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class Game {

    private PlayerCollection playerCollection;
    private Coordinates heliport;
    private CellState[][] grid;
    private int nbKeys;
    private Display display;

    public void start() {
        this.playerCollection = new PlayerCollection();
        this.heliport = new Coordinates(8, 4);
        display = new Display(playerCollection, this);
        display.showCreatePlayerMenu();
    }

    public Coordinates getHeliport() {
        return heliport;
    }

    public void setNbKeys(int n) {
        nbKeys = n;
    }

    public int getNumberPlayers() {
        return playerCollection.get().size();
    }

    public CellState[][] getGrid() {
        return grid;
    }

    int index = 0;

    public void initializeGrid() {
        CellState[][] newGrid = new CellState[9][9];
        Coordinates[] a = randomCoords(4 + nbKeys * 4);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newGrid[i][j] = new CellState(playerCollection.get().size());
            }
        }

        for (int i = 0; i < this.playerCollection.get().size(); i++) {
            newGrid[0][4].addPlayer(this.playerCollection.get().get(i));
        }

        newGrid[a[0].getX()][a[0].getY()].setArtefacts(Artefacts.EARTH, Artefacts.NULL);
        newGrid[a[1].getX()][a[1].getY()].setArtefacts(Artefacts.FIRE, Artefacts.NULL);
        newGrid[a[2].getX()][a[2].getY()].setArtefacts(Artefacts.WIND, Artefacts.NULL);
        newGrid[a[3].getX()][a[3].getY()].setArtefacts(Artefacts.WATER, Artefacts.NULL);

        for (int i = 0; i < nbKeys; i++) {
            newGrid[a[4 + i * 4].getX()][a[4 + i * 4].getY()].setArtefacts(Artefacts.NULL, Artefacts.EARTH);
            newGrid[a[5 + i * 4].getX()][a[5 + i * 4].getY()].setArtefacts(Artefacts.NULL, Artefacts.FIRE);
            newGrid[a[6 + i * 4].getX()][a[6 + i * 4].getY()].setArtefacts(Artefacts.NULL, Artefacts.WATER);
            newGrid[a[7 + i * 4].getX()][a[7 + i * 4].getY()].setArtefacts(Artefacts.NULL, Artefacts.WIND);
        }

        newGrid[heliport.getX()][heliport.getY()].setHeliport();

        this.grid = newGrid;
    }

    public Player doPlayerTurn() {
        Player player = playerCollection.get().get(index);
        index = (index + 1) % playerCollection.get().size();
        player.resetCounter();
        return player;
    }

    public Coordinates[] randomCoords(int nbCoords) {
        Coordinates[] coords = new Coordinates[nbCoords];
        Random rd = new Random();
        coords[0] = new Coordinates(abs(rd.nextInt() % 9), abs(rd.nextInt() % 9));
        while (coords[0].absDiff(this.heliport) == 0)
            coords[0].set(new Coordinates(abs(rd.nextInt() % 9), abs(rd.nextInt() % 9)));

        for (int i = 1; i < nbCoords; i++) {
            Coordinates cell = new Coordinates(abs(rd.nextInt() % 9), abs(rd.nextInt() % 9));
            boolean alreadyThere = true;
            while(cell.absDiff(this.heliport) == 0 || alreadyThere) {
                cell.set(new Coordinates(abs(rd.nextInt() % 9), abs(rd.nextInt() % 9)));
                alreadyThere = false;
                for (int j = 0; j < i; j++) {
                    if (coords[j].absDiff(cell) == 0) {
                        alreadyThere = true;
                        break;
                    }
                }
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

    public void checkEnd(Player p) {
        if (p.getCoordinates().absDiff(this.heliport) == 0)
            if (isWon())
                stop("You've won!");

        if (blockedMovement(p)) {
            p.kill();
            stop("Game Over");
        }
    }

    private boolean blockedMovement(Player p) {
        if (p.getType() == PlayerType.PILOT || p.getType() == PlayerType.DIVER || p.hasHelicopter())
            return false;

        int x = p.getCoordinates().getX();
        int y = p.getCoordinates().getY();

        boolean blockedBottom;
        boolean blockedTop;
        boolean blockedLeft;
        boolean blockedRight;

        if (p.getType() == PlayerType.EXPLORATOR) {
            blockedBottom = (x == 8) || (grid[x + 1][y].isFlooded() && ((y == 8) || grid[x + 1][y + 1].isFlooded()) && ((y == 0) || grid[x + 1][y - 1].isFlooded()));
            blockedTop = (x == 0) || (grid[x - 1][y].isFlooded() && ((y == 8) || grid[x - 1][y + 1].isFlooded()) && ((y == 0) || grid[x - 1][y - 1].isFlooded()));
            blockedRight = (y == 8) || (grid[x][y + 1].isFlooded() && ((x == 8) || grid[x + 1][y + 1].isFlooded()) && ((x == 0) || grid[x - 1][y + 1].isFlooded()));
            blockedLeft = (y == 0) || (grid[x][y - 1].isFlooded() && ((x == 8) || grid[x + 1][y - 1].isFlooded()) && ((x == 0) || grid[x - 1][y - 1].isFlooded()));
        } else {
            blockedBottom = (x == 8) || grid[x + 1][y].isFlooded();
            blockedTop = (x == 0) || grid[x - 1][y].isFlooded();
            blockedRight = (y == 8) || grid[x][y + 1].isFlooded();
            blockedLeft = (y == 0) || grid[x][y - 1].isFlooded();
        }
        if (blockedLeft && blockedBottom && blockedRight && blockedTop)
            return true;
        return false;
    }

    public void stop(String msg) {
        display.showGameOver(msg);
    }

    private boolean isWon() {
        int artefactCounter = 0;
        for (Player p : playerCollection.get()) {
            if (p.getCoordinates().absDiff(this.heliport) != 0)
                return false;
            artefactCounter += p.getArtefacts();
        }
        return artefactCounter == 4;
    }

    public boolean activate(Coordinates c, PlayerAction a, Player p1, Player p2, Artefacts artefact) {
        try {
            if (p1.getActionsLeft() < 0) {
                throw new IllegalArgumentException("Vous n'avez plus d'action.");
            } else {
                if (p1.getActionsLeft() == 0 && (a == PlayerAction.MOVE || a == PlayerAction.DRY_OUT || a == PlayerAction.PICK_ARTEFACT || a == PlayerAction.EXCHANGE_KEY || a == PlayerAction.NAV))
                    throw new IllegalArgumentException("Vous n'avez plus d'action.");
                switch (a) {
                    case MOVE -> p1.moveTo(c, grid);
                    case DRY_OUT -> {
                        if (!p1.dry(c, grid))
                            throw new IllegalStateException("déjà vide");
                    }
                    case PICK_ARTEFACT -> p1.pick(grid);
                    case SEARCH_KEY -> p1.searchKey(grid);
                    case SAND_BAG -> p1.sandBag(c, grid);
                    case HELICOPTER -> p1.helicopter(c, grid);
                    case EXCHANGE_KEY -> p1.exchangeKey(p2, artefact);
                    case NAV -> {
                        if (p1.getType() == PlayerType.NAVIGATOR)
                            p1.moveOther(p2, c, grid);
                        else
                            throw new IllegalStateException("Vous n'êtes pas un navigateur");
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + a);
                }
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public PlayerCollection getPlayerCollection() {
        return playerCollection;
    }
}
