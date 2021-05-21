package Vue;

import static Modele.Constante.*;
import Modele.Jeu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Classe Listener qui gère les mouvements de souris pour définir le curseur.
 */
public class EcouteurDeMouvementDeSouris implements MouseMotionListener {

    JeuGraphique jg;
    Jeu j;
    int largeur_plateau, hauteur_plateau;
    Cursor c_pas_bleu;
    Cursor c_pas_rouge;
    Cursor c_pas_gris;
    Cursor c_outil_bleu;
    Cursor c_outil_rouge;
    Cursor c_outil_gris;
    Cursor c_drapeau_bleu;
    Cursor c_drapeau_rouge;
    Cursor c_drapeau_gris;
    Cursor c_defaut_gris;
    Cursor c_defaut_rouge;
    Cursor c_defaut_bleu;

    final static Point CENTRE = new Point(16, 16);
    final static Point HAUT_GAUCHE = new Point(0, 0);

    /**
     * Constructeur qui génère un ensemble de curseur.
     * @param j
     * @param jg
     */
    public EcouteurDeMouvementDeSouris(Jeu j, JeuGraphique jg) {
        this.jg = jg;
        this.j = j;
        c_defaut_gris = creerCurseurGenerique( "defaut_gris", HAUT_GAUCHE);
        c_defaut_rouge = creerCurseurGenerique("defaut_rouge", HAUT_GAUCHE);
        c_defaut_bleu = creerCurseurGenerique("defaut_bleu", HAUT_GAUCHE);
        c_pas_bleu = creerCurseurGenerique("pas_bleu", CENTRE);
        c_pas_rouge = creerCurseurGenerique("pas_rouge", CENTRE);
        c_pas_gris = creerCurseurGenerique( "pas_gris", CENTRE);
        c_outil_bleu = creerCurseurGenerique("outil_bleu", HAUT_GAUCHE);
        c_outil_rouge = creerCurseurGenerique("outil_rouge", HAUT_GAUCHE);
        c_outil_gris = creerCurseurGenerique( "outil_gris", HAUT_GAUCHE);
        c_drapeau_bleu = creerCurseurGenerique("drapeau_bleu", CENTRE);
        c_drapeau_rouge = creerCurseurGenerique( "drapeau_rouge", CENTRE);
        c_drapeau_gris = creerCurseurGenerique( "drapeau_gris", CENTRE);
    }

    /**
     * Crée un objet de type Cursor depuis son nom de fichier.
     * @param fichier_nom
     * @param decallage
     * @return le curseur crée
     */
    private Cursor creerCurseurGenerique(String fichier_nom, Point decallage) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image img = toolkit.getImage(CHEMIN_RESSOURCE + "/curseur/" + fichier_nom + ".png");
            return toolkit.createCustomCursor(img, decallage, "c_" + fichier_nom);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Définis le curseur selon sa position sur la grille et la situation du jeu.
     * @param e
     * @see Jeu#getSituation()
     * @see Jeu#estAtteignable(int, int)
     * @see Jeu#getJoueur_en_cours()
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.largeur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getColonnes();
        this.hauteur_plateau = jg.getTailleCase() * jg.getJeu().getPlateau().getLignes();
        if (e.getX() <= largeur_plateau && e.getY() <= hauteur_plateau) {
            int pos_x = e.getX() / jg.getTailleCase();
            int pos_y = e.getY() / jg.getTailleCase();
            if (j.getSituation() == DEPLACEMENT) {
                if (jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == JOUEUR1) {
                        jg.setCursor(c_pas_bleu);
                    } else {
                        jg.setCursor(c_pas_rouge);
                    }
                } else {
                    jg.setCursor(c_pas_gris);
                }
            } else if (j.getSituation() == CONSTRUCTION) {
                if (jg.getJeu().estAtteignable(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == JOUEUR1) {
                        jg.setCursor(c_outil_bleu);
                    } else {
                        jg.setCursor(c_outil_rouge);
                    }
                } else {
                    jg.setCursor(c_outil_gris);
                }

            } else if (j.getSituation() == PLACEMENT) {
                if (jg.getJeu().getPlateau().estLibre(pos_y, pos_x)) {
                    if (j.getJoueur_en_cours() == JOUEUR1) {
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
