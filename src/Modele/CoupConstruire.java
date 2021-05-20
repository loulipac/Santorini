package Modele;

import static Modele.Constante.*;

import java.awt.Point;

public class CoupConstruire extends Commande {
    private Point position, builder;
    private int floor;

    public CoupConstruire(int player, Point position, Point builder) {
        super(player);
        this.position = position;
        this.builder = builder;
        type = CONSTRUCTION;
    }

    @Override
    public Commande action(Plateau level, int type) {
        type = type == 0 ? 1 : -1;
        level.setFloor(position.x , position.y, type);
        return this;
    }

    public Point getBuilder() {
        return builder;
    }
}
