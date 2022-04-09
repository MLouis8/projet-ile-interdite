package fr.pogl.projet.models;

import java.util.HashMap;

public class Grid {

    public enum WaterLevel { DRY, FLOOD, SUBMERGED }

    public HashMap<Coordinates, Artefacts> artefactsMap;

    public HashMap<Coordinates, Artefacts> keysMap;

    public HashMap<Coordinates, SpecialActions> SPMap;

    public WaterLevel[][] waterLevels;

    public Coordinates heliport;

    public Grid() {
        this.heliport = new Coordinates(8, 8);
        this.waterLevels = new WaterLevel[9][9];
        for (int i = 0; i < 18; i++) {
            this.waterLevels[i/9][i%9] = WaterLevel.DRY;
        }
        this.artefactsMap = randomArtifacts();
        this.keysMap = randomArtifacts();
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

    private HashMap<Coordinates, SpecialActions> randomSpecialActions(int n) {
        HashMap<Coordinates, SpecialActions> randomSpecialActionMap = new HashMap<>();
        int[] cells = Game.randomValues(2*n);

        for (int i = 0; i < 2*n; i += 2) {
            randomSpecialActionMap.put(new Coordinates(cells[i]/9, cells[i]%9), SpecialActions.SAND_BAG);
            randomSpecialActionMap.put(new Coordinates(cells[i+1]/9, cells[i+1]%9), SpecialActions.HELICOPTER);
        }

        return randomSpecialActionMap;
    }

}
