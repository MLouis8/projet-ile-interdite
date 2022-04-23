package fr.pogl.projet.view;

import fr.pogl.projet.controlers.Callback;
import fr.pogl.projet.controlers.Game;
import fr.pogl.projet.controlers.PlayerCollection;
import fr.pogl.projet.models.players.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CreatePlayerGrid extends JPanel {
    public CreatePlayerGrid(PlayerCollection playersBuilder, Game game, Callback callback) {
        Color BG = Color.BLACK;
        setBackground(BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel nameInput = new JPanel();
        nameInput.add(new JLabel("Player name: "));
        JTextField name = new JTextField("Toto");
        nameInput.add(name);
        add(nameInput);

        JPanel typeInput = new JPanel();
        typeInput.add(new JLabel("Type : "));
        JComboBox<PlayerType> typeComboBox = new JComboBox<>(PlayerType.values());
        typeInput.add(typeComboBox);
        add(typeInput);

        JPanel keysInput = new JPanel();
        keysInput.add(new JLabel("Number of Keys per artefact: "));
        JSpinner keysNumber = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        keysInput.add(keysNumber);
        add(keysInput);

        JPanel validate = new JPanel();
        JButton finish = new JButton("Finish");
        finish.addActionListener(e -> {
            playersBuilder.addPlayer(name.getText(),
                    (PlayerType) Objects.requireNonNull(typeComboBox.getSelectedItem()));
                    game.setNbKeys((Integer) keysNumber.getValue());
            callback.call();
        });
        validate.add(finish);
        JButton addPlayer = new JButton("Add another player");
        addPlayer.addActionListener(e -> {
            playersBuilder.addPlayer(name.getText(),
                    (PlayerType) Objects.requireNonNull(typeComboBox.getSelectedItem()));
            name.setText("Toto");
            typeComboBox.setSelectedIndex(0);
        });
        validate.add(addPlayer);
        add(validate);
    }

}