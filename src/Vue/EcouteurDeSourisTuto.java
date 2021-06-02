package Vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Modele.Constante.*;

public class EcouteurDeSourisTuto extends MouseAdapter {
    PanelTutoriel pt;
    int largeur_plateau;
    int hauteur_plateau;

    /**
     * Constructeur de EcouteurDeSourisTuto. Utilise un PanelTutoriel.
     *
     * @param pt
     */
    public EcouteurDeSourisTuto(PanelTutoriel pt ) {
        this.pt = pt;
    }


    /**
     * Ecouteur de souris personnalisé pour le tutoriel, permettant de récupérer les clics pour chacunes des étapes
     *
     * @param e evenement lorsqu'un clic intervient (contient la position du clic par exemple)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.largeur_plateau = pt.jg.getTailleCase() * PLATEAU_COLONNES;
        this.hauteur_plateau = pt.jg.getTailleCase() * PLATEAU_LIGNES;
        Point pos_souris = new Point(e.getY() / pt.jg.getTailleCase(),e.getX() / pt.jg.getTailleCase());
        switch (pt.num_etape) {
            case 2:
                pt.getJg().joueEtape2(pos_souris);
                if (pt.getJg().getEtape(2) >= 2) {
                    // On vide le tableau et la variable clic_prec
                    pt.getJg().setEtape(2,0);
                    pt.getJg().viderClic_prec();

                    // Passe à l'étape suivante
                    pt.setNum_etape(pt.getNum_etape()+1);
                    pt.changerEtape();
                }
                break;
            case 5:
                pt.getJg().joueEtape5(pos_souris);
                if (pt.getJg().getEtape(5) >= 1) {
                    // On vide le tableau et la variable clic_prec
                    pt.getJg().setEtape(5,0);
                    pt.getJg().viderClic_prec();

                    // Passe à l'étape suivante
                    pt.setNum_etape(pt.getNum_etape()+1);
                    pt.changerEtape();
                }
                break;
            default:
                break;
        }
        this.pt.jg.repaint();
    }
}
