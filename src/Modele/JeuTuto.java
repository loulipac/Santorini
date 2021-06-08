package Modele;

import Patterns.Observateur;
import Utile.Constante;

import java.awt.*;
import java.util.Objects;

import static Utile.Constante.*;

/**
 * Classe permettant de gérer tout le processus d'une partie de type tutoriel en étant guidé dans ses actions.
 */
public class JeuTuto extends Jeu {
    private int num_etape;
    private Point clic_prec;
    private final int[] clic_etapes;
    private static final String AJOUTER_JOUEUR = "ajouterJoueur";

    public JeuTuto(Observateur o) {
        super(o);
        this.num_etape = 0;
        chargerEtape(num_etape);
        this.clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
        this.clic_etapes = new int[Constante.TEXTE_ETAPES.length];
    }

    /**
     * Modifie le contenu du plateau du tutoriel pour une étape donnée
     *
     * @param etape le numéro d'étape
     */
    public void chargerEtape(int etape) {
        this.clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
        this.num_etape = etape;
        this.getPlateau().RAZ();
        //
        // Attention ! C'est la position AU DEBUT du tour
        //
        Plateau plateau = this.getPlateau();


        switch (etape) {
            case 3, 4:
                plateau.ajouterJoueur(new Point(1, 1), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                break;
            case 5, 6, 7:
                plateau.ajouterJoueur(new Point(1, 1), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 8, 9:
                plateau.ajouterJoueur(new Point(1, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 10, 11:
                plateau.ajouterJoueur(new Point(1, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                plateau.setBatiments(new Point(2, 2), 1);
                break;
            case 12, 13:
                plateau.ajouterJoueur(new Point(1, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                plateau.setBatiments(new Point(2, 2), 1);
                plateau.setBatiments(new Point(3, 3), 1);
                break;
            case 14, 15:
                plateau.ajouterJoueur(new Point(2, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                plateau.setBatiments(new Point(2, 2), 1);
                plateau.setBatiments(new Point(3, 3), 1);
                break;
            case 16, 17:
                plateau.ajouterJoueur(new Point(2, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(3, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 3), this.getJ2());
                plateau.setBatiments(new Point(2, 2), 1);
                plateau.setBatiments(new Point(3, 3), 2);
                break;
            case 18, 19:
                plateau.ajouterJoueur(new Point(2, 1), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(2, 2), this.getJ2());
                plateau.setBatiments(new Point(1, 1), 1);
                plateau.setBatiments(new Point(1, 2), 1);
                plateau.setBatiments(new Point(2, 2), 2);
                plateau.setBatiments(new Point(2, 3), 1);
                plateau.setBatiments(new Point(3, 3), 3);
                break;
            case 20, 21:
                plateau.ajouterJoueur(new Point(2, 1), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(2, 2), this.getJ2());
                plateau.setBatiments(new Point(1, 1), 1);
                plateau.setBatiments(new Point(1, 2), 1);
                plateau.setBatiments(new Point(2, 2), 2);
                plateau.setBatiments(new Point(2, 3), 1);
                plateau.setBatiments(new Point(3, 3), 4);
                break;
            case 22, 23:
                plateau.ajouterJoueur(new Point(1, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 4), this.getJ2());
                plateau.setBatiments(new Point(1, 1), 2);
                plateau.setBatiments(new Point(1, 2), 2);
                plateau.setBatiments(new Point(1, 3), 1);
                plateau.setBatiments(new Point(2, 2), 3);
                plateau.setBatiments(new Point(2, 3), 2);
                plateau.setBatiments(new Point(3, 3), 4);
                break;
            case 24:
                plateau.ajouterJoueur(new Point(2, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 2), this.getJ1());
                plateau.ajouterJoueur(new Point(4, 3), this.getJ2());
                plateau.ajouterJoueur(new Point(1, 4), this.getJ2());
                plateau.setBatiments(new Point(1, 1), 2);
                plateau.setBatiments(new Point(1, 2), 2);
                plateau.setBatiments(new Point(1, 3), 1);
                plateau.setBatiments(new Point(2, 2), 3);
                plateau.setBatiments(new Point(2, 3), 2);
                plateau.setBatiments(new Point(3, 3), 4);
                break;
            default:
                break;
        }
    }

    /**
     * Redirige vers des sous-fonction en fonction du numéro d'étape
     *
     * @param num_etape le numéro d'étape
     * @param position  la case cliquée par la souris
     */
    public void joueEtape(int num_etape, Point position) {

        String condition = num_etape > 13 ? null : AJOUTER_JOUEUR;
        switch (num_etape) {
            case 2 -> verifieEtape(num_etape, position, new Point(1, 1), new Point(3, 2), condition);
            case 6 -> verifieEtape(num_etape, position, new Point(1, 1), null, null);
            case 7, 12, 22 -> verifieEtape(num_etape, position, new Point(1, 2), null, condition);
            case 9, 13, 23 -> verifieEtape(num_etape, position, new Point(2, 2), null, condition);
            case 15, 19 -> verifieEtape(num_etape, position, new Point(3, 3), null, null);
            default -> { /* NE RIEN FAIRE */}
        }
    }

    /**
     * Modifie le plateau en fonction de la position des clics de la souris
     *
     * @param num_etape   le numéro de l'étape
     * @param pos_clic    la case cliquée par la souris
     * @param pos_valide1 la case permettant de valider l'étape
     * @param pos_valide2 la case permettant de valider l'étape
     * @param condition   détermine s'il faut ajouter un pion ou non
     */
    private void verifieEtape(int num_etape, Point pos_clic, Point pos_valide1, Point pos_valide2, String condition) {
        if (!pos_clic.equals(clic_prec) && (pos_clic.equals(pos_valide1) || pos_clic.equals(pos_valide2))) {
            clic_prec = pos_clic;
            if (Objects.equals(condition, AJOUTER_JOUEUR)) {
                this.getPlateau().ajouterJoueur(pos_clic, this.getJ1());
            }
            clic_etapes[num_etape]++;
        }
    }

    /**
     * Getter de clic_etapes pour une étape donnée
     *
     * @param num_etape le numéro d'étape
     */
    public int getClic_etape(int num_etape) {
        return clic_etapes[num_etape];
    }

    /**
     * Setter de clic_etapes pour une étape donnée
     *
     * @param num_etape le numéro d'étape
     * @param valeur    la valeur pour une étape donnée
     */
    public void setClic_etape(int num_etape, int valeur) {
        this.clic_etapes[num_etape] = valeur;
    }

}
