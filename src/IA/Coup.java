package IA;

import java.awt.*;

public class Coup {
        private final Point batisseur;
        private final Point deplacement;
        private final Point construction;

        public Coup(Point batisseur, Point deplacement, Point construction) {
            this.batisseur = batisseur;
            this.deplacement = deplacement;
            this.construction = construction;
        }



        @Override
        public String toString() {
            return "Coup{" +
                    "batisseur=" + batisseur +
                    ", deplacement=" + deplacement +
                    ", construction=" + construction +
                    '}';
        }

    public Point getBatisseur() {
        return batisseur;
    }

    public Point getDeplacement() {
        return deplacement;
    }

    public Point getConstruction() {
        return construction;
    }
}
