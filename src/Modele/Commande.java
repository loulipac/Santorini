package Modele;

public abstract class Commande {
    Plateau level;
    int player;

    protected Commande(Plateau level, int player) {
        this.level = level;
        this.player = player;
    }

    public abstract void action(int type);

    public void execute() {
        action(0);
    }

    public void unexecute() {
        action(1);
    }
}
