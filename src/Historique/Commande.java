package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

public abstract class Commande {
    protected Joueur joueur;

    protected Commande(Joueur joueur) {
        this.joueur = joueur;
    }

    public abstract void action(Jeu jeu, int type);

    public void execute(Jeu jeu) {
        action(jeu, REDO);
    }

    public void desexecute(Jeu jeu) {
        action(jeu, UNDO);
    }
}