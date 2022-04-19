package fr.pogl.projet.view;

import fr.pogl.projet.models.players.Player;
import fr.pogl.projet.models.players.PlayerType;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawPlayers {
    
    private HashMap<PlayerType, boolean> playerTypes;
    private int size;

    public DrawPlayers() {
        this.playerTypes = new HashMap<PlayerType, boolean>();
        playerTypes.put(PlayerType.EXPLORATOR, false);
        playerTypes.put(PlayerType.DIVER, false);
        playerTypes.put(PlayerType.MESSENGER, false);
        playerTypes.put(PlayerType.NAVIGATOR, false);
        playerTypes.put(PlayerType.PILOT, false);
        playerTypes.put(PlayerType.ENGINEER, false);
        size = 0;
    }

    public void addPlayer(@NotNull Player p) {
        playerTypes.replace(p.getType(), true);
        size++;
    }

    public void removePlayer(@NotNull Player p) {
        playerTypes.replace(p.getType(), false);
        size--;
    }

    private BufferedImage playerIcon(@NotNull PlayerType t) throws IOException {
        BufferedImage img = ImageIO.read(new File("/img/explorator.jpg"));
        switch (t) {
            case DIVER -> {
                img = ImageIO.read(new File("/img/diver.jpg"));
                break;
            }
            case ENGINEER -> {
                img = ImageIO.read(new File("/img/engineer.jpg"));
                break;
            }
            case NAVIGATOR -> {
                img = ImageIO.read(new File("/img/navigator.jpg"));
                break;
            }
            case MESSENGER -> {
                img = ImageIO.read(new File("/img/messenger.jpg"));
                break;
            }
            case PILOT -> {
                img = ImageIO.read(new File("/img/pilot.jpg"));
                break;
            }
        }
        return img;
    }

    private ArrayList<BufferedImage> createIconArray() {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        this.playerTypes.forEach(
                (key, value) -> {
                    if (value) {
                        try {
                            images.add(playerIcon(key));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        return images;
    }

    public BufferedImage getIcon() {
        BufferedImage concatImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        ArrayList<BufferedImage> images = createIconArray();

        int nbImages = images.size();
        double angle = 2*Math.PI / nbImages;
        double a = 0;
        for(int j = 0; j < nbImages; j++) {
            g2d.drawImage(images.get(j), (int)(Math.cos(a)*10), (int)(Math.sin(a)*10), null);
            a += angle;
        }
        g2d.dispose();
        return concatImage;
    }
}