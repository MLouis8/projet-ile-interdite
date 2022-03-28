package fr.pogl.projet.view;

import fr.pogl.projet.models.Game;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;

import javax.swing.*;

public class PlayerTurn extends JPanel {

    private PlayerAction action;
    private final JLabel playerNameLabel = new JLabel();
    private final JLabel actionsAmountLabel = new JLabel();
    private final JLabel modeLabel = new JLabel();
    JPanel buttons = new JPanel();

    public PlayerTurn(Game game) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(playerNameLabel);
        add(actionsAmountLabel);
        add(modeLabel, SwingConstants.CENTER);
        add(buttons);
        add(new MapGrid(this));
        refresh(game.doPlayerTurn());
    }

    private void refresh(Player player) {
        playerNameLabel.setText("player: " + player.getName());
        actionsAmountLabel.setText("Actions: " + player.getAvailableActions().size());
        modeLabel.setText("Mode: moving");
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
    }

    public PlayerAction getAction() {
        return action;
    }


}
