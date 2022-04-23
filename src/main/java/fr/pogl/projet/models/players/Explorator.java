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
        int xDiff = this.getCoordinates().absDiffX(coord);
        int yDiff = this.getCoordinates().absDiffY(coord);

        if ( ( absDiff > 1) || ( (yDiff == xDiff) && (absDiff > 2) ) ) {
            System.out.println("Deplacement impossible : case injoignable");
            return false;
        } else {
            return true;
        }
    }

    public PlayerType getType() { return PlayerType.EXPLORATOR; }
}
