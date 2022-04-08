package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Explorator extends Player {

    private String name;
    private Coordinates coordinates;

    public Explorator(String name) {
        this.coordinates = new Coordinates(0, 0);
        this.name = name;
        resetCounter();
    }

    public Collection<PlayerAction> getAvailableActions() {
        return new ArrayList<>(Arrays.asList(PlayerAction.values()));
    }

    public void choseAction(PlayerAction action) {
    }

    @Override
    public boolean isInRange(Coordinates coord) {
        int absDiff = this.coordinates.absDiff(coord);
        int xDiff = this.coordinates.absDiffX(coord);
        int yDiff = this.coordinates.absDiffY(coord);
        if (coord.getX() < 9 && coord.getY() < 9 && coord.getX() > -1 && coord.getY() > -1) {
            System.out.println("Deplacement impossible : hors grille");
            return false;
        } else if ( ( absDiff > 1) || ( (yDiff == xDiff) && (absDiff > 2) ) ) {
            System.out.println("Deplacement impossible : case injoignable");
            return false;
        } else {
            return true;
        }
    }
}
