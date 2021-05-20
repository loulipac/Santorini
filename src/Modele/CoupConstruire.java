package Modele;

import java.awt.Point;

public class CoupConstruire extends Commande {
    private Point position;
    private int floor;

    public CoupConstruire(int player, Point position, int floor) {
        super(player);
        this.position = position;
        this.floor = floor;
    }

    @Override
    public void action(Plateau level, int type) {
        level.setFloor(position.x , position.y, floor - type);
    }
}
