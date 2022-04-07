package fr.pogl.projet.models;

import java.util.HashMap;

public class Grid {

    public enum Artefacts { FIRE, WATER, WIND, EARTH }

    public enum WaterLevel { DRY, FLOOD, SUBMERGED }

    public HashMap<Coordinates, Artefacts> artefactsMap;

    public WaterLevel[][] waterLevels;

    public Grid() {
        this.waterLevels = new WaterLevel[9][9];
        this.artefactsMap = randomArtifacts();
    }

    private HashMap<Coordinates, Artefacts> randomArtifacts() {
        HashMap<Coordinates, Artefacts> artefactsMap = new HashMap<>();
        int[] cells = Game.randomValues(4);

        artefactsMap.put(new Coordinates(cells[0]/9, cells[0]%9), Artefacts.EARTH);
        artefactsMap.put(new Coordinates(cells[1]/9, cells[1]%9), Artefacts.FIRE);
        artefactsMap.put(new Coordinates(cells[2]/9, cells[2]%9), Artefacts.WATER);
        artefactsMap.put(new Coordinates(cells[3]/9, cells[3]%9), Artefacts.WIND);

        return artefactsMap;
    }
}
