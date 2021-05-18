package Modele;

import java.awt.Point;

public class CoupConstruire extends Commande {
    Point position;
    int level;

    public CoupConstruire(Point _position, int _level) {
        position = _position;
        level = _level;
    }

    @Override
    public void action(int type) {

    }
}
