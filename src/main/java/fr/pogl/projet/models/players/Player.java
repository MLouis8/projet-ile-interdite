package fr.pogl.projet.models.players;

import java.util.Collection;

public interface Player {

    boolean hasActionsLeft();

    Collection<PlayerAction> getAvailableActions();

    void choseAction(PlayerAction action);

}
