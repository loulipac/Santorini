import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Modele.Plateau;
import static Modele.Constante.*;

import java.awt.*;

public class TestPlateau {

    @Test
    public void testPlateauVide() {
        Plateau plateau = new Plateau();
        for (int i = 0; i < PLATEAU_LIGNES; i++) {
        for (int j = 0; j < PLATEAU_COLONNES; j++) {
                Point p = new Point(i, j);
                Assertions.assertTrue(plateau.estVide(p), "La case (" + i + "," + j +") devrait être vide! (=" + VIDE + ")");
                Assertions.assertFalse(plateau.estRDC(p), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(p), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(p), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(p), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutRDC() {
        Plateau plateau = new Plateau();

        Point p1 = new Point(0, 0);
        plateau.ajouterRDC(p1);

        Point p2 = new Point(3, 2);
        plateau.ajouterRDC(p2);

        for (int i = 0; i < PLATEAU_LIGNES; i++) {
            for (int j = 0; j < PLATEAU_COLONNES; j++) {
                Point p = new Point(i, j);
                if (p.equals(p1) || p.equals(p2)) {
                    Assertions.assertFalse(plateau.estVide(p), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + VIDE + ")");
                    Assertions.assertTrue(plateau.estRDC(p), "La case (" + i + "," + j +") devrait avoir un RDC!");
                } else {
                    Assertions.assertFalse(plateau.estRDC(p), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                    Assertions.assertTrue(plateau.estVide(p), "La case (" + i + "," + j +") devrait être vide! (=" + VIDE + ")");
                }
                Assertions.assertFalse(plateau.estEtage(p), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(p), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(p), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutEtage() {
        Plateau plateau = new Plateau();

        Point p1 = new Point(1, 4);
        plateau.ajouterEtage(p1);

        Point p2 = new Point(4, 0);
        plateau.ajouterEtage(p2);

        for (int i = 0; i < PLATEAU_LIGNES; i++) {
            for (int j = 0; j < PLATEAU_COLONNES; j++) {
                Point p = new Point(i, j);
                if (p.equals(p1) || p.equals(p2)) {
                    Assertions.assertFalse(plateau.estVide(p), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + VIDE + ")");
                    Assertions.assertTrue(plateau.estEtage(p), "La case (" + i + "," + j +") devrait avoir un étage!");
                } else {
                    Assertions.assertFalse(plateau.estEtage(p), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                    Assertions.assertTrue(plateau.estVide(p), "La case (" + i + "," + j +") devrait être vide! (=" + VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(p), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estToit(p), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(p), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutToit() {
        Plateau plateau = new Plateau();

        Point p1 = new Point(4, 4);
        plateau.ajouterToit(p1);

        Point p2 = new Point(2, 0);
        plateau.ajouterToit(p2);

        for (int i = 0; i < PLATEAU_LIGNES; i++) {
            for (int j = 0; j < PLATEAU_COLONNES; j++) {
                Point p = new Point(i, j);
                if (p.equals(p1) || p.equals(p2)) {
                    Assertions.assertFalse(plateau.estVide(p), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + VIDE + ")");
                    Assertions.assertTrue(plateau.estToit(p), "La case (" + i + "," + j +") devrait avoir un toit!");
                } else {
                    Assertions.assertFalse(plateau.estToit(p), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                    Assertions.assertTrue(plateau.estVide(p), "La case (" + i + "," + j +") devrait être vide! (=" + VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(p), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(p), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estCoupole(p), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutCoupole() {
        Plateau plateau = new Plateau();

        Point p1 = new Point(3, 1);
        plateau.ajouterCoupole(p1);

        Point p2 = new Point(0, 4);
        plateau.ajouterCoupole(p2);

        for (int i = 0; i < PLATEAU_LIGNES; i++) {
            for (int j = 0; j < PLATEAU_COLONNES; j++) {
                Point p = new Point(i, j);
                if (p.equals(p1) || p.equals(p2)) {
                    Assertions.assertFalse(plateau.estVide(p), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + VIDE + ")");
                    Assertions.assertTrue(plateau.estCoupole(p), "La case (" + i + "," + j +") devrait avoir une coupole!");
                } else {
                    Assertions.assertFalse(plateau.estCoupole(p), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
                    Assertions.assertTrue(plateau.estVide(p), "La case (" + i + "," + j +") devrait être vide! (=" + VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(p), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(p), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(p), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
            }
        }
    }
}
