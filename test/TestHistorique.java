import Modele.Jeu;
import Vue.Observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TestHistorique implements Observer {
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
        Jeu game = new Jeu(5, 5, this);

        Assertions.assertFalse(game.getHistorique().canRedo());
        Assertions.assertFalse(game.getHistorique().canUndo());
    }

    @Test
    public void testUndo() {
        Jeu game = new Jeu(5, 5, this);

        for (Point move : moves) game.jouer(move);
        Assertions.assertFalse(game.equals(new Jeu(5, 5, this)));
        for (Point move : moves) game.undo();
        Assertions.assertTrue(game.equals(new Jeu(5, 5, this)));
        Assertions.assertTrue(game.getHistorique().canRedo());
        Assertions.assertFalse(game.getHistorique().canUndo());
    }

    @Test
    public void testRedo() {
        Jeu g1 = new Jeu(5, 5, this);
        Jeu g2 = new Jeu(5, 5, this);

        for (Point move : moves) {
            g1.jouer(move);
            g2.jouer(move);
        }

        for (int i = 0; i < 3; i++) g1.undo();
        Assertions.assertFalse(g1.equals(g2));
        for (int i = 0; i < 3; i++) g1.redo();
        Assertions.assertTrue(g1.equals(g2));
        Assertions.assertTrue(g1.getHistorique().equals(g2.getHistorique()));
        Assertions.assertTrue(g1.getHistorique().canUndo());
        Assertions.assertFalse(g1.getHistorique().canRedo());
    }

    @Test
    public void testSaveLoad() {
        Jeu g1 = new Jeu(5, 5, this);
        Jeu g2 = new Jeu(5, 5, this);

        for (Point move : moves) {
            g1.jouer(move);
        }
        g1.undo();
        g1.undo();
        g1.undo();

        String filename = g1.sauvegarder();

        g2.charger(filename);

        Assertions.assertTrue(g1.equals(g2));
        Assertions.assertTrue(g1.getHistorique().equals(g2.getHistorique()));
    }

    @Override
    public void miseAjour() { }
}
