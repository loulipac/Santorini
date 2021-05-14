package Modele;

import Vue.SoundPlayer;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class Jeu {
    private Plateau plateau;

    public static final int JOUEUR1 = 8;
    public static final int JOUEUR2 = 16;

    public static final int SELECTION = 0;
    public static final int DEPLACEMENT = 1;
    public static final int CONSTRUCTION = 2;

    private int joueur_en_cours;
    private int situation;
    private int nombre_batisseurs;
    private Point batisseur_en_cours;
    private final SoundPlayer construction_son;

    /**
     * Instantie une classe jeu.
     *
     * @param l
     * @param c
     */
    public Jeu(int l, int c) {
        situation = SELECTION;
        joueur_en_cours = JOUEUR1;
        plateau = new Plateau(l, c);
        nombre_batisseurs = 0;
        batisseur_en_cours = null;
        construction_son = new SoundPlayer("boulder_drop.wav");
    }

    /**
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     * @see Plateau#estLibre(int ligne, int colonne)
     * @see Plateau#ajouterJoueur(int ligne, int colonne, int type_joueur)
     * @see #avancer(int ligne, int colonne, Point batisseur)
     * @see #construire(int ligne, int colonne, Point batisseur)
     */
    public void jouer(int l, int c) {
        // placement des batisseurs sur la grille
        if (nombre_batisseurs < 4) {
            if (plateau.estLibre(l, c)) {
                plateau.ajouterJoueur(l, c, joueur_en_cours);
                nombre_batisseurs++;
                if (nombre_batisseurs % 2 == 0) {
                    finTour();
                }
            }
        } else if (situation == SELECTION || (plateau.estBatisseur(l,c,joueur_en_cours) && situation == DEPLACEMENT)) {
            System.out.println("Choix du batisseur.");
            batisseur_en_cours = choisirBatisseur(l, c);
            if (batisseur_en_cours == null) {
                situation = SELECTION;
            } else {
                situation = DEPLACEMENT;
            }
        } else if (situation == DEPLACEMENT) { // déplace un batisseur aux coordonées l et c de la grille
            System.out.println("Déplacement du batisseur.");
            situation = avancer(l, c, batisseur_en_cours) ? CONSTRUCTION : DEPLACEMENT;
        } else if (situation == CONSTRUCTION) { // construit un bâtiment aux coordonées l et c de la grille si possib
            System.out.println("Construction.");
            if (construire(l, c, batisseur_en_cours)) {
                construction_son.playSound();
                finTour();
                situation = SELECTION;
            }
        }
        printPlateau();

        System.out.println("Tour joueur n°" + joueur_en_cours / JOUEUR1);
    }

    public int getSituation() {
        return situation;
    }

    public int getJoueur_en_cours() {
        return joueur_en_cours;
    }

    private Point choisirBatisseur(int l, int c) {
        return plateau.estBatisseur(l,c,joueur_en_cours) ? new Point(l,c) : null;
    }

    /**
     * Fini le tour pour le joueur en cours.
     */
    private void finTour() {
        joueur_en_cours = joueur_en_cours == JOUEUR1 ? JOUEUR2 : JOUEUR1; // BOUTON "VALIDER"
        batisseur_en_cours = null;
    }

    /**
     * Déplace le batisseur vers les coordonnées l et c si c'est possible.
     *
     * @param l         un indice de ligne sur la grille
     * @param c         un indice de colonne sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai s'il a été possible de se déplacer
     * @see Plateau#ajouterJoueur(int ligne, int colonne, int type_joueur)
     * @see Plateau#atteignable(int ligne, int colonne, Point batisseur)
     * @see Plateau#estLibre(int ligne, int colonne)
     * @see Plateau#deplacementPossible(int ligne, int colonne, Point batisseur)
     */
    private boolean avancer(int l, int c, Point batisseur) {
        if (plateau.deplacementPossible(l, c, batisseur)) {
            plateau.ajouterJoueur(batisseur.x, batisseur.y, 0); // enlève le batisseurs de la case du point batisseurs
            plateau.ajouterJoueur(l, c, joueur_en_cours); // ajoute un batisseurs à la case en position l,c
            batisseur_en_cours = new Point(l, c);
            return true;
        }
        return false;
    }

    /**
     * Construit aux coordonnées l et c une hauteur de bâtiment si c'est possible.
     *
     * @param l         un indice de ligne sur la grille
     * @param c         un indice de colonne sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai si la construction a bien eu lieu
     * @see Plateau#peutConstruire(int ligne, int colonne, Point batisseur)
     * @see Plateau#ameliorerBatiment(int ligne, int colonne)
     */
    private boolean construire(int l, int c, Point batisseur) {
        if (plateau.peutConstruire(l, c, batisseur)) {
            return plateau.ameliorerBatiment(l, c);
        } else {
            return false;
        }
    }



    private void printPlateau() {
        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                // formatter permet de print un entier sur deux digit (ex : 1 -> 01)
                System.out.print("| " + plateau.getTypeBatisseurs(i, j) / JOUEUR1 + " : " + plateau.getTypeBatiments(i, j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Point getBatisseur_en_cours() {
        return batisseur_en_cours;
    }

}
