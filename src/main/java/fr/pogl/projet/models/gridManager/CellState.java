package fr.pogl.projet.models.gridManager;

import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerType;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CellState {

    private WaterLevel waterLevel;
    private Player[] players;
    private int numberPlayers;
    private Artefacts artefact;
    private Artefacts key;
    private boolean heliport;

    public CellState(int n) {
        waterLevel = WaterLevel.DRY;
        numberPlayers = 0;
        players = new Player[n];
        artefact = Artefacts.NULL;
        key = Artefacts.NULL;
    }

    public boolean isHeliport() { return waterLevel == WaterLevel.NULL; }

    public void setHeliport() { waterLevel = WaterLevel.NULL; }

    public boolean hasKey() { return key != Artefacts.NULL; }

    public boolean hasArtefact() { return artefact != Artefacts.NULL; }

    public Artefacts getArtefact() { return artefact; }

    public Artefacts getKey() { return key; }

    public void setArtefacts(Artefacts a, Artefacts k) {
        artefact = a;
        key = k;
    }

    public void removeArtefacts() {
        artefact = Artefacts.NULL;
        key = Artefacts.NULL;
    }

    public WaterLevel getWaterLevel() { return waterLevel; }

    public void setWaterLevel(WaterLevel waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void addPlayer(Player p) {
        players[numberPlayers] = p;
        numberPlayers++;
    }

    public void removePlayer(Player p) {
        for (int i = 0; i < numberPlayers; i++) {
            if (p.getClass() == players[i].getClass()) {
                players[i] = null;
                numberPlayers--;
                return;
            }
        }
    }

    public void flood() {
        switch (this.waterLevel) {
            case DRY -> {
                this.waterLevel = WaterLevel.FLOOD;
                break;
            }
            case FLOOD -> {
                this.waterLevel = WaterLevel.SUBMERGED;
                break;
            }
            case SUBMERGED -> {
                break;
            }
        }
    }

    private BufferedImage playerIcon(@NotNull PlayerType t) throws IOException {
        BufferedImage img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/explorator.png"));
        switch (t) {
            case DIVER -> {
                img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/diver.png"));
                break;
            }
            case ENGINEER -> {
                img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/engineer.png"));
                break;
            }
            case NAVIGATOR -> {
                img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/navigator.png"));
                break;
            }
            case MESSENGER -> {
                img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/postman.png"));
                break;
            }
            case PILOT -> {
                img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/pilot.png"));
                break;
            }
        }
        return img;
    }

    private ArrayList<BufferedImage> createIconArray() {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int i = 0;
        while(i < numberPlayers) {
            try {
                images.add(playerIcon(this.players[i].getType()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }

        return images;
    }

    private BufferedImage getBgImage() throws IOException {
        BufferedImage img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/heliport.jpg))"));
        switch(this.waterLevel) {
            case FLOOD -> { img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/sand&water.jpg")); break; }
            case DRY -> { img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/sand.jpg")); break; }
            case SUBMERGED -> { img = ImageIO.read(new File("/home/mlouis/Bureau/Univ/S4/pogl/projet-ile-interdite/img/water.jpg")); break; }
        }
        return img;
    }

    public BufferedImage getIcon() {
        BufferedImage concatImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        ArrayList<BufferedImage> images = createIconArray();

        int nbImages = images.size();
        double angle = 2*Math.PI / nbImages;
        double a = 0;

        try {
            g2d.drawImage(getBgImage(), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int j = 0; j < nbImages; j++) {
            g2d.drawImage(images.get(j), (int)(Math.cos(a)*15)+30, (int)(Math.sin(a)*10)+30, null);
            a += angle;
        }
        g2d.dispose();
        return concatImage;
    }
}