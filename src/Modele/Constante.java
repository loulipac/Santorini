package Modele;

import java.awt.*;

/**
 * Fichier de constante.
 */
public class Constante {
    private Constante() {}

    public static final String CHEMIN_RESSOURCE = "./src/Ressources";
    public static final String SAVES_PATH = "./saves/";

    public static final Dimension FENETRE_1920x1080 = new Dimension(1920, 1080);
    public static final Dimension FENETRE_1600x900 = new Dimension(1600, 900);
    public static final Dimension FENETRE_1366x768 = new Dimension(1366, 768);
    public static final Dimension FENETRE_1024x768 = new Dimension(1024, 768);
    public static final Dimension DEFAULT_FENETRE_TAILLE = FENETRE_1600x900;

    public static final int SELECTION = 0;
    public static final int DEPLACEMENT = 1;
    public static final int CONSTRUCTION = 2;
    public static final int PLACEMENT = 3;
    public static final int JOUEUR1 = 8;
    public static final int JOUEUR2 = 16;

    public static final int UNDO = 1;
    public static final int REDO = 0;

    public static final int IA_FACILE = 1;
    public static final int IA_NORMAL = 2;
    public static final int IA_DIFFICILE = 3;

    public static final int VIDE = 0;
    public static final int RDC = 1;
    public static final int ETAGE = 2;
    public static final int TOIT = 3;
    public static final int COUPOLE = 4;

    public static final int PLATEAU_LIGNES = 5;
    public static final int PLATEAU_COLONNES = 5;

    public static final Point CASE_CENTRALE = new Point(2, 2);
    public static final int POIDS_BASE = 1;

    public static final double RATIO_BOUTON_CLASSIQUE = (double) 508/98;
    public static final double RATIO_BOUTON_CLASSIQUE_INVERSE = (double) 98/508;
    public static final double RATIO_BOUTON_PETIT = (double) 259/216;

    public static final String ECHAP_KEY = "echap";
}
