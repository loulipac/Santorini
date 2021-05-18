package Vue;

import Modele.Constante;
import Modele.Jeu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Field;

public class EcouteurDeMouvementDeSouris implements MouseMotionListener {

    JeuGraphique jg;
    Jeu j;
    int largeur_plateau, hauteur_plateau;
    Cursor c_deplacer_bleu;
    Cursor c_deplacer_rouge;
    Cursor c_deplacer_gris;
    Cursor c_construire_bleu;
    Cursor c_construire_rouge;
    Cursor c_construire_gris;
    Cursor c_drapeau_bleu;
    Cursor c_drapeau_rouge;
    Cursor c_drapeau_gris;
    Cursor c_defaut_gris;
    Cursor c_defaut_rouge;
    Cursor c_defaut_bleu;

    final static Point CENTRE = new Point(16, 16);
    final static Point HAUT_GAUCHE = new Point(0, 0);

    public EcouteurDeMouvementDeSouris(Jeu j, JeuGraphique jg) {
        this.jg = jg;
        this.j = j;
        creerCurseurGenerique("c_defaut_gris", "defaut_gris", HAUT_GAUCHE);
        creerCurseurGenerique("c_defaut_rouge", "defaut_rouge", HAUT_GAUCHE);
        creerCurseurGenerique("c_defaut_bleu", "defaut_bleu", HAUT_GAUCHE);
        creerCurseurGenerique("c_deplacer_bleu", "pas_bleu", CENTRE);
        creerCurseurGenerique("c_deplacer_rouge", "pas_rouge", CENTRE);
        creerCurseurGenerique("c_deplacer_gris", "pas_gris", CENTRE);
        creerCurseurGenerique("c_construire_bleu", "outil_bleu", HAUT_GAUCHE);
        creerCurseurGenerique("c_construire_rouge", "outil_rouge", HAUT_GAUCHE);
        creerCurseurGenerique("c_construire_gris", "outil_gris", HAUT_GAUCHE);
        creerCurseurGenerique("c_drapeau_bleu", "drapeau_bleu", CENTRE);
        creerCurseurGenerique("c_drapeau_rouge", "drapeau_rouge", CENTRE);
        creerCurseurGenerique("c_drapeau_gris", "drapeau_gris", CENTRE);
    }

    private void creerCurseurGenerique(String variable_nom, String fichier_nom, Point decallage) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image img = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/" + fichier_nom + ".png");

            Field curseur_champ = this.getClass().getDeclaredField(variable_nom);

            curseur_champ.setAccessible(true);
            curseur_champ.set(
                    this,
                    toolkit.createCustomCursor(img, decallage, variable_nom)
            );
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.largeur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getColonnes();
        this.hauteur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getLignes();
        if (e.getX() <= largeur_plateau && e.getY() <= hauteur_plateau) {
            int pos_x = e.getX() / jg.getTailleCase();
            int pos_y = e.getY() / jg.getTailleCase();
            if (j.getSituation() == Constante.DEPLACEMENT) {
                if (jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_deplacer_bleu);
                    } else {
                        jg.setCursor(c_deplacer_rouge);
                    }
                } else {
                    jg.setCursor(c_deplacer_gris);
                }
            } else if (j.getSituation() == Constante.CONSTRUCTION) {
                if (jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_construire_bleu);
                    } else {
                        jg.setCursor(c_construire_rouge);
                    }
                } else {
                    jg.setCursor(c_construire_gris);
                }

            } else if (j.getSituation() == Constante.PLACEMENT) {
                if (jg.getJeu().getPlateau().estLibre(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_drapeau_bleu);
                    } else {
                        jg.setCursor(c_drapeau_rouge);
                    }
                } else {
                    jg.setCursor(c_drapeau_gris);
                }

            } else {
                jg.setCursor(c_defaut_gris);
            }
        }
    }
}
