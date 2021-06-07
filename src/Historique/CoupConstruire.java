package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

import java.awt.Point;

public class CoupConstruire extends Commande {
    private Point position;
    private Point batisseur;

    public CoupConstruire(Joueur joueur, Point position, Point batisseur) {
        super(joueur);
        this.position = position;
        this.batisseur = batisseur;
    }

    @Override
    public void action(Jeu jeu, int type) {
        int valeur = type == REDO ? 1 : -1;
        jeu.getPlateau().MAJEtage(position, valeur);

        int situation = CONSTRUCTION;
        Point batisseur_en_cours = batisseur;
        if (type == REDO) {
            situation = SELECTION;
            batisseur_en_cours = null;
        }
        jeu.setSituation(situation);
        jeu.changerJoueur();
        jeu.setBatisseurEnCours(batisseur_en_cours);
        jeu.MAJObservateur();
        jeu.iaJoue();
    }

    @Override
    public String toString() {
        return position.x + " " + position.y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CoupConstruire)) return false;

        CoupConstruire c = (CoupConstruire) o;
        return joueur.equals(c.joueur) &&
                position.equals(c.position) &&
                batisseur.equals(c.batisseur);
    }
}
