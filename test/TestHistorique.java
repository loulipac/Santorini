import Modele.Jeu;
import Patterns.Observateur;

import Utile.ConfigurationPartie;
import Vue.PanelPlateau;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Utile.Constante.SAVES_PATH;

public class TestHistorique implements Observateur {
    private static Point[] moves = {
        new Point(1,2),
        new Point(2,1),
        new Point(2,3),
        new Point(3,2),
        new Point(1,2),
        new Point(1,3),
        new Point(0,3),
        new Point(2,3),
        new Point(2,4),
        new Point(1,4),
        new Point(2,1),
        new Point(2,2),
        new Point(1,2)
    };

    @Test
    public void testHistoriqueVide() {
        Jeu game = new Jeu(this);

        Assertions.assertFalse(game.getHistorique().peutRefaire());
        Assertions.assertFalse(game.getHistorique().peutAnnuler());
    }

    @Test
    public void testUndo() {
        Jeu game = new Jeu(this);

        for (Point move : moves) game.jouer(move);
        Assertions.assertFalse(game.equals(new Jeu(this)));
        for (Point move : moves) game.annuler();
        Assertions.assertTrue(game.equals(new Jeu(this)));
        Assertions.assertTrue(game.getHistorique().peutRefaire());
        Assertions.assertFalse(game.getHistorique().peutAnnuler());
    }

    @Test
    public void testRedo() {
        Jeu g1 = new Jeu(this);
        Jeu g2 = new Jeu(this);

        for (Point move : moves) {
            g1.jouer(move);
            g2.jouer(move);
        }

        for (Point move : moves) g1.annuler();
        Assertions.assertFalse(g1.equals(g2));
        for (Point move : moves) g1.refaire();
        Assertions.assertTrue(g1.equals(g2));
        Assertions.assertTrue(g1.getHistorique().equals(g2.getHistorique()));
        Assertions.assertTrue(g1.getHistorique().peutAnnuler());
        Assertions.assertFalse(g1.getHistorique().peutRefaire());
    }

    @Test
    public void testSaveLoad() {
        Jeu g1 = new Jeu(this);
        Jeu g2 = new Jeu(this);

        ConfigurationPartie config = new ConfigurationPartie(3, 2);
        config.setIndexJoueurCommence(1);
        g2.setConfigurationPartie(config);

        Assertions.assertFalse(g1.getConfigurationPartie().equals(g2.getConfigurationPartie()));

        for (Point move : moves) {
            g1.jouer(move);
        }
        g1.annuler();
        g1.annuler();
        g1.annuler();

        String filename = g1.sauvegarder();


        g2.charger(filename);

        Assertions.assertTrue(g1.equals(g2));
        Assertions.assertTrue(g1.getHistorique().equals(g2.getHistorique()));
    }

    @Override
    public void miseAjour() {

    }
}
