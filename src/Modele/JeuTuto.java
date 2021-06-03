package Modele;

import Patterns.Observateur;

import java.awt.*;
import static Modele.Constante.*;

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
        this.num_etape = etape;
        this.getPlateau().RAZ();
        //
        // Attention ! C'est la position AU DEBUT du tour
        //

        switch (etape) {
            case 3 :
                this.getPlateau().ajouterJoueur(new Point(1, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                break;
            case 4, 5, 6:
                this.getPlateau().ajouterJoueur(new Point(1, 1), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 7, 8:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                break;
            case 9:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                break;
            case 10:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 11, 12:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 13:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 3), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),2);
                break;
            case 14,15:
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
            case 16:
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
            case 2 -> joueEtape2(position);
            case 5 -> joueEtape5(position);
            case 6 -> joueEtape6(position);
            case 8 -> joueEtape8(position);
            case 10 -> joueEtape10(position);
            case 12 -> joueEtape12(position);
            case 15 -> joueEtape15(position);
            default -> {}
        }
    }

    /**
     * Modifie le plateau en fonction de la position des clics de la souris
     *
     * @param position la case cliquée par la souris
     */
    public void joueEtape2(Point position) {
        if (!position.equals(clic_prec) && position.equals(new Point(1, 1))) {
            clic_prec = position;
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[2]++;
        } else if (!position.equals(clic_prec) && position.equals(new Point(3, 2))) {
            clic_prec = position;
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[2]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape5(Point position) {
        if (position.equals(new Point(1, 1))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[5]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape6(Point position) {
        if (position.equals(new Point(1, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[6]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape8(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[8]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape10(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[10]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape12(Point position) {
        if (position.equals(new Point(3, 3))) {
            construireBatiment(position, 1);
            clic_etapes[12]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape15(Point position) {
        if (position.equals(new Point(3, 3))) {
            construireBatiment(position, 3);
            clic_etapes[15]++;
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
