package Modele;

import java.awt.Point;

public class CoupDeplacer extends Commande {
    Point[] positions;

    public CoupDeplacer(Plateau level, int player, Point prevPos, Point newPos) {
        super(level, player);
        positions = new Point[2];
        positions[1] = prevPos;
        positions[0] = newPos;
    }

    @Override
    public void action(int type) {
        level.ajouterJoueur(positions[type].x, positions[type].y, player);
//        int i = (type + 1) % 2;
//        level.removePlayer(position[i].x, positions[i].y);
    }
}
