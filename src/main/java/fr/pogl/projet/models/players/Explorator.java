package fr.pogl.projet.models.players;

import java.util.Collection;

public class Explorator implements Player {
    @Override
    public boolean hasActionsLeft() {
        return false;
    }

    @Override
    public Collection<PlayerAction> getAvailableActions() {
        return null;
    }

    @Override
    public void choseAction(PlayerAction action) {

    }
}
