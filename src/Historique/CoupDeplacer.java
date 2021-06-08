package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Classe représentant les coups de déplacement/placement effectués dans le jeu.
 */
public class CoupDeplacer extends Commande {
    private Point[] positions;
    private int coup;

    /**
     * Constructeur du coup.
     * @param joueur joueur ayant effectué le coup
     * @param pPos ancienne position du batisseur
     * @param nPos nouvelle position du batisseur
     */
    public CoupDeplacer(Joueur joueur, Point pPos, Point nPos) {
        super(joueur);
        positions = new Point[2];
        positions[1] = pPos;
        positions[0] = nPos;
        coup = pPos == null ? PLACEMENT : DEPLACEMENT;
    }

    @Override
    protected void action(Jeu jeu, int type) {
        // MAJ du plateau du jeu
        if (positions[type] != null) {
            jeu.getPlateau().ajouterJoueur(positions[type], joueur);
        }
        int i = (type + 1) % 2;
        if (positions[i] != null) {
            jeu.getPlateau().enleverJoueur(positions[i]);
        }

        // MAJ des batisseurs du joueur ayant effectué l'action
        ArrayList<Point> batisseurs = joueur.getBatisseurs();
        int index = batisseurs.indexOf(positions[i]);
        if (index != -1) {
            batisseurs.set(index, positions[type]);
        } else {
            batisseurs.add(positions[type]);
        }
        batisseurs.removeAll(Collections.singleton(null));

        // MAJ de la situation du jeu
        if (coup == PLACEMENT) {
            jeu.setSituation(PLACEMENT);
            int value = type == REDO ? 1 : -1;
            jeu.setNombreBatisseurs(jeu.getNombreBatisseurs() + value);
            if (type == REDO) {
                jeu.verificationNbBatisseur();
            } else { // UNDO
                if (jeu.getNombreBatisseurs() % 2 == 1) {
                    jeu.finTour();
                }
            }
        } else {
            jeu.setBatisseurEnCours(positions[type]);
            jeu.setDeplacementEnCours(positions[i], positions[type]);
            if (type == UNDO && jeu.estJeufini()) jeu.setJeuFini(false);
            else if (type == REDO) jeu.victoireJoueur();
            int situation = type == REDO ? CONSTRUCTION : SELECTION;
            jeu.setSituation(situation);
            jeu.MAJObservateur();
            jeu.iaEssayeJouer();
        }
    }

    @Override
    public String toString() {
        String str = "";
        if (positions[1] != null) str += positions[1].x + " " + positions[1].y + ", ";
        str += positions[0].x + " " + positions[0].y;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CoupDeplacer)) return false;

        CoupDeplacer c = (CoupDeplacer) o;
        
        return joueur.equals(c.joueur) &&
                positions[0].equals(c.positions[0]) &&
                Objects.equals(positions[1], c.positions[1]);
    }
}
