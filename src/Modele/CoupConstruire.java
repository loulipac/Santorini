package Modele;

import static Modele.Constante.*;

import java.awt.Point;

public class CoupConstruire extends Commande {
    private Point position, batisseur;

    public CoupConstruire(int joueur, Point position, Point batisseur) {
        super(joueur);
        this.position = position;
        this.batisseur = batisseur;
    }

    @Override
    public void action(Jeu jeu, int type) {
        int valeur = type == REDO ? 1 : -1;
        jeu.getPlateau().MAJEtage(position, valeur);

        int situation = CONSTRUCTION;
        Point batisseur_en_cours = batisseur;
        if (type == REDO) {
            situation = SELECTION;
            batisseur_en_cours = null;
        }
        jeu.setSituation(situation);
        jeu.changerJoueur();
        jeu.setBatisseur_en_cours(batisseur_en_cours);
        jeu.MAJObservateur();
        jeu.iaJoue();
    }

    @Override
    public String toString() {
        String str = position.x + " " + position.y;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CoupConstruire)) return false;

        CoupConstruire c = (CoupConstruire) o;

        return joueur == c.joueur &&
                position.equals(c.position) &&
                batisseur.equals(c.batisseur);
    }
}
