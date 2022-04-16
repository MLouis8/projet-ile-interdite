package fr.pogl.projet.controlers;

import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.Grid;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.view.Display;

import static java.lang.Math.random;

public class Game {

    private PlayerCollection playerCollection;
    private Display display;
    private GameState state;
    private Grid grid;

    public void start() {
        this.grid = new Grid();
        this.playerCollection = new PlayerCollection();
        this.display = new Display(playerCollection, this);
        display.showCreatePlayerMenu();
    }

    public Grid getGrid() { return this.grid; }

    int index = 0;

    public Player doPlayerTurn() {
        Player player = playerCollection.get().get(index);
        index = (index + 1) % playerCollection.get().size();
        return player;
    }

    public static int[] randomValues(int nbCells) {
        int[] cells = new int[nbCells];
        cells[0] = (int)(random()*18);

        for (int i = 1; i < nbCells; i++) {
            int cell = (int)(random()*18);
            for (int j = 0; j < i; j++) {
                while (cell == cells[j])
                    cell = (int)(random()*18);
            }
            cells[i] = cell;
        }

        return cells;
    }

    public void randomFlood() {
        int[] cells = randomValues(3);
        this.grid.flood(new Coordinates(cells[0]/9, cells[0]%9));
        this.grid.flood(new Coordinates(cells[1]/9, cells[1]%9));
        this.grid.flood(new Coordinates(cells[2]/9, cells[2]%9));
    }

    public boolean isWon() {
        int artefactCounter = 0;
        for (Player p : playerCollection.get()) {
            if (p.getCoordinates().absDiff(this.grid.heliport) != 0)
                return false;
            artefactCounter += p.getArtefacts();
        }
        if (artefactCounter == 4)
            return true;
        return false;
    }

}
