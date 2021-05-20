package Modele;

import Vue.Observer;
import Vue.LecteurSon;

import java.awt.*;

import static Modele.Constante.*;

/**
 * Classe permettant de gérer tout le processus d'une partie en éditant les valeurs du plateau.
 *
 * @see Plateau
 */
public class Jeu {
    private Plateau plateau;

    private int joueur_en_cours;
    private int situation;
    private int nombre_batisseurs;
    private Point batisseur_en_cours;
    private final LecteurSon construction_son;
    private Observer observateur;
    private boolean jeu_fini;
    private Historique histo;

    /**
     * Instantie une classe jeu.
     *
     * @param l nombre de ligne
     * @param c nombre de colonne
     * @param o observateur
     */
    public Jeu(int l, int c, Observer o) {
        situation = PLACEMENT;
        joueur_en_cours = JOUEUR1;
        plateau = new Plateau(l, c);
        nombre_batisseurs = 0;
        batisseur_en_cours = null;
        construction_son = new LecteurSon("boulder_drop.wav");
        observateur = o;
        jeu_fini = false;
        histo = new Historique(plateau);
    }

    /**
     * Effectue des actions selon la situation du jeu parmi placement des batisseurs, selection de batisseur, déplacement des batisseurs et construction des bâtiments.
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @see Plateau#estLibre(int ligne, int colonne)
     * @see Plateau#ajouterJoueur(int ligne, int colonne, int type_joueur)
     * @see Jeu#avancer(int ligne, int colonne, Point batisseur)
     * @see Jeu#construire(int ligne, int colonne, Point batisseur)
     */
    public void jouer(int l, int c) {
        Commande cmd = null;
        // placement des batisseurs sur la grille
        if (situation == PLACEMENT) {
            if (plateau.estLibre(l, c)) {
                cmd = new CoupDeplacer(joueur_en_cours, null, new Point(l, c));
                plateau.ajouterJoueur(l, c, joueur_en_cours);
                nombre_batisseurs++;
                if (nombre_batisseurs % 2 == 0) {
                    finTour();
                }
            }
            if (nombre_batisseurs >= 4) {
                situation = SELECTION;
            }
        } else if (situation == SELECTION || (plateau.estBatisseur(l, c, joueur_en_cours) && situation == DEPLACEMENT)) {
            System.out.println("Choix du batisseur.");
            batisseur_en_cours = choisirBatisseur(l, c);
            if (batisseur_en_cours == null) {
                situation = SELECTION;
            } else {
                situation = DEPLACEMENT;
            }
        } else if (situation == DEPLACEMENT) { // déplace un batisseur aux coordonées l et c de la grille
            System.out.println("Déplacement du batisseur.");
            Point prevPos = batisseur_en_cours;
            if (avancer(l, c, batisseur_en_cours)) {
                cmd = new CoupDeplacer(joueur_en_cours, prevPos, batisseur_en_cours);
                situation = CONSTRUCTION;
            }
            victoireJoueur();
        } else if (!jeu_fini && situation == CONSTRUCTION) { // construit un bâtiment aux coordonées l et c de la grille si possib
            System.out.println("Construction.");
            if (construire(l, c, batisseur_en_cours)) {
                construction_son.joueSon(false);
                cmd = new CoupConstruire(joueur_en_cours, new Point(l, c), plateau.getTypeBatiments(l, c));
                finTour();
                situation = SELECTION;
            }
        }
        histo.store(cmd);
    }

    /**
     * Choisi le batisseur à la position (l, c).
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return le batisseur du joueur s'il existe à cette position
     */
    private Point choisirBatisseur(int l, int c) {
        return plateau.estBatisseur(l, c, joueur_en_cours) ? new Point(l, c) : null;
    }

    /**
     * Vérifie que la case (l, c) est atteignable sur la grille (selon la situation)
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai s'il on peut atteindre la case
     */
    public boolean estAtteignable(int l, int c) {
        if (situation == DEPLACEMENT)
            return (batisseur_en_cours != null) && plateau.deplacementPossible(l, c, batisseur_en_cours);
        else if (situation == CONSTRUCTION)
            return (batisseur_en_cours != null) && plateau.peutConstruire(l, c, batisseur_en_cours);
        else if (situation == SELECTION) return plateau.estBatisseur(l, c, joueur_en_cours);
        else return nombre_batisseurs < 4;
    }

    /**
     * Fini le tour pour le joueur en cours.
     */
    private void finTour() {
        joueur_en_cours = joueur_en_cours == JOUEUR1 ? JOUEUR2 : JOUEUR1; // BOUTON "VALIDER"
        batisseur_en_cours = null;
        observateur.miseAjour();
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

    /**
     * Affiche les valeurs des cases du plateau.
     */
    private void printPlateau() {
        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                System.out.print("| " + plateau.getTypeBatisseurs(i, j) / JOUEUR1 + " : " + plateau.getTypeBatiments(i, j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Vérifie si le batisseur du joueur en cours est sur un bâtiment d'hauteur 3.
     * Si c'est le cas, le jeu s'arrête et l'observateur est notifié de la victoire.
     */
    public void victoireJoueur() {
        if (batisseur_en_cours != null && plateau.getTypeBatiments(batisseur_en_cours.x, batisseur_en_cours.y) == Plateau.TOIT) {
            System.out.println("cest fini");
            jeu_fini = true;
            observateur.miseAjour();
        }
    }

    public void undo() {
        if (histo.canUndo()) histo.undo();
    }

    public void redo() {
        if (histo.canRedo()) histo.redo();
    }

    public boolean estJeufini() {
        return jeu_fini;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Point getBatisseur_en_cours() {
        return batisseur_en_cours;
    }

    public int getSituation() {
        return situation;
    }

    public int getJoueur_en_cours() {
        return joueur_en_cours;
    }
}
