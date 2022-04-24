package fr.pogl.projet.view;

import fr.pogl.projet.controlers.PlayerCollection;
import fr.pogl.projet.controlers.Game;

import javax.swing.*;

public class Display extends JFrame {

    private final PlayerCollection playersBuilder;
    private final Game game;
    private final JFrame frame;

    public Display(PlayerCollection playersBuilder, Game game) {
        this.playersBuilder = playersBuilder;
        this.game = game;

        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Île Interdite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showCreatePlayerMenu() {
        frame.getContentPane().add(new CreatePlayerGrid(playersBuilder, game, this::showGameMenu));
        frame.pack();
        frame.setVisible(true);
    }

    public void showGameMenu() {
        frame.getContentPane().removeAll();
        game.initializeGrid();
        PlayerTurn playerTurn = new PlayerTurn(game);
        frame.getContentPane().add(playerTurn);
        frame.pack();
        frame.setVisible(true);
    }

    public void showGameOver() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new GameOver());
        frame.pack();
        frame.setVisible(true);
    }

}
