package Listener;

import Vue.PanelTutoriel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EcouteurDeSourisTuto extends MouseAdapter {
    private final PanelTutoriel pt;

    /**
     * Constructeur de EcouteurDeSourisTuto. Utilise un PanelTutoriel.
     *
     * @param pt le PanelTutoriel
     */
    public EcouteurDeSourisTuto(PanelTutoriel pt) {
        this.pt = pt;
    }

    /**
     * Ecouteur de souris personnalisé pour le tutoriel, permettant de récupérer les clics pour chacunes des étapes
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point pos_souris = new Point(e.getY() / pt.getJg().getTailleCase(), e.getX() / pt.getJg().getTailleCase());

        switch (pt.getNum_etape()) {
            case 2 -> actionEtape(2, pos_souris, 2);
            case 6 -> actionEtape(6, pos_souris, 1);
            case 7 -> actionEtape(7, pos_souris, 1);
            case 9 -> actionEtape(9, pos_souris, 1);
            case 12 -> actionEtape(12, pos_souris, 1);
            case 13 -> actionEtape(13, pos_souris, 1);
            case 15 -> actionEtape(15, pos_souris, 1);
            case 19 -> actionEtape(19, pos_souris, 1);
            case 22 -> actionEtape(22, pos_souris, 1);
            case 23 -> actionEtape(23, pos_souris, 1);
            default -> { /* DO NOTHING */ }
        }
        this.pt.getJg().repaint();
    }

    /**
     * Compare le nombre de clics validés et le nombre de clics validés nécessaires pour chaque étape, change d'étape
     *
     * @param num_etape  le numéro d'étape
     * @param pos_souris la position de la souris
     * @param nb_clic    le nombre de clics validés nécessaire pour passer à l'étape suivante
     */
    private void actionEtape(int num_etape, Point pos_souris, int nb_clic) {
        pt.getJg().getJeu_tuto().joueEtape(num_etape, pos_souris);
        if (pt.getJg().getJeu_tuto().getClic_etape(num_etape) >= nb_clic) {

            // On vide le tableau et la variable clic_prec
            pt.getJg().getJeu_tuto().setClic_etape(num_etape, 0);
            pt.getJg().viderClic_prec();

            // Passe à l'étape suivante
            pt.setNum_etape(pt.getNum_etape() + 1);
            pt.changerEtape();
        }
    }

    /**
     * Evènement quand la souris sort de la grille de jeu, permet d'effacer les hovers sur la grille
     * (tel que le batisseur en transparence lors du placement).
     */
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        pt.getJg().setCase_sous_souris(null);
        pt.getJg().repaint();
    }


}
