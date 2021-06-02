package Vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Modele.Constante.*;

public class EcouteurDeSourisTuto extends MouseAdapter {
    PanelTutoriel panel_tutoriel;
    int largeur_plateau;
    int hauteur_plateau;

    /**
     * Constructeur de EcouteurDeSourisTuto. Utilise un PanelTutoriel.
     *
     * @param panel_tutoriel
     */
    public EcouteurDeSourisTuto(PanelTutoriel panel_tutoriel ) {
        this.panel_tutoriel = panel_tutoriel;
    }


    /**
     * Utilise getJeu().jouer de JeuGraphique pour effectuer une action à la case calculé depuis la position de la souris.
     *
     * @param e evenement lorsqu'un clic intervient (contient la position du clic par exemple)
     * @see JeuGraphique#getJeu()
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.largeur_plateau = panel_tutoriel.jg.getTailleCase() * PLATEAU_COLONNES;
        this.hauteur_plateau = panel_tutoriel.jg.getTailleCase() * PLATEAU_LIGNES;
        if(panel_tutoriel.num_etape == 2) {
            panel_tutoriel.jg.etapeUneEffectuee(new Point(e.getY() / panel_tutoriel.jg.getTailleCase(),e.getX() / panel_tutoriel.jg.getTailleCase()));
        }
        this.panel_tutoriel.jg.repaint();
    }
}
