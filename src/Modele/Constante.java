package Modele;

import java.awt.*;

/**
 * Fichier de constante.
 */
public class Constante {
    private Constante() {
    }

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

    public static final int PORT = 16518;

    public static final String[] TEXTE_ETAPES = {
            """
                Bienvenue dans le tutoriel de Santorini !
                C’est ici que tu vas apprendre à devenir un pro de ce jeu.
                Mais avant de devenir un pro commençons par voir les bases.
                On y va ?""",
            """
                Commençons à jouer !
                Au début du jeu, chaque joueur doit placer ses deux pions où il veut sur le plateau.""",
            "Tu es le joueur bleu, positionne par exemple tes 2 personnages sur les cases indiquées.",
            """
               C’est au tour de ton adversaire, le joueur rouge, de placer ses deux bâtisseurs !
               Voyons où il les place.""",
            """
                A chaque tour, tu dois bouger un de tes pions.
                Pour cela tu dois cliquer sur le personnage que tu veux déplacer, puis sélectionner la case sur laquelle tu veux aller.
                """,
            "C’est à toi de jouer, clic sur le personnage. ",
            "Va sur la case de droite du personnage pour le déplacer.",
            """
               Super ! Tu dois ensuite construire un étage d’un bâtiment sur une des cases autour du personnage.
               Seul le personnage que tu as déplacé précédemment peut construire un étage durant ce tour.
               """,
            "A toi de jouer, construis le rez-de-chaussée sur la case en dessous du personnage.",
            """
                Génial! Tu as effectué tes deux actions, c’est donc la fin de ton tour.
                C’est maintenant à ton adversaire de jouer.
                """,
            "C’est de nouveau ton tour, essaie de monter sur le premier étage que tu as construit au tour d’avant",
            """
                Trop bien !
                N’oublie pas, tu ne peux monter que d’un étage à la fois et descendre de n’importe quel étage.""",
            """
                Tu peux voir qu’il n'est pas possible de construire sur ta propre case.
                Construit un étage sur la case en diagonale à droite.""",
            """
                Génial !
                Maintenant avançons un peu dans la partie !""",
            """
                Pour bloquer ton adversaire qui risque de gagner la partie en arrivant sur un bâtiment de 3 étages, tu peux construire un dôme sur une tour à 3 étages pour l’empêcher de monter dessus.
                Mais méfie toi, car toi non plus tu ne peux plus monter dessus !""",
            """
               Attention, ton adversaire va gagner !
               Pour éviter cela, place un dôme au-dessus de la tour. Il ne gagnera pas ce tour là !
               """,
            "C’est maintenant au tour de ton adversaire de jouer !",
            """
                Tu as la possibilité de gagner, profites-en !
                Déplace ton pion sur la tour à 3 étage.
                """,
            """
                Félicitations, tu as gagné(e) !
                Le tutoriel est fini, tu es maintenant un pro de ce jeu.
                Amuse toi bien !
                """
    };
}
