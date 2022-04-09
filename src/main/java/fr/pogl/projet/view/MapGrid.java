package fr.pogl.projet.view;

import fr.pogl.projet.models.Coordinates;
import fr.pogl.projet.models.Grid;

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
                int finalI = i;
                int finalJ = j;
                button.addActionListener((e) -> {
                    System.out.println("Clicked on " + finalI + " " + finalJ + " with action " + playerTurn.getAction());
                });
                Dimension BTN_PREF_SIZE = new Dimension(80, 80);
                button.setPreferredSize(BTN_PREF_SIZE);
                add(button);
            }
        }
    }
}