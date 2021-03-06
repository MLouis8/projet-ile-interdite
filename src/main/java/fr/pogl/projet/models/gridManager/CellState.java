package fr.pogl.projet.models.gridManager;

import fr.pogl.projet.Core;
import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerType;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CellState {

    private WaterLevel waterLevel;
    private final Player[] players;
    private int numberPlayers;
    private Artefacts artefact;
    private Artefacts key;

    public CellState(int n) {
        waterLevel = WaterLevel.DRY;
        numberPlayers = 0;
        players = new Player[n];
        artefact = Artefacts.NULL;
        key = Artefacts.NULL;
    }

    public void setHeliport() {
        waterLevel = WaterLevel.NULL;
    }

    public boolean hasKey() {
        return key != Artefacts.NULL;
    }

    public boolean hasArtefact() {
        return artefact != Artefacts.NULL;
    }

    public Artefacts getArtefact() {
        return artefact;
    }

    public Artefacts getKey() {
        return key;
    }

    public void setArtefacts(Artefacts a, Artefacts k) {
        artefact = a;
        key = k;
    }

    public boolean isFlooded() {
        return waterLevel == WaterLevel.SUBMERGED;
    }

    public void removeArtefacts() {
        artefact = Artefacts.NULL;
        key = Artefacts.NULL;
    }

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(WaterLevel waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void addPlayer(Player p) {
        players[numberPlayers] = p;
        numberPlayers++;
    }

    public void removePlayer(Player p) {
        for (int i = 0; i < numberPlayers; i++) {
            if (p.getType() == players[i].getType()) {
                players[i] = null;
                for (int j = i + 1; j < numberPlayers; j++) {
                    players[j - 1] = players[j];
                }
                numberPlayers--;
                return;
            }
        }
    }

    public void flood() {
        switch (this.waterLevel) {
            case DRY -> this.waterLevel = WaterLevel.FLOOD;
            case FLOOD -> this.waterLevel = WaterLevel.SUBMERGED;
        }
    }

    private BufferedImage playerIcon(@NotNull PlayerType t) throws IOException {
        String name;
        switch (t) {
            case DIVER -> name = "diver.png";
            case ENGINEER -> name = "engineer.png";
            case NAVIGATOR -> name = "navigator.png";
            case MESSENGER -> name = "postman.png";
            case PILOT -> name = "pilot.png";
            default -> name = "explorator.png";
        }
        return loadImageFromSource(name);
    }

    private ArrayList<BufferedImage> createIconArray() {
        ArrayList<BufferedImage> images = new ArrayList<>();
        int i = 0;
        while (i < numberPlayers) {
            try {
                images.add(playerIcon(this.players[i].getType()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }

        return images;
    }

    private BufferedImage loadImageFromSource(String name) throws IOException {
        return ImageIO.read(Objects.requireNonNull(Core.class.getResourceAsStream("../../../img/" + name)));
    }

    private BufferedImage getBgImage() throws IOException {

        String name;
        if (this.hasArtefact())
            switch (getArtefact()) {
                case FIRE -> name = "fire.jpg";
                case WATER -> name = "waterArtefact.jpg";
                case WIND -> name = "wind.jpg";
                case EARTH -> name = "earth.jpg";
                default -> throw new IllegalArgumentException("Artefact not found");
            }
        else {
            switch (this.waterLevel) {
                case FLOOD -> name = "sand&water.jpg";
                case DRY -> name = "sand.jpg";
                case SUBMERGED -> name = "water.jpg";
                default -> name = "heliport.jpg";
            }
        }
        return loadImageFromSource(name);
    }

    public BufferedImage getIcon() {
        BufferedImage concatImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        ArrayList<BufferedImage> images = createIconArray();

        int nbImages = images.size();
        double angle = 2 * Math.PI / nbImages;
        double a = 0;

        try {
            g2d.drawImage(getBgImage(), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (BufferedImage image : images) {
            g2d.drawImage(image, (int) (Math.cos(a) * 15) + 30, (int) (Math.sin(a) * 10) + 30, null);
            a += angle;
        }
        g2d.dispose();
        return concatImage;
    }
}