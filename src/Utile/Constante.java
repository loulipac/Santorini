package Utile;

import java.awt.*;

/**
 * Fichier de constante.
 */
public class Constante {
    private Constante() { /* Non utilisé car utilisation des attributs statiques */ }

    /**
     * Chemin d'accès au dossier ressources
     */
    public static final String CHEMIN_RESSOURCE = "./ressources";

    /**
     * Chemin d'accès au dossier de sauvegardes
     */
    public static final String SAVES_PATH = "./saves/";

    public static final Dimension FENETRE_1600x900 = new Dimension(1600, 900);
    public static final Dimension DEFAULT_FENETRE_TAILLE = FENETRE_1600x900;

    /**
     * Code de situation de sélection de batisseur
     */
    public static final int SELECTION = 0;

    /**
     * Code de situation de déplacement du batisseur sélectionné
     */
    public static final int DEPLACEMENT = 1;

    /**
     * Code de situation de construction par rapport au batisseur sélectionné
     */
    public static final int CONSTRUCTION = 2;

    /**
     * Code de situation de placement des batisseurs
     */
    public static final int PLACEMENT = 3;

    /**
     * Numéro du joueur 1.
     */
    public static final int JOUEUR1 = 8;

    /**
     * Numéro du joueur 2.
     */
    public static final int JOUEUR2 = 16;

    /**
     * Code d'attente exclusif au tutoriel
     */
    public static final int ATTENTE = 32;

    /**
     * Nombre de batisseurs que le joueur pose pendant la situation de placement du jeu
     */
    public static final int NOMBRE_BATISSEUR_JOUEUR = 2;

    public static final int UNDO = 1;
    public static final int REDO = 0;

    public static final int IA_FACILE = 1;
    public static final int IA_NORMAL = 2;
    public static final int IA_DIFFICILE = 3;

    /**
     * Valeur d'une case vide sur la grille
     */
    public static final int VIDE = 0;

    /**
     * Valeur d'une case de hauteur 1 sur la grille
     */
    public static final int RDC = 1;

    /**
     * Valeur d'une case de hauteur 2 sur la grille
     */
    public static final int ETAGE = 2;

    /**
     * Valeur d'une case de hauteur 3 sur la grille
     */
    public static final int TOIT = 3;

    /**
     * Valeur d'une case de hauteur 4, le maximum, sur la grille
     */
    public static final int COUPOLE = 4;


    // TODO: Utiliser ou supprimer
    public static final int[][] POIDS_CASES = {
            {100, 50, 25, 12, 0},
            {0, 100, 50, 25, 12},
            {0, 0, 100, 50, 25},
            {0, 0, 0, 0, 100},
            {0, 0, 0, 100, 50},
    };

    /**
     * Nombre de lignes du plateau.
     */
    public static final int PLATEAU_LIGNES = 5;

    /**
     * Nombre de colonnes du plateau.
     */
    public static final int PLATEAU_COLONNES = 5;

    /**
     * Position de la case centrale. Utilisé pour l'heuristique de l'IA difficile.
     * @see IA.IADifficile
     */
    public static final Point CASE_CENTRALE = new Point(2, 2);

    /**
     * Ratio d'un bouton classique (large) pour garder un bon ratio d'image (en fonction de la hauteur).
     */
    public static final double RATIO_BOUTON_CLASSIQUE = (double) 508 / 98;

    /**
     * Ratio d'un bouton classique (large) pour garder un bon ratio d'image (en fonction de la largeur).
     */
    public static final double RATIO_BOUTON_CLASSIQUE_INVERSE = (double) 98 / 508;

    /**
     * Ratio d'un bouton (petit) pour garder un bon ratio d'image.
     */
    public static final double RATIO_BOUTON_PETIT = (double) 259 / 216;

    /**
     * Commande de la clé Echap pour l'ouverture des paramètres
     */
    public static final String ECHAP_KEY = "echap";

    /**
     * Port de connexion au serveur.
     */
    public static final int PORT = 16518;

