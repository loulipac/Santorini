package Modele;

import Vue.Observer;

import java.awt.*;

public class JeuTuto extends Jeu {
    int num_etape;

    public JeuTuto(Observer o) {
        super(o);
        this.num_etape = 0;
        chargerEtape(num_etape);
    }

    public int getNum_etape() {
        return num_etape;
    }

    public void setNum_etape(int etape) {
        this.num_etape = etape;
    }

    public void chargerEtape(int etape) {
        this.getPlateau().RAZ();
        switch (etape) {
            default:
            case 0:
                break;
            case 3:
                initialiserEtape1();
                break;
        }
    }

    public void initialiserEtape1() {
        this.getPlateau().ajouterJoueur(new Point(1, 3), this.getJ2());
        this.getPlateau().ajouterJoueur(new Point(3, 4), this.getJ2());
        construireBatiment(new Point(0, 0),2);
    }

    private void construireBatiment(Point position, int num_etage) {
        for (int i = 0; i < num_etage; i++) {
            this.getPlateau().ameliorerBatiment(position);
        }
    }
}
