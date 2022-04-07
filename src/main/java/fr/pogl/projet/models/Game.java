package fr.pogl.projet.models;

import fr.pogl.projet.controlers.PlayerCollection;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.view.Display;

import java.util.Arrays;

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
        flood(new Coordinates(cells[0]/9, cells[0]%9));
        flood(new Coordinates(cells[1]/9, cells[1]%9));
        flood(new Coordinates(cells[2]/9, cells[2]%9));
    }

    public void flood(Coordinates c) {
        switch (this.grid.waterLevels[c.getX()][c.getY()]) {
            case DRY -> {
                this.grid.waterLevels[c.getX()][c.getY()] = Grid.WaterLevel.FLOOD;
                break;
            }
            case FLOOD -> {
                this.grid.waterLevels[c.getX()][c.getY()] = Grid.WaterLevel.SUBMERGED;
                break;
            }
            case SUBMERGED -> {
                break;
            }
        }
    }

}
