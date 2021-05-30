package Modele;

import java.awt.*;
import java.util.ArrayList;

public abstract class Joueur {
    protected int num_joueur;
    protected Jeu jeu;
    protected ArrayList<Point> batisseurs;

    protected Joueur(Jeu _jeu, int _num_joueur) {
        jeu = _jeu;
        num_joueur = _num_joueur;
        batisseurs = new ArrayList<Point>();
    }

    public abstract void joue();

    public abstract void joue(Point clic_position);

    public int getNum_joueur() {
        return num_joueur;
    }

    public ArrayList<Point> getBatisseurs() {
        return batisseurs;
    }

    public void addBatisseur(Point batisseur) {
        batisseurs.add(batisseur);
    }
}
