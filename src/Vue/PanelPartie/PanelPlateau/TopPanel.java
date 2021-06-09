package Vue.PanelPartie.PanelPlateau;

import Vue.PanelPartie.PanelTutoriel.PanelTutoriel;

import javax.swing.*;
import java.awt.*;

import static Utile.Constante.TEXTE_ETAPES;

/**
 * Crée un JPanel modifié qui ajoute le logo et le texte designant quel joueur joue.
 */
public class TopPanel extends JPanel {

    /**
     * Constructeur de TopPanel. Ajoute les élements et définis les valeurs des propriétés de chacuns.
     */
    public TopPanel(float taille_h, PanelTutoriel Panel_tutoriel) {
        setOpaque(false);

        setLayout(new GridBagLayout());

        Dimension taille = new Dimension(Panel_tutoriel.getTailleFenetre().width, (int) (Panel_tutoriel.getTailleFenetre().height * taille_h));
        setPreferredSize(taille);
        setMaximumSize(taille);
        setMinimumSize(taille);

        Panel_tutoriel.setJt(new JLabel("Tutoriel : Etape " + (Panel_tutoriel.getNumEtape() + 1) + "/" + TEXTE_ETAPES.length));
        JLabel jt = Panel_tutoriel.getJt();
        jt.setAlignmentX(CENTER_ALIGNMENT);
        jt.setAlignmentY(CENTER_ALIGNMENT);
        jt.setOpaque(false);
        jt.setFont(Panel_tutoriel.getLilyScriptOne());
        jt.setForeground(Color.WHITE);
        add(jt);
    }

    public TopPanel(float taille_h, PanelPlateau panel_plateau) {
        setOpaque(false);

        setLayout(new GridBagLayout());

        Dimension size = new Dimension(panel_plateau.getTailleFenetre().width, (int) (panel_plateau.getTailleFenetre().height * taille_h));
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        panel_plateau.setJt(new JLabel("C'est au tour du Joueur " + (panel_plateau.getConfig().getIndexJoueurCommence() + 1)));
        JLabel jt = panel_plateau.getJt();
        jt.setAlignmentX(CENTER_ALIGNMENT);
        jt.setAlignmentY(CENTER_ALIGNMENT);
        jt.setOpaque(false);
        jt.setFont(panel_plateau.getLilyScriptOne());
        jt.setForeground(Color.WHITE);
        add(jt);
    }
}