package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CoupDeplacer extends Commande {
    private Point[] positions;
    private int coup;

    public CoupDeplacer(Joueur joueur, Point pPos, Point nPos) {
        super(joueur);
        positions = new Point[2];
        positions[1] = pPos;
        positions[0] = nPos;
        coup = pPos == null ? PLACEMENT : DEPLACEMENT;
    }

    @Override
    public void action(Jeu jeu, int type) {
        if (positions[type] != null) {
            jeu.getPlateau().ajouterJoueur(positions[type], joueur);
        }
        int i = (type + 1) % 2;
        if (positions[i] != null) {
            jeu.getPlateau().enleverJoueur(positions[i]);
        }

        ArrayList<Point> batisseurs = joueur.getBatisseurs();
        int index = batisseurs.indexOf(positions[i]);
        if (index != -1) {
            batisseurs.set(index, positions[type]);
        } else {
            batisseurs.add(positions[type]);
        }
        batisseurs.removeAll(Collections.singleton(null));

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
            jeu.setDeplacement_en_cours(positions[i], positions[type]);
            if (type == UNDO && jeu.estJeufini()) jeu.setJeuFini(false);
            else if (type == REDO) jeu.victoireJoueur();
            int situation = type == REDO ? CONSTRUCTION : SELECTION;
            jeu.setSituation(situation);
            jeu.MAJObservateur();
            jeu.iaJoue();
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
