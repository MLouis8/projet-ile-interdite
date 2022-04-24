package fr.pogl.projet.view;

import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerTurn extends JPanel {

    private PlayerAction action;
    private Player player;
    private Player targetedPlayer;
    private final Game game;

    private final JLabel playerNameLabel = new JLabel();
    private final JLabel actionsAmountLabel = new JLabel();
    private final JLabel modeLabel = new JLabel();
    private final JLabel numberOfPlayersLabel = new JLabel();
    private final JLabel helicoptersLabel = new JLabel();
    private final JLabel sandBagsLabel = new JLabel();

    final JPanel buttons = new JPanel();

    public PlayerTurn(@NotNull Game game) {
        this.game = game;
        player = this.game.doPlayerTurn();
        targetedPlayer = player;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(playerNameLabel);
        add(actionsAmountLabel);
        add(modeLabel, SwingConstants.CENTER);
        add(numberOfPlayersLabel);
        add(helicoptersLabel);
        add(sandBagsLabel);
        add(buttons);

        JPanel panel = new JPanel();
        panel.add(new MapGrid(this, this.game));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        game.getPlayerCollection().get().forEach(player -> {
            JButton playerButton = new JButton(player.getName());
            ActionListener listener = e -> {
                System.out.println("player button clicked");
                targetedPlayer = player;
            };
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

    public Player getTargetedPlayer() { return targetedPlayer; }

    public void refresh() {
        playerNameLabel.setText("player: " + player.getName());
        actionsAmountLabel.setText("Actions: " + player.getActionsLeft());
        modeLabel.setText("Mode: default");
        numberOfPlayersLabel.setText("Number of players: " + game.getNumberPlayers());
        helicoptersLabel.setText("Helicopters: " + player.helicoptersNumbers());
        sandBagsLabel.setText("Sand Bags: " + player.sandBagNumbers());

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
        game.randomFlood();
        player = game.doPlayerTurn();
        game.checkEnd(player);
        refresh();
    }
}
