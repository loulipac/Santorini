package Modele;

public abstract class Commande {
    protected int player;

    protected Commande(int player) {
        this.player = player;
    }

    public abstract void action(Plateau level, int type);

    public void execute(Plateau level) {
        action(level, 0);
    }

    public void unexecute(Plateau level) {
        action(level, 1);
    }
}
