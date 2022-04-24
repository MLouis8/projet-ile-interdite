package fr.pogl.projet.view;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {

    public GameOver(String msg) {
        JLabel gameOver = new JLabel(msg);
        gameOver.setFont(new Font("Verdana", Font.PLAIN, 150));
        gameOver.setForeground(Color.white);
        gameOver.setPreferredSize(new Dimension(1000, 500));
        setBackground(Color.black);
        add(gameOver);
    }

}