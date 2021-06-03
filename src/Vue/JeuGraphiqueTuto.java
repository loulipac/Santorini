package Vue;

import Modele.JeuTuto;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Modele.Constante.*;

public class JeuGraphiqueTuto extends JeuGraphique {
    JeuTuto jeu_tuto;
    int num_etape;
    Color c_fond = creerCouleur(67, 204, 212, 0.5f);
    Color c_bordure = creerCouleur(80, 186, 245, 1f);
    Point clic_prec;
    Timer timer;
    static final int VITESSE_BASE = 1000;
    Observateur o;

    /**
     * Constructeur de JeuGraphiqueTuto.
     *
     * @param j         un JeuTuto
     * @param num_etape le numéro de l'étape
     */
    public JeuGraphiqueTuto(JeuTuto j, int num_etape, Observateur o) {
        super(j);
        this.jeu_tuto = j;
        this.num_etape = num_etape;
        this.o = o;
        timer = new Timer(VITESSE_BASE, e -> animationEtapes());
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.clearRect(0, 0, getTailleCase() * PLATEAU_COLONNES, getTailleCase() * PLATEAU_LIGNES);

        super.paintComponent(g);

        switch (num_etape) {
            case 2:
                // draw the rectangle here
                dessinerRectangle(drawable, new Point(1, 1), c_fond, c_bordure);
                dessinerRectangle(drawable, new Point(3, 2), c_fond, c_bordure);
                break;
            case 3,9:
                timerSet(true);
                break;
            case 5:
                dessinerRectangle(drawable, new Point(1, 1), c_fond, c_bordure);
                break;
            case 6:
                dessinerRectangle(drawable, new Point(1, 2), c_fond, c_bordure);
                break;
            case 8, 10:
                dessinerRectangle(drawable, new Point(2, 2), c_fond, c_bordure);
                break;
            case 12, 15:
                dessinerRectangle(drawable, new Point(3, 3), c_fond, c_bordure);
                break;
            default:
                break;
        }
    }

    public void animationEtapes() {
        switch (num_etape) {
            case 3 -> animationEtape3();
            case 9 -> animationEtape9();
            default -> timerSet(false);
        }
        repaint();
    }

    public void animationEtape3() {
        Point pos_pion1 = new Point(1, 3);
        Point pos_pion2 = new Point(4, 2);

        if (    jeu_tuto.getPlateau().estBatisseur(pos_pion1, jeu_tuto.getJ2())
            &&  jeu_tuto.getPlateau().estBatisseur(pos_pion2, jeu_tuto.getJ2())) {
            timerSet(false);
            o.miseAjour();

        } else if (jeu_tuto.getPlateau().estBatisseur(pos_pion1, jeu_tuto.getJ2())) {
            jeu_tuto.getPlateau().ajouterJoueur(pos_pion2, jeu_tuto.getJ2());

        } else {
            jeu_tuto.getPlateau().ajouterJoueur(pos_pion1, jeu_tuto.getJ2());
        }
    }

    public void animationEtape9() {
        Point pos_batiment = new Point(3, 3);
        Point nouv_pos_J2 = new Point(4, 3);

        if (    jeu_tuto.getPlateau().estBatisseur(nouv_pos_J2, jeu_tuto.getJ2())
            &&  jeu_tuto.getPlateau().estRDC(pos_batiment)) {
            timerSet(false);
            o.miseAjour();

        } else if (jeu_tuto.getPlateau().estBatisseur(nouv_pos_J2, jeu_tuto.getJ2())) {
            jeu_tuto.construireBatiment(pos_batiment, 1);

        } else {
            jeu_tuto.getPlateau().enleverJoueur(new Point(4, 2));
            jeu_tuto.getPlateau().ajouterJoueur(nouv_pos_J2, jeu_tuto.getJ2());
        }
    }

    public void timerSet(boolean statut) {
        if (statut) {
            timer.start();
        } else {
            timer.stop();
        }
    }


    public void dessinerRectangle(Graphics2D drawable, Point position, Color c_fond, Color c_bordure) {
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float((float) position.y * getTailleCase(), (float) position.x * getTailleCase(), getTailleCase(), getTailleCase(), 10, 10);

        drawable.setColor(c_fond);
        drawable.fillRoundRect(position.y * getTailleCase(), position.x * getTailleCase(), getTailleCase(), getTailleCase(), 10, 10);

        drawable.setStroke(new BasicStroke(4));
        drawable.setColor(c_bordure);
        drawable.draw(roundedRectangle);
    }

    public Color creerCouleur(int r, int g, int b, float alpha) {
        return new Color(
                (float) r / 255,
                (float) g / 255,
                (float) b / 255,
                alpha
        );
    }

    public void viderClic_prec() {
        clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
    }

    public void chargerEtape(int num_etape) {
        this.num_etape = num_etape;
        jeu_tuto.chargerEtape(num_etape);
        repaint();
    }

    public JeuTuto getJeu_tuto() {
        return jeu_tuto;
    }
}
