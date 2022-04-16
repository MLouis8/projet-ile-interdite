package fr.pogl.projet.view;

import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.models.gridManager.Coordinates;
import fr.pogl.projet.models.gridManager.Grid;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PlayerTurn extends JPanel {

    private PlayerAction action;
    private Player player;
    private Grid grid;

    private final JLabel playerNameLabel = new JLabel();
    private final JLabel actionsAmountLabel = new JLabel();
    private final JLabel modeLabel = new JLabel();

    JPanel buttons = new JPanel();

    public PlayerTurn(@NotNull Game game) {
        player = game.doPlayerTurn();
        grid = game.getGrid();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(playerNameLabel);
        add(actionsAmountLabel);
        add(modeLabel, SwingConstants.CENTER);
        add(buttons);
        add(new MapGrid(this, grid));
        refresh();
    }

    public void activate(Coordinates c) {
        if (player.getActionsLeft() > 0) {
            switch (getAction()) {
                case MOVE: {
                    player.moveTo(c, grid.waterLevels);
                    break;
                }
                case DRY_OUT: {
                    player.dry(c, grid.waterLevels);
                    break;
                }
                case PICK_ARTEFACT: {
                    player.pick(grid.artefactsMap);
                    break;
                }
            }
            refresh();
        } else {
            System.out.println("Le joueur n'a plus d'actions");
        }
    }

    private void refresh() {
        playerNameLabel.setText("player: " + player.getName());
        actionsAmountLabel.setText("Actions: " + player.getActionsLeft());
        modeLabel.setText("Mode: moving");
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
    }

    public PlayerAction getAction() { return action; }
}
