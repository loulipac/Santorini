package Vue;

import Modele.Constante;
import Modele.Jeu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class EcouteurDeMouvementDeSouris implements MouseMotionListener {

    JeuGraphique jg;
    Jeu j;
    int largeur_plateau, hauteur_plateau;
    Cursor c_construire_rouge;
    Cursor c_construire_bleu;
    Cursor c_construire_gris;
    Cursor c_deplacer_rouge;
    Cursor c_deplacer_bleu;
    Cursor c_deplacer_gris;
    Cursor c_drapeau_rouge;
    Cursor c_drapeau_bleu;
    Cursor c_drapeau_gris;

    public EcouteurDeMouvementDeSouris(Jeu j, JeuGraphique jg) {
        this.jg = jg;
        this.j = j;
        creerCurseur();
    }

    private void creerCurseur() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image image_pas_bleu = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/pas_bleu.png");
        Image image_pas_rouge = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/pas_rouge.png");
        Image image_pas_gris = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/pas_gris.png");
        Image image_outil_bleu = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/outil_bleu.png");
        Image image_outil_rouge = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/outil_rouge.png");
        Image image_outil_gris = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/outil_gris.png");
        Image image_drapeau_bleu = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/drapeau_bleu.png");
        Image image_drapeau_rouge = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/drapeau_rouge.png");
        Image image_drapeau_gris = toolkit.getImage(Constante.CHEMIN_RESSOURCE + "/curseur/drapeau_gris.png");
        Point p = new Point(16, 16);
        c_deplacer_bleu = toolkit.createCustomCursor(image_pas_bleu, p, "c_pas_bleu");
        c_deplacer_rouge = toolkit.createCustomCursor(image_pas_rouge, p, "c_pas_rouge");
        c_deplacer_gris = toolkit.createCustomCursor(image_pas_gris, p, "c_deplacer_gris");
        c_construire_bleu = toolkit.createCustomCursor(image_outil_bleu, p, "c_outil_bleu");
        c_construire_rouge = toolkit.createCustomCursor(image_outil_rouge, p, "c_outil_rouge");
        c_construire_gris = toolkit.createCustomCursor(image_outil_gris, p, "c_construire_gris");
        c_drapeau_rouge = toolkit.createCustomCursor(image_drapeau_rouge, p, "c_drapeau_rouge");
        c_drapeau_bleu = toolkit.createCustomCursor(image_drapeau_bleu, p, "c_drapeau_bleu");
        c_drapeau_gris = toolkit.createCustomCursor(image_drapeau_gris, p, "c_drapeau_gris");
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
                if(jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_deplacer_bleu);
                    } else {
                        jg.setCursor(c_deplacer_rouge);
                    }
                } else {
                    jg.setCursor(c_deplacer_gris);
                }
            } else if (j.getSituation() == Constante.CONSTRUCTION) {
                if(jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_construire_bleu);
                    } else {
                        jg.setCursor(c_construire_rouge);
                    }
                } else {
                    jg.setCursor(c_construire_gris);
                }

            } else if (j.getSituation() == Constante.PLACEMENT) {
                if(jg.getJeu().getPlateau().estLibre(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == Constante.JOUEUR1) {
                        jg.setCursor(c_drapeau_bleu);
                    } else {
                        jg.setCursor(c_drapeau_rouge);
                    }
                } else {
                    jg.setCursor(c_drapeau_gris);
                }

            } else {
                jg.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
