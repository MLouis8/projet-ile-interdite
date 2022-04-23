package fr.pogl.projet.models.players;

public class Navigator extends Player {

    public Navigator(String name) { super(name); }

    public PlayerType getType() { return  PlayerType.NAVIGATOR; }
}
