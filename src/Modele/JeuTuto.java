package Modele;

import Patterns.Observateur;
import Utile.Constante;

import java.awt.*;
import static Utile.Constante.*;

public class JeuTuto extends Jeu {
    int num_etape;
    Point clic_prec;
    int[] clic_etapes;

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

        switch (etape) {
            case 3,4 :
                this.getPlateau().ajouterJoueur(new Point(1, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                break;
            case 5, 6, 7:
                this.getPlateau().ajouterJoueur(new Point(1, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 8, 9:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 10,11:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                break;
            case 12,13:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 14, 15:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 16,17:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),2);
                break;
            case 18,19:
                this.getPlateau().ajouterJoueur(new Point(2, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ2());
                construireBatiment(new Point(1,1),1);
                construireBatiment(new Point(1,2),1);
                construireBatiment(new Point(2,2),2);
                construireBatiment(new Point(2,3),1);
                construireBatiment(new Point(3,3),3);
                break;
            case 20,21:
                this.getPlateau().ajouterJoueur(new Point(2, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ2());
                construireBatiment(new Point(1,1),1);
                construireBatiment(new Point(1,2),1);
                construireBatiment(new Point(2,2),2);
                construireBatiment(new Point(2,3),1);
                construireBatiment(new Point(3,3),4);
                break;
            case 22,23:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 4), this.getJ2());
                construireBatiment(new Point(1,1),2);
                construireBatiment(new Point(1,2),2);
                construireBatiment(new Point(1,3),1);
                construireBatiment(new Point(2,2),3);
                construireBatiment(new Point(2,3),2);
                construireBatiment(new Point(3,3),4);
                break;
            case 24:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 4), this.getJ2());
                construireBatiment(new Point(1,1),2);
                construireBatiment(new Point(1,2),2);
                construireBatiment(new Point(1,3),1);
                construireBatiment(new Point(2,2),3);
                construireBatiment(new Point(2,3),2);
                construireBatiment(new Point(3,3),4);
                break;
            default:
                break;
        }
    }

    /**
     * Redirige vers des sous-fonction en fonction du numéro d'étape
     *
     * @param num_etape le numéro d'étape
     * @param position la case cliquée par la souris
     */
    public void joueEtape(int num_etape, Point position) {
        switch (num_etape) {
            case 2 -> verifieEtape(2, position, new Point(1, 1), new Point(3, 2), "ajouterJoueur");
            case 6 -> verifieEtape(6, position, new Point(1, 1), null, null);
            case 7 -> verifieEtape(7, position, new Point(1, 2), null, "ajouterJoueur");
            case 9 -> verifieEtape(9, position, new Point(2, 2), null, "ajouterJoueur");
            case 12 -> verifieEtape(12, position, new Point(1, 2), null, "ajouterJoueur");
            case 13 -> verifieEtape(13, position, new Point(2, 2), null, "ajouterJoueur");
            case 15 -> verifieEtape(15, position, new Point(3, 3), null, null);
            case 19 -> verifieEtape(19, position, new Point(3, 3), null, null);
            case 22 -> verifieEtape(22, position, new Point(1, 2), null, null);
            case 23 -> verifieEtape(23, position, new Point(2, 2), null, null);
            default -> {}
        }
    }

    /**
     * Modifie le plateau en fonction de la position des clics de la souris
     *
     * @param pos_clic la case cliquée par la souris
     */
    public void verifieEtape(int num_etape, Point pos_clic, Point pos_valide1, Point pos_valide2, String condition) {
        if (!pos_clic.equals(clic_prec) && (pos_clic.equals(pos_valide1) || pos_clic.equals(pos_valide2))) {
            clic_prec = pos_clic;
            if(condition.equals("ajouterJoueur")) {
                this.getPlateau().ajouterJoueur(pos_clic, this.getJ1());
            }
            clic_etapes[num_etape]++;
        }
    }

    /**
     * Construit un bâtiment de X étage(s) sur le plateau à une position donnée
     *
     * @param position la case cliquée par la souris
     * @param num_etage le nombre d'étage à construire
     */
    public void construireBatiment(Point position, int num_etage) {
        for (int i = 0; i < num_etage; i++) {
            this.getPlateau().ameliorerBatiment(position);
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
     * @param valeur la valeur pour une étape donnée
     */
    public void setClic_etape(int num_etape, int valeur) {
        this.clic_etapes[num_etape] = valeur;
    }

}
