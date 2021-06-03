package IO;

import Modele.Jeu;

import java.awt.*;

public abstract class IO {
    private Jeu jeu;
    int num_player;

    public int getNum_player() {
        return num_player;
    }

    public abstract boolean isSet_local_change();
    public abstract void connect();
    public abstract void analyserMessage(Message _message);
    public abstract void startPartie();
    public abstract void deconnexion();
    public abstract void sendAction(Point action);
    public abstract void setJeu(Jeu _jeu);
}
