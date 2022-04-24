package fr.pogl.projet.view;

import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PlayerTurn extends JPanel {

    private PlayerAction action;
    private Player player;
    private final Game game;

    private final JLabel playerNameLabel = new JLabel();
    private final JLabel actionsAmountLabel = new JLabel();
    private final JLabel modeLabel = new JLabel();
    private final JLabel numberOfPlayers = new JLabel();

    final JPanel buttons = new JPanel();

    public PlayerTurn(@NotNull Game game) {
        this.game = game;
        player = this.game.doPlayerTurn();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(playerNameLabel);
        add(actionsAmountLabel);
        add(modeLabel, SwingConstants.CENTER);
        add(numberOfPlayers);
        add(buttons);

        JPanel panel = new JPanel();
        panel.add(new MapGrid(this, this.game));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        game.getPlayerCollection().get().forEach(player -> {
            JButton playerButton = new JButton(player.getName());
            ActionListener listener = e -> System.out.println("player button clicked");
            playerButton.addActionListener(listener);
            player.getOnDeathEvents().add((p) -> {
                        playerButton.setBackground(Color.gray);
                        playerButton.removeActionListener(listener);
                    }
            );
            rightPanel.add(playerButton);
        });
        panel.add(rightPanel);
        add(panel);
        refresh();
    }

    public PlayerAction getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    public void refresh() {
        playerNameLabel.setText("player: " + player.getName());
        actionsAmountLabel.setText("Actions: " + player.getActionsLeft());
        modeLabel.setText("Mode: moving");
        numberOfPlayers.setText("Number of players: " + game.getNumberPlayers());

        if (action == null)
            action = PlayerAction.MOVE;
        buttons.removeAll();
        for (PlayerAction action : player.getAvailableActions()) {
            JButton modeButton = new JButton(action.toString());
            modeButton.addActionListener(e -> {
                modeLabel.setText("Mode: " + action);
                this.action = action;
            });
            buttons.add(modeButton);
        }
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(e -> nextTurn());
        buttons.add(endTurn);
    }

    private void nextTurn() {
        player = game.doPlayerTurn();
        game.randomFlood();
        refresh();
    }
}
