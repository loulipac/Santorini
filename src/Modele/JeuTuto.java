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

    public int getNum_etape() {
        return num_etape;
    }

    public void setNum_etape(int etape) {
        this.num_etape = etape;
    }

    public void chargerEtape(int etape) {
        this.getPlateau().RAZ();
        //
        // Attention ! C'est la position AU DEBUT du tour
        //

        switch (etape) {
            case 3, 4, 5, 6:
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
            case 9, 10:
                this.getPlateau().ajouterJoueur(new Point(1, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 11, 12:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),1);
                break;
            case 13:
                this.getPlateau().ajouterJoueur(new Point(2, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(3, 2), this.getJ1());
                this.getPlateau().ajouterJoueur(new Point(4, 2), this.getJ2());
                this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
                construireBatiment(new Point(2,2),1);
                construireBatiment(new Point(3,3),2);
                break;
            default:
                break;
        }
    }


    public void joueEtape(int num_etape, Point position) {
        switch (num_etape) {
            case 2 -> joueEtape2(position);
            case 5 -> joueEtape5(position);
            case 6 -> joueEtape6(position);
            case 8 -> joueEtape8(position);
            case 10 -> joueEtape10(position);
            case 12 -> joueEtape12(position);
            default -> {
                break;
            }
        }
    }

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

    public void joueEtape5(Point position) {
        if (position.equals(new Point(1, 1))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[5]++;
        }
    }

    public void joueEtape6(Point position) {
        if (position.equals(new Point(1, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[6]++;
        }
    }

    public void joueEtape8(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[8]++;
        }
    }

    public void joueEtape10(Point position) {
        if (position.equals(new Point(2, 2))) {
            this.getPlateau().ajouterJoueur(position, this.getJ1());
            clic_etapes[10]++;
        }
    }

    public void joueEtape12(Point position) {
        if (position.equals(new Point(3, 3))) {
            construireBatiment(position, 1);
            clic_etapes[12]++;
        }
    }

    private void construireBatiment(Point position, int num_etage) {
        for (int i = 0; i < num_etage; i++) {
            this.getPlateau().ameliorerBatiment(position);
        }
    }

    public int[] getEtapes() {
        return clic_etapes;
    }

    public void setEtapes(int[] etape) {
        this.clic_etapes = etape;
    }

    public int getEtape(int num_etape) {
        return clic_etapes[num_etape];
    }

    public void setEtape(int num_etape, int valeur) {
        this.clic_etapes[num_etape] = valeur;
    }

}
