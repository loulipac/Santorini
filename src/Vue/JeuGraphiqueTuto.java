package Vue;

import Modele.Jeu;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Modele.Constante.PLATEAU_COLONNES;
import static Modele.Constante.PLATEAU_LIGNES;

public class JeuGraphiqueTuto extends JeuGraphique{
    int num_etape;

    /**
     * Constructeur de JeuGraphiqueTuto.
     *
     * @param j
     */
    public JeuGraphiqueTuto(Jeu j, int num_etape) {
        super(j);
        this.num_etape = num_etape;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.clearRect(0,0,getTailleCase() * PLATEAU_COLONNES,getTailleCase() * PLATEAU_LIGNES);

        super.paintComponent(g);

        switch(num_etape){
            case 2:
                // draw the rectangle here
                Color c_fond = creerCouleur(67, 204, 212, 0.5f);
                Color c_bordure = creerCouleur(80, 186, 245, 1f);
                dessinerRectangle(drawable, new Point(1,1), c_fond, c_bordure);
                dessinerRectangle(drawable, new Point(3,2), c_fond, c_bordure);
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
                (float) r/255,
                (float) g/255,
                (float) b/255,
                alpha
        );
    }

    public void chargerEtape(int num_etape) {
        this.num_etape = num_etape;
        repaint();
    }

    public int getNum_etape() {
        return num_etape;
    }

    public void setNum_etape(int num_etape) {
        this.num_etape = num_etape;
    }
}
