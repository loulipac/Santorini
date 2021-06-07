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
            case 2 -> joueEtape2(position);
            case 6 -> joueEtape6(position);
            case 7 -> joueEtape7(position);
            case 9 -> joueEtape9(position);
            case 12 -> joueEtape12(position);
            case 13 -> joueEtape13(position);
            case 15 -> joueEtape15(position);
            case 19 -> joueEtape19(position);
            case 22 -> joueEtape22(position);
            case 23 -> joueEtape23(position);
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
    public void joueEtape6(Point position) {
        if (position.equals(new Point(1, 1))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[6]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape7(Point position) {
        if (position.equals(new Point(1, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[7]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape9(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[9]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape12(Point position) {
        if (position.equals(new Point(1, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[12]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape13(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[13]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape15(Point position) {
        if (position.equals(new Point(3, 3))) {
            construireBatiment(position, 1);
            clic_etapes[15]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape19(Point position) {
        if (position.equals(new Point(3, 3))) {
            construireBatiment(position, 3);
            clic_etapes[19]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape22(Point position) {
        if (position.equals(new Point(1, 2))) {
            clic_etapes[22]++;
        }
    }

    /**
     * @see JeuTuto#joueEtape2
     */
    public void joueEtape23(Point position) {
        if (position.equals(new Point(2, 2))) {
            clic_etapes[23]++;
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
