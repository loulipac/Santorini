package Modele;

public abstract class Commande {
    int player;

    public abstract void action(int type);

    public void execute() {
        action(0);
    }

    public void unexecute() {
        action(1);
    }
}
