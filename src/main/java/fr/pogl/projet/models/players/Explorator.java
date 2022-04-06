package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Explorator extends Player {

    private String name;
    private Coordinates coordinates;
    private int actionCounter;

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
    public void moveTo(Coordinates choseCoord) {
        int absDiff = this.coordinates.absDiff(choseCoord);
        int xDiff = this.coordinates.absDiffX(choseCoord);
        int yDiff = this.coordinates.absDiffY(choseCoord);
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Deplacement impossible : hors grille");
        } else if ( ( absDiff > 1) || ( (yDiff == xDiff) && (absDiff > 2) ) ) { // ajout deplacement en diagonale
            System.out.println("Deplacement impossible : case injoignable");

            // tester aussi si case bloquee
        } else {
            this.coordinates.set(choseCoord);
        }
    }
}
