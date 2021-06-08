package Listener;

import static Utile.Constante.*;

import Modele.Jeu;
import Modele.JoueurIA;
import Vue.JeuGraphique;
import Vue.Panels;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Classe Listener qui gère les mouvements de souris pour définir le curseur.
 */
public class EcouteurDeMouvementDeSouris implements MouseMotionListener {
    private final JeuGraphique jg;
    private final Jeu jeu;
    private final Panels p;
    private final Cursor c_defaut_rouge;
    private final Cursor c_defaut_bleu;
    private final Cursor c_hand_rouge;
    private final Cursor c_hand_bleu;
    private final Cursor c_hand_gris;
    private final Cursor c_hourglass;
    private final int numJoueurBleu;
    static final Point CENTRE = new Point(16, 16);
    static final Point HAUT_GAUCHE = new Point(0, 0);

    /**
     * Constructeur qui génère un ensemble de curseur.
     */
    public EcouteurDeMouvementDeSouris(Jeu j, JeuGraphique jg, Panels p) {
        this.jg = jg;
        this.jeu = j;
        this.p = p;
        this.numJoueurBleu = (j.getConfigurationPartie().isJoueur1Bleu() ? JOUEUR1 : JOUEUR2);
        c_defaut_rouge = creerCurseurGenerique("defaut_rouge", HAUT_GAUCHE);
        c_defaut_bleu = creerCurseurGenerique("defaut_bleu", HAUT_GAUCHE);
        c_hand_rouge = creerCurseurGenerique("hand_rouge", CENTRE);
        c_hand_bleu = creerCurseurGenerique("hand_bleu", CENTRE);
        c_hand_gris = creerCurseurGenerique("hand_gris", CENTRE);
        c_hourglass = creerCurseurGenerique("hourglass", CENTRE);
    }

    /**
     * Crée un objet de type Cursor depuis son nom de fichier.
     *
     * @return le curseur crée
     */
    public static Cursor creerCurseurGenerique(String fichier_nom, Point decallage) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image img = toolkit.getImage(CHEMIN_RESSOURCE + "/curseur/" + fichier_nom + ".png");
            return toolkit.createCustomCursor(img.getScaledInstance(32, 32, Image.SCALE_SMOOTH), decallage, "c_" + fichier_nom);
        } catch (Exception ex) {
            System.err.println("Echec de création du curseur : " + fichier_nom);
        }
        return null;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        // NON UTILISÉ
    }


    /**
     * Définis le curseur selon sa position sur la grille et la situation du jeu.
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        if (e.getX() > (jg.getTailleCase() * PLATEAU_COLONNES) ||
                e.getY() > (jg.getTailleCase() * PLATEAU_LIGNES))
            return;

        int pos_x = e.getX() / jg.getTailleCase();
        int pos_y = e.getY() / jg.getTailleCase();
        Point position = new Point(pos_y, pos_x);
        if ((pos_x > (PLATEAU_LIGNES - 1)) || (pos_y > (PLATEAU_COLONNES - 1))) return;

        if ((jeu.getJoueurEnCours().getClass() == JoueurIA.class) ||
                (jeu.getNetUser() != null && jeu.getNetUser().getNumJoueur() != jeu.getJoueurEnCours().getNum_joueur())
        ) {
            jg.setCursor(c_hourglass);
            return;
        }

        switch (jeu.getSituation()) {
            case ATTENTE:
                jg.setCursor(c_hourglass);
                break;
            case PLACEMENT:
                setCursor((jg.getJeu().estAtteignable(position)), c_hand_gris, c_hand_rouge, c_hand_bleu);
                jg.setCase_sous_souris(position);
                jg.repaint();
                return;

            case DEPLACEMENT:
            case CONSTRUCTION:
                if (jg.getJeu().estAtteignable(position) && !jg.getJeu().getPlateau().estBatisseur(position, jeu.getJoueurEnCours())) {
                    setCursor(true, null, c_hand_rouge, c_hand_bleu);
                    jg.setCase_sous_souris(position);
                    jg.repaint();
                    return;
                }
                break;

            case SELECTION:
                if (jg.getJeu().getPlateau().estBatisseur(position, jeu.getJoueurEnCours())) {
                    setCursor(true, null, c_hand_rouge, c_hand_bleu);
                    jg.setCase_sous_souris(position);
                    jg.repaint();
                    return;
                }
                break;

            default:
                break;
        }
        jg.setCase_sous_souris(position);
        jg.repaint();
        setCursor(true, null, c_defaut_rouge, c_defaut_bleu);

    }

    /**
     * Met le curseur gris ou le curseur de la couleur du joueur selon la condition en paramètre.
     */
    private void setCursor(boolean condition, Cursor gris, Cursor rouge, Cursor bleu) {
        if (condition) {
            if (jeu.getJoueurEnCours().getNum_joueur() == numJoueurBleu) {
                jg.setCursor(bleu);
            } else {
                jg.setCursor(rouge);
            }
        } else {
            jg.setCursor(gris);
        }
    }
}

