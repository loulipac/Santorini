package Modele;

import java.awt.Point;

public class CoupConstruire extends Commande {
    Point position;
    int floor;

    public CoupConstruire(Plateau level, int player, Point position, int floor) {
        super(level, player);
        this.position = position;
        this.floor = floor;
    }

    @Override
    public void action(int type) {
        //level.setFloor(floor - type);
    }
}
