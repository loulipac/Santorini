package Modele;

import static Modele.Constante.*;

import java.awt.Point;

public class CoupDeplacer extends Commande {
    private Point[] positions;

    public CoupDeplacer(int player, Point prevPos, Point newPos) {
        super(player);
        positions = new Point[2];
        positions[1] = prevPos;
        positions[0] = newPos;
        type = prevPos == null ? PLACEMENT : DEPLACEMENT;
    }

    @Override
    public Commande action(Plateau level, int type) {
        if (positions[type] != null) {
            level.ajouterJoueur(positions[type].x, positions[type].y, player);
        }
        int i = (type + 1) % 2;
        if (positions[i] != null) {
            level.removePlayer(positions[i].x, positions[i].y);
        }
        return this;
    }

    @Override
    public Point getBuilder() {
        return positions[0];
    }
}
