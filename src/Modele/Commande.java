package Modele;

import java.awt.Point;

public abstract class Commande {
    protected int player;
    protected int type;

    protected Commande(int player) {
        this.player = player;
    }

    public abstract Commande action(Plateau level, int type);

    public abstract Point getBuilder();

    public Commande execute(Plateau level) {
        return action(level, 0);
    }

    public Commande unexecute(Plateau level) {
        return action(level, 1);
    }

    public int getType() {
        return type;
    }
}
