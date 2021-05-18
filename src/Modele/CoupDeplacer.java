package Modele;

import java.awt.Point;

public class CoupDeplacer extends Commande {
    Point prevPos, newPos;

    public CoupDeplacer(Point _prevPos, Point _newPos) {
        prevPos = _prevPos;
        newPos = _newPos;
    }

    @Override
    public void action(int type) {
        
    }
}
