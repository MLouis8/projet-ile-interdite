package fr.pogl.projet.models.players;

import fr.pogl.projet.models.Coordinates;

import java.util.Collection;

public abstract class Player {

    private String name;
    Coordinates coordinates;

    private int actionCounter;

    boolean hasActionsLeft(){ return actionCounter > 0; }

    abstract public Collection<PlayerAction> getAvailableActions();

    public void choseAction(PlayerAction action, Coordinates choseCoord) {
        switch (action) {
            case MOVE -> moveTo(choseCoord);
            case DRY_OUT -> dry(choseCoord);
            case PICK_ARTEFACT -> pick(choseCoord);
        }
    }

    public String getName() {
        return name;
    }

    public void resetCounter() { this.actionCounter = 3; }

    public void decreaseCounter() { this.actionCounter--; }

    public void dry(Coordinates choseCoord) {

    }

    public void pick(Coordinates choseCoord) {

    }

    public void moveTo(Coordinates choseCoord) {
        if (choseCoord.getX() < 9 && choseCoord.getY() < 9 && choseCoord.getX() > -1 && choseCoord.getY() > -1) {
            System.out.println("Deplacement impossible : hors grille");
        } else if (this.coordinates.absDiff(choseCoord) > 1) {
            System.out.println("Deplacement impossible : case injoignable");

        // tester aussi si case bloquee
        } else {
            this.coordinates.set(choseCoord);
        }
    }
}
