package Modele;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe abstraite Joueur englobant JoueurHumain et JoueurIA,
 * comporte une référence à jeu, une liste de batisseur et un numéro de joueur.
 */
public abstract class Joueur {
    protected int num_joueur;
    protected Jeu jeu;
    protected ArrayList<Point> batisseurs;

    protected Joueur(Jeu _jeu, int _num_joueur) {
        jeu = _jeu;
        num_joueur = _num_joueur;
        batisseurs = new ArrayList<>();
    }

    /**
     * Ajoute un batisseur dans la liste des batisseurs du joueur.
     * @param batisseur position (x, y) du batisseur
     */
    public void addBatisseur(Point batisseur) {
        batisseurs.add(batisseur);
    }

    // GETTER

    public int getNum_joueur() {
        return num_joueur;
    }

    public ArrayList<Point> getBatisseurs() {
        return batisseurs;
    }

    // OVERRIDE MÉTHODES HÉRITÉES

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Joueur)) return false;

        Joueur j = (Joueur) o;

        return num_joueur == j.num_joueur &&
                batisseurs.equals(j.batisseurs);
    }
}
