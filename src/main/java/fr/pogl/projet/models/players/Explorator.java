package fr.pogl.projet.models.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Explorator implements Player {

    private String name;

    public Explorator(String name) {
        this.name = name;
    }

    @Override
    public boolean hasActionsLeft() {
        return false;
    }

    @Override
    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    @Override
    public void choseAction(PlayerAction action) {

    }

    @Override
    public String getName() {
        return name;
    }
}
