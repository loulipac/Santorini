package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

import java.awt.Point;

/**
 * Classe représentant les coups de construction effectués dans le jeu.
 */
public class CoupConstruire extends Commande {
    private Point position;
    private Point batisseur;

    /**
     * Constructeur du coup.
     * @param joueur joueur ayant effectué le coup
     * @param position position à laquelle l'étage a été construit
     * @param batisseur position du batisseur ayant effectué la construction
     */
    public CoupConstruire(Joueur joueur, Point position, Point batisseur) {
        super(joueur);
        this.position = position;
        this.batisseur = batisseur;
    }

    @Override
    protected void action(Jeu jeu, int type) {
        // MAJ du plateau du jeu
        int valeur = type == REDO ? 1 : -1;
        jeu.getPlateau().MAJEtage(position, valeur);

        // MAJ de la situation du jeu
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
        jeu.iaEssayeJouer();
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
