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
    private WaterLevel[][] waterLevelGrid;
    private int numberFloodedCell;

    public void start() {
        this.numberFloodedCell = 0;
        this.waterLevelGrid = new WaterLevel[9][9];
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

    public void flood() {
        int cell = (int)(random()*(18-this.numberFloodedCell+1)+this.numberFloodedCell);
        int i = 0;
        while (cell >= 0) {
            if (this.waterLevelGrid[i / 9][i % 9] == WaterLevel.FLOOD)
                cell++;
            cell--;
            i++;
        }
        switch (this.waterLevelGrid[i / 9][i % 9]) {
            case DRY -> this.waterLevelGrid[i / 9][i % 9] = WaterLevel.MEDIUM;
            case MEDIUM -> {
                this.waterLevelGrid[i / 9][i % 9] = WaterLevel.FLOOD;
                this.numberFloodedCell++;
            }
        }
    }

}
