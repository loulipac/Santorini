package Vue;

import Modele.Constante;
import Modele.JeuTuto;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

import static Modele.Constante.*;

public class JeuGraphiqueTuto extends JeuGraphique {
    JeuTuto jeu_tuto;
    int num_etape;
    Color c_fond = creerCouleur(67, 204, 212, 0.5f);
    Color c_bordure = creerCouleur(80, 186, 245, 1f);
    int etapes[];
    Point clic_prec;

    /**
     * Constructeur de JeuGraphiqueTuto.
     *
     * @param j         un JeuTuto
     * @param num_etape le numéro de l'étape
     */
    public JeuGraphiqueTuto(JeuTuto j, int num_etape) {
        super(j);
        this.jeu_tuto = j;
        this.num_etape = num_etape;
        this.etapes = new int[Constante.TEXTE_ETAPES.length];
        clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
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
            case 5 :
                dessinerRectangle(drawable, new Point(1, 1), c_fond, c_bordure);
                break;
            case 6:
                dessinerRectangle(drawable, new Point(1, 2), c_fond, c_bordure);
                break;
            default:
                break;
        }
    }

    public void dessinerRectangle(Graphics2D drawable, Point position, Color c_fond, Color c_bordure) {
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(position.y * getTailleCase(), position.x * getTailleCase(), getTailleCase(), getTailleCase(), 10, 10);

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

    public void joueEtape(int num_etape, Point position) {
        switch (num_etape) {
            case 2 -> joueEtape2(position);
            case 5 -> joueEtape5(position);
        }
    }

    public void joueEtape2(Point position) {
        if (!comparePoints(position, clic_prec) && comparePoints(position, new Point(1, 1))) {
            clic_prec = position;
            this.jeu_tuto.getPlateau().ajouterJoueur(position, this.jeu_tuto.getJ1());
            etapes[2]++;
        } else if (!comparePoints(position, clic_prec) && comparePoints(position, new Point(3, 2))) {
            clic_prec = position;
            this.jeu_tuto.getPlateau().ajouterJoueur(position, this.jeu_tuto.getJ1());
            etapes[2]++;
        }
    }

    public void joueEtape5(Point position) {
        if (comparePoints(position, new Point(1, 1))) {
            this.jeu_tuto.getPlateau().ajouterJoueur(position, this.jeu_tuto.getJ1());
            etapes[5]++;
        }
    }

    public void viderClic_prec() {
        clic_prec = new Point(PLATEAU_LIGNES, PLATEAU_COLONNES);
    }

    public void chargerEtape(int num_etape) {
        this.num_etape = num_etape;
        jeu_tuto.chargerEtape(num_etape);
        repaint();
    }

    public boolean comparePoints(Point p1, Point p2) {
        return p1.equals(p2);
    }

    public int[] getEtapes() {
        return etapes;
    }

    public void setEtapes(int[] etape) {
        this.etapes = etape;
    }

    public int getEtape(int num_etape) {
        return etapes[num_etape];
    }

    public void setEtape(int num_etape, int valeur) {
        this.etapes[num_etape] = valeur;
    }

    public JeuTuto getJeu_tuto() {
        return jeu_tuto;
    }

    public void setJeu_tuto(JeuTuto jeu_tuto) {
        this.jeu_tuto = jeu_tuto;
    }

    public int getNum_etape() {
        return num_etape;
    }

    public void setNum_etape(int num_etape) {
        this.num_etape = num_etape;
    }
}
