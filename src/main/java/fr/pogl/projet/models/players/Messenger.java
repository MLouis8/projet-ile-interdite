package fr.pogl.projet.models.players;

import fr.pogl.projet.models.gridManager.Artefacts;

public class Messenger extends Player {

    public Messenger(String name) { super(name); }

    public PlayerType getType() { return  PlayerType.MESSENGER; }

    @Override
    public void exchangeKey(Player other, Artefacts a) {
        if (this.hasKey(a)) {
            this.removeKey(a);
            other.gainKey(a);
        } else if (other.hasKey(a)) {
            other.removeKey(a);
            this.gainKey(a);
        } else {
            System.out.println("Erreur la cle n'est possede par aucun des deux joueurs");
        }
    }
}

