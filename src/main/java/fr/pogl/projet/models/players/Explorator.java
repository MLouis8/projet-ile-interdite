package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.Coordinates;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Explorator extends Player {

    public Explorator(String name) {
        super(name);
    }

    @Override
    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    @Override
    public boolean isInRange(@NotNull Coordinates coord) {
        int absDiff = this.getCoordinates().absDiff(coord);
        if (absDiff == 1 || (this.getCoordinates().absDiffX(coord) == this.getCoordinates().absDiffY(coord) && absDiff == 2)) {
            return true;
        } else {
            System.out.println("Deplacement impossible : case injoignable");
            return false;
        }
    }

    public PlayerType getType() { return PlayerType.EXPLORATOR; }
}
