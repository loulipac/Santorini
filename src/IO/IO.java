package IO;

import Modele.Jeu;
import Vue.LobbyPanel;

import java.awt.*;

public abstract class IO {
    private Jeu jeu;
    int num_player;
    String username;

    String adversaire_nom;
    LobbyPanel lobby;

    public String getUsername() {
        return username;
    }

    public void setLobby(LobbyPanel lobby) {
        this.lobby = lobby;
    }

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

    public abstract void sendName();

    public void setAdversaireNom(String nom) {
        if (nom != null && lobby != null) {
            adversaire_nom = nom;
            lobby.setAdversaireNom(nom);
        }
    }

    public String getAdversaire_nom() {
        return adversaire_nom;
    }

}
