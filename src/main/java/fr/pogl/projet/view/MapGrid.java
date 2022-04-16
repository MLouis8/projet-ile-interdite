package fr.pogl.projet.view;

import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.Grid;

import javax.swing.*;
import java.awt.*;

public class MapGrid extends JPanel {

    Color groundColor(Grid g, int i, int j) {
        Color c = Color.BLACK;
        switch (g.waterLevels[i][j]) {
            case DRY -> { c = Color.orange; break; }
            case FLOOD -> { c = Color.CYAN; break; }
            case SUBMERGED -> { c = Color.BLUE; break; }
        }
        return c;
    }

    public MapGrid(PlayerTurn playerTurn, Grid gameGrid) {
        Color BG = Color.BLACK;
        setBackground(BG);
        int SIDE = 9;
        int GAP = 3;
        setLayout(new GridLayout(SIDE, SIDE, GAP, GAP));
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                BG = groundColor(gameGrid, i, j);
                JButton button = new JButton("");
                button.setBackground(BG);
                Coordinates coord = new Coordinates(i, j);
                button.addActionListener((e) -> {
                    System.out.println("Clicked on " + coord.getX() + " " + coord.getY() + " with action " + playerTurn.getAction());
                    playerTurn.activate(coord);
                });
                Dimension BTN_PREF_SIZE = new Dimension(80, 80);
                button.setPreferredSize(BTN_PREF_SIZE);
                add(button);
            }
        }
    }
}