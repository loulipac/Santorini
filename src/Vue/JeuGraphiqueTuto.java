package Vue;

import Modele.Jeu;

import java.awt.*;

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
        drawable.clearRect(
                0,
                0,
                getTailleCase() * PLATEAU_COLONNES,
                getTailleCase() * PLATEAU_LIGNES
        );

        super.paintComponent(g);

        switch(num_etape){
            case 2:
                // draw the rectangle here
                dessinerRectangle(drawable, new Point(2,3));
                break;
            default:
                break;
        }
    }

    public void dessinerRectangle(Graphics2D drawable, Point position) {
        drawable.setColor(Color.BLUE);
        drawable.setStroke(new BasicStroke(4));
        drawable.drawRect(position.y * getTailleCase(), position.x * getTailleCase(), getTailleCase(), getTailleCase());

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
