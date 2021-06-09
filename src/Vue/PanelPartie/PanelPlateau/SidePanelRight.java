package Vue.PanelPartie.PanelPlateau;

import Utile.Utile;
import Vue.Bouton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static Utile.Constante.*;

/**
 * JPanel personnalisé qui gère les boutons de droites du plateau (IA, Historique)
 */
public class SidePanelRight extends JPanel {
    private Bouton acceleration;
    private ArrayList<Integer> niveauAcceleration;
    private int index_acceleration;
    private static final int TITRE_TAILLE = 30;
    private final Dimension size;
    private final Bouton histo_annuler;
    private final Bouton histo_refaire;
    private final PanelPlateau panel_plateau;

    public SidePanelRight(Dimension size, PanelPlateau panel_plateau) {

        this.panel_plateau = panel_plateau;

        this.size = size;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        int height = (int) (size.height * 0.1);

        Dimension size_pane = new Dimension(size.width, height);

        Dimension size_pane_button = new Dimension(size.width, (int) (height + (size.height * 0.05)));
        Dimension size_button = new Dimension((int) (height * RATIO_BOUTON_PETIT), height);

        JPanel panel_historique = creerTitre("Historique", size_pane);

        JPanel histo_bouton = new JPanel();
        histo_bouton.setOpaque(false);
        histo_bouton.setPreferredSize(size_pane_button);
        histo_bouton.setMaximumSize(size_pane_button);

        histo_annuler = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png",
                size_button, this::actionUndo);
        histo_refaire = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png",
                size_button, this::actionRedo);

        histo_bouton.add(histo_annuler);
        histo_bouton.add(histo_refaire);

        JPanel panel_ia = null;
        JPanel ia_bouton = null;
        JPanel panel_vit_ia = null;
        JPanel vit_ia_bouton = null;

        if (panel_plateau.getConfig().getIaMode1() != 0) {
            // titre IA
            panel_ia = creerTitre("IA", size_pane);

            // boutons
            // boutons
            ia_bouton = new JPanel();
            ia_bouton.setOpaque(false);
            ia_bouton.setPreferredSize(size_pane_button);
            ia_bouton.setMaximumSize(size_pane_button);

            panel_plateau.setOn_off_ia(new Bouton(CHEMIN_RESSOURCE + "/bouton/running.png", CHEMIN_RESSOURCE + "/bouton/running_hover.png", size_button, panel_plateau::switchOnOffIA));

            ia_bouton.add(panel_plateau.getOn_off_ia());

            // titre
            panel_vit_ia = creerTitre("Vitesse IA", size_pane);

            // boutons
            vit_ia_bouton = new JPanel();
            vit_ia_bouton.setOpaque(false);
            vit_ia_bouton.setPreferredSize(size_pane_button);
            vit_ia_bouton.setMaximumSize(size_pane_button);

            acceleration = new Bouton(CHEMIN_RESSOURCE + "/bouton/x1.png", CHEMIN_RESSOURCE + "/bouton/x1.png",
                    size_button);
            Bouton plus = new Bouton(CHEMIN_RESSOURCE + "/bouton/plus.png", CHEMIN_RESSOURCE + "/bouton/plus.png",
                    size_button,
                    this::accelerationIA);
            Bouton minus = new Bouton(CHEMIN_RESSOURCE + "/bouton/minus.png", CHEMIN_RESSOURCE + "/bouton/minus.png",
                    size_button,
                    this::ralentirIA);

            index_acceleration = 0;
            niveauAcceleration = new ArrayList<>();
            niveauAcceleration.add(1);
            niveauAcceleration.add(2);
            niveauAcceleration.add(4);
            niveauAcceleration.add(8);
            niveauAcceleration.add(16);
            niveauAcceleration.add(32);
            Font lilli_belle_tmp = new Font(LILY_SCRIPT, Font.PLAIN, 20);
            acceleration.setFont(lilli_belle_tmp);

            vit_ia_bouton.add(minus);
            vit_ia_bouton.add(acceleration);
            vit_ia_bouton.add(plus);
        }


        add(panel_historique);
        add(histo_bouton);
        if (panel_plateau.getConfig().getIaMode1() != 0) {
            add(panel_ia);
            add(ia_bouton);
            add(panel_vit_ia);
            add(vit_ia_bouton);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessinePanelBackground(g, size, null);

        histo_annuler.setEnabled(panel_plateau.getJeu().getHistorique().peutAnnuler());
        histo_refaire.setEnabled(panel_plateau.getJeu().getHistorique().peutRefaire());
    }

    private JPanel creerTitre(String _t, Dimension _s) {
        JPanel _jpan = new JPanel();
        JLabel _lab = new JLabel(_t);
        _lab.setFont(new Font(LILY_SCRIPT, Font.PLAIN, SidePanelRight.TITRE_TAILLE));
        _lab.setForeground(new Color(103, 69, 42));
        _jpan.setLayout(new GridBagLayout());
        _jpan.setOpaque(false);
        _jpan.add(_lab);
        _jpan.setPreferredSize(_s);
        _jpan.setMaximumSize(_s);
        return _jpan;
    }

    private void ralentirIA(ActionEvent e) {
        index_acceleration--;
        if (index_acceleration < 0) {
            index_acceleration = 0;
        }
        panel_plateau.getJeu().accelererIA(niveauAcceleration.get(index_acceleration));
        changeBoutonVitesse();
    }

    private void accelerationIA(ActionEvent e) {
        index_acceleration++;
        if (index_acceleration >= niveauAcceleration.size()) {
            index_acceleration = niveauAcceleration.size() - 1;
        }
        panel_plateau.getJeu().accelererIA(niveauAcceleration.get(index_acceleration));
        changeBoutonVitesse();
    }

    private void changeBoutonVitesse() {
        acceleration.changeImage(
                CHEMIN_RESSOURCE + "/bouton/x" + niveauAcceleration.get(index_acceleration) + ".png",
                CHEMIN_RESSOURCE + "/bouton/x" + niveauAcceleration.get(index_acceleration) + ".png"
        );
    }

    private void actionUndo(ActionEvent e) {
        panel_plateau.getJeu().annuler();
        panel_plateau.getJg().repaint();
    }

    private void actionRedo(ActionEvent e) {
        panel_plateau.getJeu().refaire();
        panel_plateau.getJg().repaint();
    }

}
