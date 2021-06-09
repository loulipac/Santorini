package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe gérant les animations de déplacement.
 */
public class Animation implements ActionListener {
    private static final double LENTEUR = 6f;
    private final JeuGraphique jg;
    private final Timer t;
    private int pasX;
    private int pasY;
    private int compteur;
    private final Point[] pos;

    public Animation(Point[] pos, int taille_case, JeuGraphique jg) {
        this.pos = pos;
        this.pos[0].x *= taille_case;
        this.pos[0].y *= taille_case;
        pasX = (pos[1].x * taille_case) - pos[0].x;
        pasX = (int) Math.round(pasX / LENTEUR);
        pasY = (pos[1].y * taille_case) - pos[0].y;
        pasY = (int) Math.round(pasY / LENTEUR);
        compteur = 0;
        this.jg = jg;
        t = new Timer(10, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        compteur++;
        pos[0].x += pasX;
        pos[0].y += pasY;
        jg.repaint();
        if (compteur == LENTEUR) {
            t.stop();
            jg.resetBatisseur();
        }
    }
}
