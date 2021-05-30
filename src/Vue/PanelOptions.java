package Vue;

import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

class PanelOptions extends JPanel {
    final static String TITRE_SECTION1 = "Mode de jeu";
    final static String TITRE_SECTION2 = "Difficulté de l'IA";
    final static String TITRE_SECTION3 = "Difficulté de l'IA 2";

    private Bouton bRetour, bCommencer;
    private LecteurSon son_bouton;
    Font lilly_belle;
    JRadioButton joueur_joueur, joueur_ia, ia_ia;
    Dimension taille_fenetre;

    ButtonGroup adversaires_boutons;
    ButtonGroup boutons_IA;
    ButtonGroup boutons_IA_IA ;


    public PanelOptions(Dimension _taille_fenetre) {
        son_bouton = new LecteurSon("menu_click.wav");
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 20);
        taille_fenetre = _taille_fenetre;
        initialiserPanel();
    }

    /**
     * Ajoute tous les composants au panel
     *
     */
    public void initialiserPanel() {
        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        JLabel logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(taille_fenetre.width / 3, taille_fenetre.height / 9));

        /* JPanel */

        OptionPanel contenu = new OptionPanel();

        JPanel bouton_bas = new JPanel();
        bouton_bas.setOpaque(false);
        bouton_bas.setMaximumSize(new Dimension(taille_fenetre.width, taille_fenetre.height / 10));
        bouton_bas.setBorder(null);
        bouton_bas.add(bRetour);
        bouton_bas.add(bCommencer);

        /* Selection par défaut des boutons radio*/
        joueur_joueur.setSelected(true);

        /* Adding */
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 15)));
        add(logo);
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
        add(contenu);
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
        add(bouton_bas);
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));

    }


    private class OptionPanel extends JPanel {
        private ButtonGroup creerDifficulteButton(JPanel parent) {
            ButtonGroup _group = new ButtonGroup();

            JRadioButton _facile = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/facile", taille_fenetre.width / 6, taille_fenetre.width / 30, _group);
            JRadioButton _normale = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/normale", taille_fenetre.width / 6, taille_fenetre.width / 30, _group);
            JRadioButton _difficile = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/difficile", taille_fenetre.width / 6, taille_fenetre.width / 30, _group);

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

        private JLabel creerLabel(String t) {
            JLabel _e = new JLabel(t);
            _e.setFont(lilly_belle);
            _e.setForeground(new Color(82, 60, 43));
            return _e;
        }

        public OptionPanel() {
            setOpaque(false);
            setMaximumSize(new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2 / 3));

            JLabel versus_texte = creerLabel(TITRE_SECTION1);
            JLabel IA_texte = creerLabel(TITRE_SECTION2);
            JLabel IA_IA_texte = creerLabel(TITRE_SECTION3);

            JPanel versus_panel = creerButtonRadioPanel();
            JPanel IA_panel = creerButtonRadioPanel();
            JPanel IA_IA_panel = creerButtonRadioPanel();

            /* Boutons*/
            adversaires_boutons = new ButtonGroup();
            boutons_IA = creerDifficulteButton(IA_panel);
            boutons_IA_IA = creerDifficulteButton(IA_IA_panel);

            joueur_joueur = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/joueur_contre_joueur", taille_fenetre.width / 6, taille_fenetre.width / 30, adversaires_boutons);
            joueur_ia = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/joueur_contre_ia", taille_fenetre.width / 6, taille_fenetre.width / 30, adversaires_boutons);
            ia_ia = new BoutonRadio(CHEMIN_RESSOURCE + "/bouton/joueur_contre_ia", taille_fenetre.width / 6, taille_fenetre.width / 30, adversaires_boutons);

            joueur_joueur.setActionCommand("0");
            joueur_ia.setActionCommand("1");
            ia_ia.setActionCommand("2");

            bCommencer = new Bouton(CHEMIN_RESSOURCE + "/bouton/commencer_la_partie.png", CHEMIN_RESSOURCE + "/bouton/commencer_la_partie_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);
            bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png", taille_fenetre.width / 4, taille_fenetre.width / 20);

            /* Evenements */
            bRetour.addActionListener(PanelOptions.this::actionBoutonRetourMenu);
            bCommencer.addActionListener(PanelOptions.this::actionBoutonCommencer);

            joueur_joueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IA_texte.setVisible(false);
                    IA_IA_texte.setVisible(false);
                    IA_panel.setVisible(false);
                    IA_IA_panel.setVisible(false);
                }
            });

            joueur_ia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IA_texte.setVisible(true);
                    IA_IA_texte.setVisible(false);
                    IA_panel.setVisible(true);
                    IA_IA_panel.setVisible(false);
                    IA_texte.setText(TITRE_SECTION2);
                }
            });

            ia_ia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IA_texte.setVisible(true);
                    IA_IA_texte.setVisible(true);
                    IA_panel.setVisible(true);
                    IA_IA_panel.setVisible(true);
                    IA_texte.setText(TITRE_SECTION2 + " 1");
                }
            });

            /* Add dans les sous-panel */
            versus_panel.add(joueur_joueur);
            versus_panel.add(joueur_ia);
            versus_panel.add(ia_ia);

            addSeparateur();
            add(versus_texte);
            add(versus_panel);
            addSeparateur();
            add(IA_texte);
            add(IA_panel);
            addSeparateur();
            add(IA_IA_texte);
            add(IA_IA_panel);

            IA_panel.setVisible(false);
            IA_texte.setVisible(false);
            IA_IA_panel.setVisible(false);
            IA_IA_texte.setVisible(false);
        }

        private void addSeparateur() {
            add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 30)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            try {
                BufferedImage bg_panel = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));
                g2d.drawImage(
                        bg_panel,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erreur image de fond: " + e.getMessage());
            }
        }
    }

    /**
     * Change l'affichage de la fenetre par le menu
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonRetourMenu(ActionEvent e) {
        son_bouton.joueSon(false);
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }


    /**
     * Change l'affichage de la fenetre par le plateau
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonCommencer(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);

        PanelPlateau plateau = null;
        int ia_mode2 = 0, ia_mode1 = 0;

        switch (Integer.valueOf(adversaires_boutons.getSelection().getActionCommand())) {
            case 2:
                ia_mode1 = Integer.valueOf(boutons_IA.getSelection().getActionCommand());
                ia_mode2 = Integer.valueOf(boutons_IA_IA.getSelection().getActionCommand());
                break;
            case 1:
                ia_mode1 = Integer.valueOf(boutons_IA.getSelection().getActionCommand());
                break;
            default:
                break;
        }

        plateau = new PanelPlateau(getSize(), ia_mode1, ia_mode2);
        f.setPanel(plateau);
    }

    /**
     * Joue un son lors du clique de la souris sur le bouton
     *
     * @param e Evenement declenché lors du clique de la souris sur le bouton
     */
    public void actionBoutonRadio(ActionEvent e) {
        son_bouton.joueSon(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Chargement de l"image de fond
        try {
            BufferedImage img = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/base.png"));
            Dimension img_dim = new Dimension(img.getWidth(), img.getHeight());
            Dimension taille_max = new Dimension((int) (taille_fenetre.width * 0.7), (int) (taille_fenetre.height * 0.7));
            Dimension taille_redimensionnee = conserverRatio(img_dim, taille_max);

            g2d.drawImage(
                    img,
                    getWidth() / 2 - ((int) (taille_redimensionnee.getWidth() / 2)),
                    getHeight() / 2 - ((int) (taille_redimensionnee.getHeight() / 2)),
                    taille_redimensionnee.width,
                    taille_redimensionnee.height,
                    this
            );

            BufferedImage img_colonnes = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));
            g2d.drawImage(
                    img_colonnes,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur image de fond: " + e.getMessage());
        }
    }
    
    private Dimension conserverRatio(Dimension imageSize, Dimension boundary) {

        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width * ratio),
                (int) (imageSize.height * ratio));
    }
}