import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Modele.Plateau;

public class TestPlateau {

    @Test
    public void testPlateauVide() {
        int lignes = 5;
        int colonnes = 5;

        Plateau plateau = new Plateau(lignes, colonnes);
        Assertions.assertEquals(lignes, plateau.getLignes(), "Le nombre de lignes du plateau devrait être égal à " + lignes);
        Assertions.assertEquals(colonnes, plateau.getColonnes(), "Le nombre de colonnes du plateau devrait être égal à " + colonnes);
        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                Assertions.assertTrue(plateau.estVide(i, j), "La case (" + i + "," + j +") devrait être vide! (=" + Plateau.VIDE + ")");
                Assertions.assertFalse(plateau.estRDC(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(i, j), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutRDC() {
        int lignes = 5;
        int colonnes = 5;

        Plateau plateau = new Plateau(lignes, colonnes);
        Assertions.assertEquals(lignes, plateau.getLignes(), "Le nombre de lignes du plateau devrait être égal à " + lignes);
        Assertions.assertEquals(colonnes, plateau.getColonnes(), "Le nombre de colonnes du plateau devrait être égal à " + colonnes);

        int ligne_ajout1 = 0;
        int colonne_ajout1 = 0;
        plateau.ajouterRDC(ligne_ajout1, colonne_ajout1);

        int ligne_ajout2 = 3;
        int colonne_ajout2 = 2;
        plateau.ajouterRDC(ligne_ajout2, colonne_ajout2);

        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                if ((i == ligne_ajout1 && j == colonne_ajout1) || (i == ligne_ajout2 && j == colonne_ajout2)) {
                    Assertions.assertFalse(plateau.estVide(i, j), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + Plateau.VIDE + ")");
                    Assertions.assertTrue(plateau.estRDC(i, j), "La case (" + i + "," + j +") devrait avoir un RDC!");
                } else {
                    Assertions.assertFalse(plateau.estRDC(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                    Assertions.assertTrue(plateau.estVide(i, j), "La case (" + i + "," + j +") devrait être vide! (=" + Plateau.VIDE + ")");
                }
                Assertions.assertFalse(plateau.estEtage(i, j), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutEtage() {
        int lignes = 5;
        int colonnes = 5;

        Plateau plateau = new Plateau(lignes, colonnes);
        Assertions.assertEquals(lignes, plateau.getLignes(), "Le nombre de lignes du plateau devrait être égal à " + lignes);
        Assertions.assertEquals(colonnes, plateau.getColonnes(), "Le nombre de colonnes du plateau devrait être égal à " + colonnes);

        int ligne_ajout1 = 1;
        int colonne_ajout1 = 4;
        plateau.ajouterEtage(ligne_ajout1, colonne_ajout1);

        int ligne_ajout2 = 4;
        int colonne_ajout2 = 0;
        plateau.ajouterEtage(ligne_ajout2, colonne_ajout2);

        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                if ((i == ligne_ajout1 && j == colonne_ajout1) || (i == ligne_ajout2 && j == colonne_ajout2)) {
                    Assertions.assertFalse(plateau.estVide(i, j), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + Plateau.VIDE + ")");
                    Assertions.assertTrue(plateau.estEtage(i, j), "La case (" + i + "," + j +") devrait avoir un étage!");
                } else {
                    Assertions.assertFalse(plateau.estEtage(i, j), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                    Assertions.assertTrue(plateau.estVide(i, j), "La case (" + i + "," + j +") devrait être vide! (=" + Plateau.VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estToit(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                Assertions.assertFalse(plateau.estCoupole(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutToit() {
        int lignes = 5;
        int colonnes = 5;

        Plateau plateau = new Plateau(lignes, colonnes);
        Assertions.assertEquals(lignes, plateau.getLignes(), "Le nombre de lignes du plateau devrait être égal à " + lignes);
        Assertions.assertEquals(colonnes, plateau.getColonnes(), "Le nombre de colonnes du plateau devrait être égal à " + colonnes);

        int ligne_ajout1 = 4;
        int colonne_ajout1 = 4;
        plateau.ajouterToit(ligne_ajout1, colonne_ajout1);

        int ligne_ajout2 = 2;
        int colonne_ajout2 = 0;
        plateau.ajouterToit(ligne_ajout2, colonne_ajout2);

        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                if ((i == ligne_ajout1 && j == colonne_ajout1) || (i == ligne_ajout2 && j == colonne_ajout2)) {
                    Assertions.assertFalse(plateau.estVide(i, j), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + Plateau.VIDE + ")");
                    Assertions.assertTrue(plateau.estToit(i, j), "La case (" + i + "," + j +") devrait avoir un toit!");
                } else {
                    Assertions.assertFalse(plateau.estToit(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
                    Assertions.assertTrue(plateau.estVide(i, j), "La case (" + i + "," + j +") devrait être vide! (=" + Plateau.VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(i, j), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estCoupole(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
            }
        }
    }

    @Test
    public void testAjoutCoupole() {
        int lignes = 5;
        int colonnes = 5;

        Plateau plateau = new Plateau(lignes, colonnes);
        Assertions.assertEquals(lignes, plateau.getLignes(), "Le nombre de lignes du plateau devrait être égal à " + lignes);
        Assertions.assertEquals(colonnes, plateau.getColonnes(), "Le nombre de colonnes du plateau devrait être égal à " + colonnes);

        int ligne_ajout1 = 3;
        int colonne_ajout1 = 1;
        plateau.ajouterCoupole(ligne_ajout1, colonne_ajout1);

        int ligne_ajout2 = 0;
        int colonne_ajout2 = 4;
        plateau.ajouterCoupole(ligne_ajout2, colonne_ajout2);

        for (int i = 0; i < plateau.getLignes(); i++) {
            for (int j = 0; j < plateau.getColonnes(); j++) {
                if ((i == ligne_ajout1 && j == colonne_ajout1) || (i == ligne_ajout2 && j == colonne_ajout2)) {
                    Assertions.assertFalse(plateau.estVide(i, j), "La case (" + i + "," + j +") ne devrait pas être vide! (=" + Plateau.VIDE + ")");
                    Assertions.assertTrue(plateau.estCoupole(i, j), "La case (" + i + "," + j +") devrait avoir une coupole!");
                } else {
                    Assertions.assertFalse(plateau.estCoupole(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de coupole!");
                    Assertions.assertTrue(plateau.estVide(i, j), "La case (" + i + "," + j +") devrait être vide! (=" + Plateau.VIDE + ")");
                }
                Assertions.assertFalse(plateau.estRDC(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de RDC!");
                Assertions.assertFalse(plateau.estEtage(i, j), "La case (" + i + "," + j +") ne devrait pas avoir d'étage!");
                Assertions.assertFalse(plateau.estToit(i, j), "La case (" + i + "," + j +") ne devrait pas avoir de toit!");
            }
        }
    }
}
