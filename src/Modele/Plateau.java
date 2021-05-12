package Modele;

import java.awt.*;

/**
 *
 */
public class Plateau {
    private int[][] cases;
    private int lignes;
    private int colonnes;

    public static final int VIDE = 0;
    public static final int RDC = 1;
    public static final int ETAGE = 2;
    public static final int TOIT = 4;
    public static final int COUPOLE = 8;

    /**
     * Instantie une classe Plateau depuis une taille de grille passé en paramètre du constructeur
     * @param l
     * @param c
     */
    public Plateau(int l, int c) {
        cases = new int[l][c];
        lignes = l;
        colonnes = c;
    }

    /**
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est vide
     *
     */
    public boolean estVide(int l, int c) {
        return (cases[l][c] & 0xF) == VIDE;
    }

    /**
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est un rez-de-chaussée (bâtiment de hauteur 1)
     */
    public boolean estRDC(int l, int c) {
        return (cases[l][c] & 0xF) == RDC;
    }

    /**
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est un étage (bâtiment de hauteur 2)
     */
    public boolean estEtage(int l, int c) {
        return (cases[l][c] & 0xF) == ETAGE;
    }

    /**
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return boolean si la case de la grille est un toit (bâtiment de hauteur 3)
     */
    public boolean estToit(int l, int c) {
        return (cases[l][c] & 0xF) == TOIT;
    }

    /**
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return boolean si la case de la grille est une coupole (bâtiment de hauteur 4)
     */
    public boolean estCoupole(int l, int c) {
        return (cases[l][c] & 0xF) == COUPOLE;
    }

    /**
     * Ajoute un rez-de-chaussée (bâtiment de hauteur 1) sur une case de la grille
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     */
    public void ajouterRDC(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | RDC;
    }

    /**
     * Ajoute un étage (bâtiment de hauteur 2) sur une case de la grille
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     */
    public void ajouterEtage(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | ETAGE;
    }

    /**
     * Ajoute un toit (bâtiment de hauteur 3) sur une case de la grille
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     */
    public void ajouterToit(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | TOIT;
    }

    /**
     * Ajoute une coupole (bâtiment de hauteur 4) sur une case de la grille
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     */
    public void ajouterCoupole(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | COUPOLE;
    }

    /**
     * Ameliore le batiment à la position l c si c'est possible, considère qu'il n'y pas de batisseurs dessus
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si l'amélioration a marché
     */
    public boolean ameliorerBatiment(int l, int c){
        cases[l][c] = cases[l][c] << 1;
        return true;
    }

    /**
     * Vérifie que l'on peut sélectionner le batisseur selectionné appartient au joueur
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param joueur
     */
    public boolean estBatisseur(int l,int c, int joueur){
        return (cases[l][c] & ~0xf) == joueur;
    }

    /**
     * Vérifie que la case de la grille ne contient pas de joueur
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public boolean estLibre(int l, int c) {
        return (cases[l][c] & (~0xf)) == 0;
    }

    /**
     * Vérifie que la case de la grille visé peut acceuillir le batisseurs (deplacement vers le haut de uniquement 1 de hauteur)
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return
     */
    public boolean deplacementPossible(int l, int c, Point batisseur){
        int temp = (cases[batisseur.x][batisseur.y]&0xf) >> 1;
        return temp <= (cases[l][c] &0xf);
    }

    /**
     * Ajoute un joueur sur la case de la grille.
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param joueur
     */
    public void ajouterJoueur(int l, int c, int joueur){
        cases[l][c] = ((cases[l][c] & 0xf) | joueur);
    }

    /**
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param batisseur position (x;y) d'un batisseur
     * @return
     */
    public boolean atteignable(int l, int c, Point batisseur){
        int a = Math.abs(l-batisseur.x);
        int b = Math.abs(c-batisseur.y);
        return (a + b > 0) && (a < 2) && (b < 2);
    }

    public int getLignes() {
        return lignes;
    }

    public int getColonnes() {
        return colonnes;
    }

    public int getTypeBatiments(int l, int c){
        return cases[l][c] & 0xf;
    }
    public int getTypeBatisseurs(int l, int c){
        return cases[l][c] & (~0xf);
    }
}