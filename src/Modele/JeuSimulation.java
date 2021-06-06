package Modele;

import java.awt.*;
import java.util.ArrayList;

import static Modele.Constante.*;
import static Modele.Constante.CONSTRUCTION;

public class JeuSimulation {
    Plateau plateau;
    JoueurLambda joueur_en_cours;
    JoueurLambda j1, j2;
    int nombre_batisseurs;
    Point batisseur_en_cours;
    boolean jeu_fini;
    SaveStatut s;

    private class SaveStatut {
        Plateau p;
        JoueurLambda j1;
        JoueurLambda j2;

        public SaveStatut(Plateau p, JoueurLambda j1, JoueurLambda j2) {
            this.p = p;
            this.j1 = j1;
            this.j2 = j2;
        }
    }

    public JeuSimulation(Plateau plateau, JoueurLambda _j1, JoueurLambda _j2) {
        this.plateau = plateau;
        j1 = _j1.clone();
        j2 = _j2.clone();
        nombre_batisseurs = 0;
        batisseur_en_cours = null;
        jeu_fini = false;
        s = new SaveStatut(plateau, _j1, _j2);
    }

    public JeuSimulation(Plateau plateau, Joueur _j1, Joueur _j2) {
        this.plateau = plateau;
        j1 = createCloneJoueur(_j1);
        j2 = createCloneJoueur(_j2);
        nombre_batisseurs = 0;
        batisseur_en_cours = null;
        jeu_fini = false;
        s = new SaveStatut(plateau, j1, j2);
    }

    class JoueurLambda {
        protected ArrayList<Point> batisseurs;
        int num_joueur;

        public JoueurLambda(int _num_joueur) {
            num_joueur = _num_joueur;
            batisseurs = new ArrayList<Point>();
        }

        public ArrayList<Point> getBatisseurs() {
            return batisseurs;
        }

        public void addBatisseur(Point batisseur) {
            batisseurs.add(batisseur);
        }

        public int getNum_joueur() {
            return num_joueur;
        }

        @Override
        protected JoueurLambda clone() {
            JoueurLambda j = new JoueurLambda(this.getNum_joueur());
            for(Point batisseur : this.getBatisseurs()) {
                j.addBatisseur(batisseur);
            }
            return j;
        }
    }

    public JoueurLambda createCloneJoueur(Joueur _j) {
        JoueurLambda j = new JoueurLambda(_j.getNum_joueur());
        for(Point batisseur : _j.getBatisseurs()) {
            j.addBatisseur(batisseur);
        }
        return j;
    }

    public void setJoueur_en_cours(int num_joueur) {
        if(num_joueur == j1.getNum_joueur()) joueur_en_cours = j1;
        else if(num_joueur != j1.getNum_joueur()) joueur_en_cours = j2;
        else joueur_en_cours = null;
    }

    public void jouer(Point position, int situation) {
        switch (situation) {
            case SELECTION:
                joueSelection(position);
                break;
            case DEPLACEMENT:
                joueDeplacement(position);
                break;
            case CONSTRUCTION:
                joueConstruction(position);
                break;
        }
    }

    public void joueSelection(Point position) {
        int index = joueur_en_cours.getBatisseurs().indexOf(position);

        if (index == -1) {
            batisseur_en_cours = null;
        } else {
            batisseur_en_cours = joueur_en_cours.getBatisseurs().get(index);
        }
    }

    public void joueDeplacement(Point position) {
        Point prevPos = batisseur_en_cours;

        if (plateau.deplacementPossible(position, batisseur_en_cours)) {

            // remove batisseur at before pos
            plateau.ajouterJoueur(batisseur_en_cours, 0);
            // add batisseur at new pos
            plateau.ajouterJoueur(position, joueur_en_cours.getNum_joueur());
            // nouvelle position du batisseur en cours
            batisseur_en_cours = position;


            // changement de la position du batisseur
            joueur_en_cours.getBatisseurs().set(
                    joueur_en_cours.getBatisseurs().indexOf(prevPos),
                    position
            );

        }
    }

    public void reset() {
        plateau = s.p;
        j1 = s.j1;
        j2 = s.j2;
    }

    public void joueConstruction(Point position) {
        if (plateau.peutConstruire(position, batisseur_en_cours)) {
            plateau.ameliorerBatiment(position);
            // fin du tour
            batisseur_en_cours = null;
        }
    }

    public ArrayList<Point> getBatisseurs(int joueur) {
        if (joueur == j1.getNum_joueur()) return j1.getBatisseurs();
        else if (joueur == j2.getNum_joueur()) return j2.getBatisseurs();
        else return null;
    }

    public boolean estJeufini() {
        return jeu_fini;
    }
    public Plateau getPlateau() {
        return plateau;
    }

}