    /**
     * Nom du jeu.
     */
    public static final String NOM_JEU = "Santorini";

    /**
     * Texte lancé lors d'une erreur de chargement d'une image.
     * @see Utile
     */
    public static final String ERREUR_IMAGE_FOND = "Erreur image de fond: ";

    /**
     * Nom de la police utilisée pour les titres et quelques textes.
     */
    public static final String LILY_SCRIPT = "Lily Script One";

    /**
     * Nom de la police utilisée pour les règles.
     */
    public static final String LORA = "Lora";

    /**
     * Nom du fichier de bruit de construction utilisé dans Jeu.
     * @see Modele.Jeu
     */
    public static final String SON_CONSTRUCTION = "son_construction.wav";

    /**
     * Tableau du scénario des différentes étapes du tutoriel.
     */
    public static final String[] TEXTE_ETAPES = {
            """
                Bienvenue dans le tutoriel de Santorini !
                C’est ici que tu vas apprendre à devenir un pro de ce jeu.
                Mais avant d’en devenir un, commençons par voir les bases.
                On y va ?""",
            """
                Commençons à jouer !
                Au début du jeu, chaque joueur doit placer ses deux pions où il veut sur le plateau.""",
            "Tu es le joueur bleu, positionne par exemple tes 2 bâtisseurs sur les cases indiquées.",
            """
               C’est au tour de ton adversaire, le joueur rouge, de placer ses deux bâtisseurs !
               Voyons où il les place. (Suivant)""",
            """
               C’est au tour de ton adversaire, le joueur rouge, de placer ses deux bâtisseurs !
               Voyons où il les place.""",
            """
                A chaque tour, tu dois déplacer un de tes pions.
                Pour cela, tu dois cliquer sur le bâtisseur que tu veux déplacer, puis choisir une des cases adjacentes sur laquelle tu veux aller.""",
            "C’est à toi de jouer, clique sur le personnage.",
            "Clique sur la case de droite adjacente à ton bâtisseur pour le déplacer.",
            """
              Super ! Il faut ensuite construire l’étage d’un bâtiment sur une des cases autour du personnage.
              Dans un même tour, seul le personnage que tu as déplacé précédemment peut construire un étage.""",
            "A toi de jouer, construis le rez-de-chaussée sur la case en dessous du personnage.",
            """
               Génial! Tu as effectué tes deux actions, ton tour est maintenant fini.
               C’est à ton adversaire de jouer. (Suivant)""",
            """
               Génial! Tu as effectué tes deux actions, ton tour est maintenant fini.
               C’est à ton adversaire de jouer.""",
            "C’est de nouveau ton tour, clique sur ton personnage.",
            "Essaie de monter sur le premier étage que tu as construit au tour d’avant.",
            """
                Bien joué !
                N’oublie pas, tu ne peux monter que d’un étage à la fois mais tu peux descendre de n’importe quel étage.""",
            """
                Tu peux voir qu’il n'est pas possible de construire sur ta propre case.
                Construit un étage sur la case en diagonale à droite.""",
            """
               Génial !
               Maintenant avançons un peu dans la partie ! (Suivant)""",
            """
               Génial !
               Maintenant avançons un peu dans la partie !""",
            "Le premier qui arrive sur un bâtiment de 3 étages gagne la partie, mais pour contrer ça tu peux construire un dôme sur le bâtiment, ce qui empêche de monter dessus.",
            """
               Attention, ton adversaire va gagner !
               Pour éviter cela, place un dôme au-dessus de la tour.""",
            """
            Il ne gagnera pas ce tour là !
            Avançons encore dans la partie. (Suivant)""",
            """
            Il ne gagnera pas ce tour là !
            Avançons encore dans la partie.""",
            """
                Tu as la possibilité de gagner, profites-en !
                Clique sur ton personnage.""",
            "Déplace ton pion sur la tour à 3 étages.",
            """
                C’est gagné !
                Le tutoriel est fini, tu es maintenant un pro de ce jeu.
                Amuse toi bien !"""
    };
}
