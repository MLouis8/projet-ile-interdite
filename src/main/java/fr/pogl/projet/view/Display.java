package fr.pogl.projet.view;

import fr.pogl.projet.controlers.PlayerCollectionBuilder;
import fr.pogl.projet.models.Game;
import fr.pogl.projet.models.players.Player;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    private final PlayerCollectionBuilder inputsManager;
    private final Game game;
    private JPanel panel;

    public Display(PlayerCollectionBuilder inputsManager, Game game) {
        this.inputsManager = inputsManager;
        this.game = game;
        this.panel = new JPanel();
    }

    public void showCreatePlayerMenu() {
        this.panel.setLayout(new GridLayout(0, 2));
        this.panel.add(new JButton("Button 1"));
        this.getContentPane().add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void onButtonClicked() {
        onPlayerCreatedEvent(null);
    }

    private void onPlayerCreatedEvent(Player player) {

    }

}
