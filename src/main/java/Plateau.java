package main.java;

import java.util.Arrays;

public class Plateau {
    private int[][] cases;
    private int ligne;
    private int colonne;

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
        ligne = l;
        colonne = c;
    }

    /**
     * estVide
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est vide
     */
    public boolean estVide(int l, int c) {
        return (cases[l][c] & 0xF) == VIDE;
    }

    /**
     * estRDC
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est un rez-de-chaussée (bâtiment de hauteur 1)
     */
    public boolean estRDC(int l, int c) {
        return (cases[l][c] & 0xF) == RDC;
    }

    /**
     * estEtage
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est un étage (bâtiment de hauteur 2)
     */
    public boolean estEtage(int l, int c) {
        return (cases[l][c] & 0xF) == ETAGE;
    }

    /**
     * estToit
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est un toit (bâtiment de hauteur 3)
     */
    public boolean estToit(int l, int c) {
        return (cases[l][c] & 0xF) == TOIT;
    }

    /**
     * estCoupole
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return vrai si la case de la grille est une coupole (bâtiment de hauteur 4)
     */
    public boolean estCoupole(int l, int c) {
        return (cases[l][c] & 0xF) == COUPOLE;
    }

    /**
     * ajouterRDC
     * Ajoute un rez-de-chaussée (bâtiment de hauteur 1) sur une case de la grille
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public void ajouterRDC(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | RDC;
    }

    /**
     * ajouterEtage
     * Ajoute un étage (bâtiment de hauteur 2) sur une case de la grille
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public void ajouterEtage(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | ETAGE;
    }

    /**
     * ajouterToit
     * Ajoute un toit (bâtiment de hauteur 3) sur une case de la grille
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public void ajouterToit(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | TOIT;
    }

    /**
     * ajouterCoupole
     * Ajoute une coupole (bâtiment de hauteur 4) sur une case de la grille
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public void ajouterCoupole(int l, int c) {
        cases[l][c] = (cases[l][c] & ~0xf) | COUPOLE;
    }

    /**
     * estJoueur
     * Vérifie que l'on peut sélectionner l'ouvrier selectionné appartient au joueur
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param joueur
     * @return
     */
    public boolean estJoueur(int l,int c, int joueur){
        return (cases[l][c] & ~0xf) == joueur;
    }

    /**
     * estJoueur
     * Vérifie que la case de la grille ne contient pas de joueur
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public boolean estLibre(int l, int c) {
        return (cases[l][c] & (~0xf)) == 0;
    }

    /**
     * deplacementPossible
     * Vérifie que la case de la grille
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param dl
     * @param dc
     * @return
     */

    public boolean deplacementPossible(int l, int c, int dl, int dc){
        int temp = (cases[dl][dc]&0xf) >> 1;
        return temp <= (cases[l][c] &0xf);
    }

    public void ajouterJoueur(int l, int c, int joueur){
        cases[l][c] = cases[l][c] & (~0xf) | joueur;
    }

    /**
     * atteignable
     *
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @return
     */
    public boolean atteignable(int l, int c, int dl, int dc){
        int a = Math.abs(l-dl);
        int b = Math.abs(c-dc);
        return a+b>0 && a<2 && b<2;
    }
}