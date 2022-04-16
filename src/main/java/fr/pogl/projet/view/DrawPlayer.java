package fr.pogl.projet.view;

import fr.pogl.projet.models.players.Player;

import javax.swing.*;
import java.awt.*;

public class DrawPlayer extends JFrame {
    public DrawPlayer(Player p) {
        Color bg = playerColor(p);
        setBackground(bg);
        //setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval(150, 150, 100, 100);
    }

    private Color playerColor(Player p) {
        Color c = Color.BLACK;
        switch (p.getType()) {
            case EXPLORATOR: { c = Color.GREEN; break; }
        }
        return c;
    }
}
