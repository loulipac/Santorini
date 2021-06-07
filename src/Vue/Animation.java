package Vue;

import Vue.JeuGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animation implements ActionListener {
    final double lenteur = 6f;
    JeuGraphique jg;
    Timer t;
    int pasX, pasY;
    int compteur;
    Point[] pos;

    public Animation(Point[] pos, int taille_case, JeuGraphique jg) {
        this.pos = pos;
        this.pos[0].x *= taille_case;
        this.pos[0].y *= taille_case;
        pasX = (pos[1].x * taille_case) - pos[0].x;
        pasX = (int) Math.round(pasX / lenteur);
        pasY = (pos[1].y * taille_case) - pos[0].y;
        pasY = (int) Math.round(pasY / lenteur);
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
        if (compteur == lenteur) {
            t.stop();
            jg.resetBatisseur();
        }
    }
}
