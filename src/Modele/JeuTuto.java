package Modele;

import Vue.Observer;

import java.awt.*;
import static Modele.Constante.*;

public class JeuTuto extends Jeu{
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
        switch (etape) {
            default:
            case 0:
                break;
            case 1 :
                initialiserEtape1();
                break;
        }
    }

    public void initialiserEtape1() {
        this.getPlateau().ajouterJoueur(new Point(1,1), this.getJ1());
        System.out.println(this.getPlateau());
    }
}
