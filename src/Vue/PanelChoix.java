package Vue;

import Modele.ConfigurationPartie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static Modele.Constante.*;
import static Modele.Constante.CHEMIN_RESSOURCE;

public class PanelChoix extends JPanel {

    private static final String TITRE_PANEL = "Options de partie";
    private static final String TITRE_SECTION2 = "Qui commence ?";
    private static final String TITRE_SECTION3 = "Changer les couleurs";

    private final Color BLEU = new Color(70, 153, 206);
    private final Color ROUGE = new Color(224, 98, 98);

    private Bouton bRetour;
    private Bouton bCommencer;

    Font lilly_belle;
    Dimension taille_fenetre;
    ButtonGroup quiCommenceRadioGroupe;

    int ia_mode1, ia_mode2;

    boolean joueur1EstBleu = true;

    OptionPanel contenu;


    public PanelChoix(Dimension _taille_fenetre, ConfigurationPartie config) {
        this.ia_mode1 = config.getIaMode1();
        this.ia_mode2 = config.getIaMode2();
        taille_fenetre = _taille_fenetre;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.PLAIN, 20);
        initialiserPanel();
    }

    /**
     * Ajoute tous les composants au panel
     */
    public void initialiserPanel() {
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

        contenu = new OptionPanel(new Dimension((int) (taille_fenetre.width * 0.55), (int) (taille_panel)));

        double width_bouton = taille_fenetre.width * 0.25;
        JPanel bouton_bas = new JPanel();
        bouton_bas.setOpaque(false);
        bouton_bas.setMaximumSize(new Dimension(taille_fenetre.width, (int) (height * ratio_bouton_bas)));
        Dimension taille_bouton = new Dimension((int) (width_bouton), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE));
        bouton_bas.setBorder(null);
        bCommencer = new Bouton(CHEMIN_RESSOURCE + "/bouton/commencer_la_partie.png", CHEMIN_RESSOURCE + "/bouton/commencer_la_partie_hover.png",
                taille_bouton, this::actionBoutonCommencer);
        bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png",
                taille_bouton, this::actionBoutonRetourMenu);

        bouton_bas.add(bRetour);
        addSeparateur(bouton_bas, new Dimension((int) (taille_fenetre.width * 0.03), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE)));
        bouton_bas.add(bCommencer);

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

        CarreCouleur carreJ1;
        CarreCouleur carreJ2;

        private JPanel creerButtonRadioPanel() {
            JPanel _e = new JPanel();
            _e.setOpaque(false);
            return _e;
        }

        private JPanel creerTitre(String _t, int _fs, Dimension _s) {
            JPanel _jpan = new JPanel();
            JLabel _lab = new JLabel(_t);
            _lab.setFont(new Font("Lily Script One", Font.PLAIN, _fs));
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

        public OptionPanel(Dimension _taille_panel) {
            Dimension taille_total_panel = _taille_panel;
            setOpaque(false);
            setMinimumSize(taille_total_panel);
            setMaximumSize(taille_total_panel);
            setPreferredSize(taille_total_panel);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            double width_bouton = (taille_total_panel.width - taille_total_panel.width * 0.1) / 3;
            Dimension taille_bouton = new Dimension((int) (width_bouton), (int) (width_bouton * RATIO_BOUTON_CLASSIQUE_INVERSE));

            JPanel titrePanel = creerTitre(TITRE_PANEL, 45, new Dimension(taille_total_panel.width, (int) (taille_total_panel.height * 0.20)));


            Dimension taille_titre_panel = new Dimension(taille_total_panel.width, (int) (taille_total_panel.height * 0.15));

            JPanel groupeCommence = creerPanelRadio(new Dimension(taille_total_panel.width, (int) (taille_total_panel.height * 0.25)));
            JPanel quiCommenceTitre = creerTitre(TITRE_SECTION2, 30, taille_titre_panel);
            JPanel quiCommencePanelBouton = creerButtonRadioPanel();

            quiCommenceRadioGroupe = new ButtonGroup();
            BoutonRadio joueur1Commence = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/commence_j1",
                    taille_bouton, quiCommenceRadioGroupe);
            joueur1Commence.setSelected(true);
            BoutonRadio joueur2Commence = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/commence_j2",
                    taille_bouton, quiCommenceRadioGroupe);
            joueur1Commence.setActionCommand("0");
            joueur2Commence.setActionCommand("1");

            quiCommencePanelBouton.add(joueur1Commence);
            quiCommencePanelBouton.add(joueur2Commence);

            groupeCommence.add(quiCommenceTitre);
            groupeCommence.add(quiCommencePanelBouton);

            JPanel changerCouleursPanel = creerPanelRadio(new Dimension(taille_total_panel.width, (int) (taille_total_panel.height * 0.35)));
            JPanel changerCouleursTitre = creerTitre(TITRE_SECTION3, 30, taille_titre_panel);

            JPanel changerCouleursPanelBouton = new JPanel();
            changerCouleursPanelBouton.setOpaque(false);
            changerCouleursPanelBouton.setLayout(new GridBagLayout());
            Dimension taille_panel_bouton = new Dimension((int) (taille_total_panel.width * 0.5), (int) (taille_total_panel.height * 0.15));
            changerCouleursPanelBouton.setMaximumSize(taille_panel_bouton);
            changerCouleursPanelBouton.setPreferredSize(taille_panel_bouton);

            Bouton changer = new Bouton(CHEMIN_RESSOURCE + "/bouton/changer_couleurs.png", CHEMIN_RESSOURCE + "/bouton/changer_couleurs_hover.png",
                    taille_bouton, PanelChoix.this::actionChangementCouleur);

            changerCouleursPanelBouton.add(changer);

            Dimension tailleCarre = new Dimension((int) (taille_total_panel.width * 0.2), (int) (taille_total_panel.height * 0.10));
            carreJ1 = new CarreCouleur("Joueur 1", BLEU, tailleCarre);
            carreJ2 = new CarreCouleur("Joueur 2", ROUGE, tailleCarre);
            JPanel carres = new JPanel();
            carres.setOpaque(false);

            carres.add(carreJ1);
            carres.add(carreJ2);

            changerCouleursPanel.add(changerCouleursTitre);
            changerCouleursPanel.add(changerCouleursPanelBouton);
            changerCouleursPanel.add(carres);

            Dimension taille_separateur = new Dimension(taille_total_panel.width, (int) (taille_total_panel.height * 0.05));
            add(titrePanel);
            addSeparateur(this, taille_separateur);
            add(groupeCommence);
            addSeparateur(this, taille_separateur);
            add(changerCouleursPanel);
        }

        public class CarreCouleur extends JPanel {
            Dimension tailleCarre;

            Color couleur;

            public CarreCouleur(String numJoueur, Color couleur, Dimension tailleCarre) {
                this.tailleCarre = tailleCarre;
                this.couleur = couleur;
                setOpaque(false);
                setLayout(new GridBagLayout());
                JLabel texte = new JLabel(numJoueur);
                texte.setForeground(Color.white);
                texte.setFont(lilly_belle);
                setMaximumSize(tailleCarre);
                setPreferredSize(tailleCarre);
                add(texte);
            }

            public void changerCouleur(Color couleur) {
                this.couleur = couleur;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.clearRect(0, 0, tailleCarre.width, tailleCarre.height);
                g.setColor(couleur);
                g.fillRect(0, 0, tailleCarre.width, tailleCarre.height);
            }
        }

        public void switchCouleur() {
            if(!joueur1EstBleu) {
                carreJ1.changerCouleur(ROUGE);
                carreJ2.changerCouleur(BLEU);
            } else {
                carreJ1.changerCouleur(BLEU);
                carreJ2.changerCouleur(ROUGE);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Utile.dessinePanelBackground(g, getSize(), this);
        }
    }

    /**
     * Change l'affichage de la fenetre par le menu
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    public void actionChangementCouleur(ActionEvent e) {
        joueur1EstBleu = !joueur1EstBleu;
        contenu.switchCouleur();
    }

    private void addSeparateur(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }


    /**
     * Change l'affichage de la fenetre par le plateau
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonCommencer(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        ConfigurationPartie config = new ConfigurationPartie(ia_mode1, ia_mode2);

        config.setJoueur1Bleu(joueur1EstBleu);

        // vaut 0 ou 1
        int quiCommence = Integer.parseInt(quiCommenceRadioGroupe.getSelection().getActionCommand());

        config.setIndexJoueurCommence(quiCommence);

        f.setPanel(new PanelPlateau(getSize(), config));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessineBackground(g, getSize(), this);
    }
}
