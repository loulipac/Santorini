package Modele;

import java.awt.*;

/**
 * Fichier de constante.
 */
public class Constante {

    /**
     * Chemin vers le dossier de ressource ({@value #CHEMIN_RESSOURCE})
     */
    public static final String CHEMIN_RESSOURCE = "./src/Ressources";

    public static final Dimension FENETRE_1920x1080 = new Dimension(1920, 1080);
    public static final Dimension FENETRE_1600x1900 = new Dimension(1600, 900);
    public static final Dimension FENETRE_1366x768 = new Dimension(1366, 768);
    public static final Dimension FENETRE_1024x768 = new Dimension(1024, 768);
    public static final Dimension DEFAULT_FENETRE_TAILLE = FENETRE_1600x1900;

    public static final int SELECTION = 0;
    public static final int DEPLACEMENT = 1;
    public static final int CONSTRUCTION = 2;
    public static final int PLACEMENT = 3;
    public static final int JOUEUR1 = 8;
    public static final int JOUEUR2 = 16;
}
