package fr.pogl.projet.models.players;

public class Messenger extends Player {

    public Messenger(String name) { super(name); }

    public PlayerType getType() { return  PlayerType.MESSENGER; }
}

