package fr.pogl.projet.view;

import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.models.gridManager.CellState;
import fr.pogl.projet.models.gridManager.Coordinates;

import javax.swing.*;
import java.awt.*;

public class MapGrid extends JPanel {

    public MapGrid(PlayerTurn playerTurn, Game game) {
        int SIDE = 9;
        int GAP = 3;
        setLayout(new GridLayout(SIDE, SIDE, GAP, GAP));
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Coordinates coord = new Coordinates(i, j);
                CellState cell = game.getGrid()[coord.getX()][coord.getY()];
                JButton button = new JButton(new ImageIcon(cell.getIcon()));

                button.addActionListener((e) -> {
                    System.out.println("Clicked on " + coord.getX() + " " + coord.getY() + " with action " + playerTurn.getAction());
                    game.activate(coord, playerTurn.getAction(), playerTurn.getPlayer());
                    playerTurn.refresh();
                    updateButtons(this, game.getGrid());
                });

                Dimension BTN_PREF_SIZE = new Dimension(80, 80);
                button.setPreferredSize(BTN_PREF_SIZE);
                add(button);
            }
        }
    }

    private void updateButtons(MapGrid mapGrid, CellState[][] cells) {
        Component[] components = mapGrid.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JButton button) {
                CellState cell = cells[i /9][i%9];
                button.setIcon(new ImageIcon(cell.getIcon()));
            }
        }
    }
}