package fr.pogl.projet.models.gridManager;

import static java.lang.Math.abs;

public class Coordinates {
    int x;
    int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public void set(Coordinates u) {
        this.x = u.getX();
        this.y = u.getY();
    }

    public int absDiffX(Coordinates u) { return abs(this.x-u.getX()); }

    public int absDiffY(Coordinates u) { return abs(this.y-u.getY()); }

    public int absDiff(Coordinates u) {
        return absDiffX(u) + absDiffY(u);
    }
}
