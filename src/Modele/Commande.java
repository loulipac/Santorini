package Modele;

import static Modele.Constante.*;

public abstract class Commande {
    protected int player;

    protected Commande(int player) {
        this.player = player;
    }

    public abstract void action(Jeu game, int type);

    public void execute(Jeu game) {
        action(game, REDO);
    }

    public void unexecute(Jeu game) {
        action(game, UNDO);
    }
}
