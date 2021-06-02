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
                actionEtape(2,pos_souris,2);
                break;
            case 5:
                actionEtape(5,pos_souris,1);
                break;
            case 6:
                actionEtape(6,pos_souris,1);
                break;
            default:
                break;
        }
        this.pt.jg.repaint();
    }

    public void actionEtape(int num_etape, Point pos_souris, int nb_clic) {
        pt.getJg().joueEtape(num_etape,pos_souris);
        if (pt.getJg().getEtape(num_etape) >= nb_clic) {
            // On vide le tableau et la variable clic_prec
            pt.getJg().setEtape(num_etape,0);
            pt.getJg().viderClic_prec();

            // Passe à l'étape suivante
            pt.setNum_etape(pt.getNum_etape()+1);
            pt.changerEtape();
        }
    }
}