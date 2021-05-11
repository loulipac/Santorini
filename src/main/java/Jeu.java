package main.java;

import java.awt.*;
import java.util.ArrayList;

public class Jeu {
    Plateau plateau;


    public static final int JOUEUR1 = 16;
    public static final int JOUEUR2 = 32;

    private int joueur_en_cours;

    ArrayList<Point> ouvriersJoueur1;
    ArrayList<Point> ouvriersJoueur2;

    /**
     * Instantie une classe jeu
     */
    public Jeu(int l, int c){
        joueur_en_cours = JOUEUR1;
        plateau = new Plateau(l,c);
        ouvriersJoueur1 = new ArrayList<>();
        ouvriersJoueur2 = new ArrayList<>();
    }


    public void Jouer(int l,int c,Point ouvrier){
        while(!Avancer(l, c, ouvrier)){
            // LA CASE NEST PAS VALIDE
        }

        joueur_en_cours = joueur_en_cours == JOUEUR1 ? JOUEUR2: JOUEUR1;
    }

    public boolean Avancer(int l,int c,Point ouvrier){
        if(plateau.atteignable(l,c,ouvrier.x,ouvrier.y) && plateau.estLibre(l,c) && plateau.deplacementPossible(l,c,ouvrier.x,ouvrier.y)){
            plateau.ajouterJoueur(l,c,joueur_en_cours);
            return true;
        }
        return false;
    }
//
//    public boolean Construire(){
//
//    }

    /**
     * peutConstruire
     * Vérifie si un @ouvrier peut construire sur la case en @l @c
     * @param l un indice de ligne sur la grille
     * @param c un indice de colonne sur la grille
     * @param ouvrier le Point d'un ouvrier
     * @see Plateau#atteignable(int, int, int, int)
     * @return vrai si l'ouvrier peut construire ici.
     */
    public boolean peutConstruire(int l, int c,Point ouvrier){
        return (plateau.atteignable(ouvrier.x,ouvrier.y,l,c) && !plateau.estCoupole(l,c) && plateau.estLibre(l,c));
    }
}
