package Vue;

import Utile.ConfigurationPartie;
import Utile.*;

import static Utile.Constante.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelOptions extends JPanel {
    private static final String TITRE_SECTION1 = "Mode de jeu";
    private static final String TITRE_SECTION2 = "Difficulté de l'IA";
    private static final String TITRE_SECTION3 = "Difficulté de l'IA 2";

    private final LecteurSon son_bouton;
    private JRadioButton joueur_joueur;
    private final Dimension taille_fenetre;

    private ButtonGroup adversaires_boutons;
    private ButtonGroup boutons_IA;
    private ButtonGroup boutons_IA_IA;


    public PanelOptions(Dimension _taille_fenetre) {
        this.taille_fenetre = _taille_fenetre;
        son_bouton = new LecteurSon("menu_click.wav");
        initialiserPanel();
    }

    /**
     * Ajoute tous les composants au panel
     */
    private void initialiserPanel() {
        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        double height = taille_fenetre.height;
        double ratio_marge = 0.03;
        double ratio_logo = 0.10;
        double ratio_bouton_bas = 0.15;
        double taille_panel = height - height * ratio_logo - height * ratio_bouton_bas - (height * ratio_marge) * 5;

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new GridBagLayout());
        ImageIcon logo_img = new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo_hd.png");
        double ratio_logo_img = (double) logo_img.getIconWidth() / logo_img.getIconHeight();
        double taille_logo = taille_fenetre.height * ratio_logo;
        ImageIcon logo_resize = new ImageIcon(logo_img.getImage().getScaledInstance((int) (taille_logo * ratio_logo_img), (int) (taille_logo), Image.SCALE_SMOOTH));
        JLabel logo = new JLabel(logo_resize);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        Dimension logoPanel_taille = new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * ratio_logo));
        logoPanel.setMaximumSize(logoPanel_taille);
        logoPanel.setPreferredSize(logoPanel_taille);
        logoPanel.add(logo);

        /* JPanel */

        OptionPanel contenu = new OptionPanel(new Dimension((int) (taille_fenetre.width * 0.55), (int) (taille_panel)));

        double width_bouton = taille_fenetre.width * 0.25;
        JPanel bouton_bas = new JPanel();
        bouton_bas.setOpaque(false);
        bouton_bas.setMaximumSize(new Dimension(taille_fenetre.width, (int) (height * ratio_bouton_bas)));
        Dimension taille_bouton = new Dimension((int) (width_bouton), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE));
        bouton_bas.setBorder(null);
        Bouton bCommencer = new Bouton(CHEMIN_RESSOURCE + "/bouton/commencer_la_partie.png", CHEMIN_RESSOURCE + "/bouton/commencer_la_partie_hover.png",
                taille_bouton, this::actionBoutonCommencer);
        Bouton bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png",
                taille_bouton, this::actionBoutonRetourMenu);

        bouton_bas.add(bRetour);
        addSeparateur(bouton_bas, new Dimension((int) (taille_fenetre.width * 0.03), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE)));
        bouton_bas.add(bCommencer);

        /* Selection par défaut des boutons radio*/
        joueur_joueur.setSelected(true);

        /* Adding */
        Dimension taille_marge = new Dimension(taille_fenetre.width, (int) (height * ratio_marge));

        addSeparateur(this, taille_marge);
        addSeparateur(this, taille_marge);
        add(logoPanel);
        addSeparateur(this, taille_marge);
        add(contenu);
        addSeparateur(this, taille_marge);
        add(bouton_bas);
        addSeparateur(this, taille_marge);

    }

    private class OptionPanel extends JPanel {

        public OptionPanel(Dimension _taille_panel) {
            setOpaque(false);
            setMinimumSize(_taille_panel);
            setMaximumSize(_taille_panel);
            setPreferredSize(_taille_panel);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            double height = _taille_panel.height;

            double ratio_marge = 0.10;
            double height_panel = height - (height * ratio_marge) * 4;
            double taille_restante = height_panel / 3;

            double width_bouton = (_taille_panel.width - _taille_panel.width * 0.1) / 3;
            double height_titre = taille_restante - width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE;

            Dimension taille_bouton = new Dimension((int) (width_bouton), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE));
            Dimension taille_titre = new Dimension(_taille_panel.width, (int) height_titre);
            Dimension taille_panel = new Dimension(_taille_panel.width, (int) (taille_restante));

            JPanel j_vs_j = creerPanelRadio(taille_panel);
            JPanel j_vs_ia = creerPanelRadio(taille_panel);
            JPanel ia_vs_ia = creerPanelRadio(taille_panel);


            JPanel versus_titre = creerTitre(TITRE_SECTION1, taille_titre);
            JPanel IA_titre = creerTitre(TITRE_SECTION2, taille_titre);
            JPanel IAvIA_titre = creerTitre(TITRE_SECTION3, taille_titre);

            JPanel versus_panel = creerButtonRadioPanel();
            JPanel IA_panel = creerButtonRadioPanel();
            JPanel IA_IA_panel = creerButtonRadioPanel();

            /* Boutons*/
            adversaires_boutons = new ButtonGroup();
            boutons_IA = creerDifficulteButton(IA_panel, taille_bouton);
            boutons_IA_IA = creerDifficulteButton(IA_IA_panel, taille_bouton);

            joueur_joueur = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/joueur_contre_joueur",
                    taille_bouton, adversaires_boutons);
            JRadioButton joueur_ia = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/joueur_contre_ia",
                    taille_bouton, adversaires_boutons);
            JRadioButton ia_ia = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/ia_contre_ia",
                    taille_bouton, adversaires_boutons);

            joueur_joueur.setActionCommand("0");
            joueur_ia.setActionCommand("1");
            ia_ia.setActionCommand("2");


            joueur_joueur.addActionListener(e -> {
                j_vs_ia.setVisible(false);
                ia_vs_ia.setVisible(false);
            });

            joueur_ia.addActionListener(e -> {
                j_vs_ia.setVisible(true);
                ia_vs_ia.setVisible(false);
                changeTexte(IA_titre, TITRE_SECTION2);
            });

            ia_ia.addActionListener(e -> {
                j_vs_ia.setVisible(true);
                ia_vs_ia.setVisible(true);
                changeTexte(IA_titre, TITRE_SECTION2 + " 1");
            });

            /* Add dans les sous-panel */
            versus_panel.add(joueur_joueur);
            versus_panel.add(joueur_ia);
            versus_panel.add(ia_ia);

            j_vs_j.add(versus_titre);
            j_vs_j.add(versus_panel);

            j_vs_ia.add(IA_titre);
            j_vs_ia.add(IA_panel);

            ia_vs_ia.add(IAvIA_titre);
            ia_vs_ia.add(IA_IA_panel);

            Dimension taille_separateur = new Dimension(_taille_panel.width, (int) (height * ratio_marge));
            addSeparateur(this, taille_separateur);
            add(j_vs_j);
            addSeparateur(this, taille_separateur);
            add(j_vs_ia);
            addSeparateur(this, taille_separateur);
            add(ia_vs_ia);
            addSeparateur(this, taille_separateur);

            j_vs_ia.setVisible(false);
            ia_vs_ia.setVisible(false);
        }

        private ButtonGroup creerDifficulteButton(JPanel parent, Dimension size) {
            ButtonGroup _group = new ButtonGroup();

            JRadioButton _facile = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/facile",
                    size, _group);
            JRadioButton _normale = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/normale",
                    size, _group);
            JRadioButton _difficile = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/difficile",
                    size, _group);

            _facile.setActionCommand(String.valueOf(IA_FACILE));
            _normale.setActionCommand(String.valueOf(IA_NORMAL));
            _difficile.setActionCommand(String.valueOf(IA_DIFFICILE));

            parent.add(_facile);
            parent.add(_normale);
            parent.add(_difficile);


            _facile.setSelected(true);

            return _group;
        }

        private JPanel creerButtonRadioPanel() {
            JPanel _e = new JPanel();
            _e.setOpaque(false);
            return _e;
        }

        private JPanel creerTitre(String _t, Dimension _s) {
            JPanel _jpan = new JPanel();
            JLabel _lab = new JLabel(_t);
            _lab.setFont(new Font("Lily Script One", Font.PLAIN, 30));
            _lab.setForeground(new Color(103, 69, 42));
            _jpan.setLayout(new GridBagLayout());
            _jpan.setOpaque(false);
            _jpan.add(_lab);
            _jpan.setPreferredSize(_s);
            _jpan.setMaximumSize(_s);
            return _jpan;
        }

        private JPanel creerPanelRadio(Dimension _t) {
            JPanel _j = new JPanel();
            _j.setOpaque(false);
            _j.setLayout(new BoxLayout(_j, BoxLayout.Y_AXIS));
            _j.setPreferredSize(_t);
            _j.setMaximumSize(_t);
            _j.setMinimumSize(_t);
            return _j;
        }


        public void changeTexte(JPanel _jp, String texte) {
            for (Component jc : _jp.getComponents()) {
                JLabel label = (JLabel) jc;
                label.setText(texte);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Utile.dessinePanelBackground(g, getSize(), this);
        }
    }

    private void addSeparateur(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }

    /**
     * Change l'affichage de la fenetre par le menu.
     */
    private void actionBoutonRetourMenu(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    /**
     * Change l'affichage de la fenetre par le plateau.
     */
    private void actionBoutonCommencer(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        int ia_mode2 = 0;
        int ia_mode1 = 0;

        switch (Integer.parseInt(adversaires_boutons.getSelection().getActionCommand())) {
            case 2:
                ia_mode1 = Integer.parseInt(boutons_IA.getSelection().getActionCommand());
                ia_mode2 = Integer.parseInt(boutons_IA_IA.getSelection().getActionCommand());
                break;
            case 1:
                ia_mode1 = Integer.parseInt(boutons_IA.getSelection().getActionCommand());
                break;
            default:
                break;
        }

        f.setPanel(new PanelChoix(taille_fenetre, new ConfigurationPartie(ia_mode1, ia_mode2)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessineBackground(g, getSize(), this);
    }
}