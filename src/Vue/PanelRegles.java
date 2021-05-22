package Vue;

import static Modele.Constante.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelRegles extends JPanel {
    private Bouton bRetour;
    private JLabel logo;
    Font lilly_belle;

    public PanelRegles(int largeur, int hauteur) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE+"/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 26);
        initialiserPanel(largeur, hauteur);
    }

    /**
     * Ajoute tous les composants au panel
     *
     * @param largeur la largeur de la fenetre
     * @param hauteur la hauteur de la fenetre
     */
    public void initialiserPanel(int largeur, int hauteur) {

        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE+"/logo/logo.png"));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(415, 100));

        JLabel titreCommentJouer = new JLabel("Comment jouer ?");
        JLabel titreDeplacement = new JLabel("Déplacement");
        JLabel titreConstruction = new JLabel("Construction");

        titreCommentJouer.setFont(lilly_belle);
        titreDeplacement.setFont(lilly_belle);
        titreConstruction.setFont(lilly_belle);

        titreCommentJouer.setForeground(new Color(82, 60, 43));
        titreDeplacement.setForeground(new Color(82, 60, 43));
        titreConstruction.setForeground(new Color(82, 60, 43));


        /* TextArea */
        JTextArea texteCommentJouer = new JTextArea("Ce jeu se joue à 2 joueurs au tour par tour.\n" +
                "Le but du jeu est de monter le plus vite possible au sommet \n" +
                "d'une tour de 3 étages qu'il faut construire.\n" +
                "Chaque joueur dispose de 2 bâtisseur qu'il place sur le plateau au début de la partie.\n" +
                "A chaque tour, le joueur doit sélectionner un bâtisseur à déplacer\n" +
                "et construire un étage dans les cases adjacentes.");

        JTextArea texteDeplacement = new JTextArea("Le bâtisseur choisi peut se déplacer sur un des emplacements proposés. \n" +
                "Le bâtisseur ne peut monter que d'un étage à la fois et ne peut pas se\n" +
                "déplacer sur un dôme.");

        JTextArea texteConstruction = new JTextArea("Un bâtisseur peut construire un étage sur les emplacements proposés.\n" +
                "Le bâtisseur peut poser un dôme en haut de la tour pour bloquer son adversaire. \n" +
                "On considère une tour de 3 étages et un dôme comme une 'Tour Complète'.");

        texteCommentJouer.setOpaque(false);
        texteDeplacement.setOpaque(false);
        texteConstruction.setOpaque(false);

        texteCommentJouer.setEditable(false);
        texteDeplacement.setEditable(false);
        texteConstruction.setEditable(false);

        /* Boutons */
        bRetour = new Bouton(CHEMIN_RESSOURCE+"/bouton/retour.png", CHEMIN_RESSOURCE+"/bouton/retour_hover.png", largeur / 4, largeur / 20);
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        /* Panel */
        ReglesPanel panel = new ReglesPanel();
        int largeur_panel = largeur * 5 / 8;
        int hauteur_panel = hauteur * 5 / 8;
        int largeur_sous_panel = (int) Math.round(largeur_panel * 0.9);
        int hauteur_sous_panel = (int) Math.round(hauteur_panel /3.5);

        panel.setMaximumSize(new Dimension(largeur_panel, hauteur_panel));

        LignePanel sous_panel_1 = new LignePanel(largeur_sous_panel, hauteur_sous_panel, CHEMIN_RESSOURCE+"/artwork/comment_jouer.png", titreCommentJouer, texteCommentJouer);
        LignePanel sous_panel_2 = new LignePanel(largeur_sous_panel, hauteur_sous_panel, CHEMIN_RESSOURCE+"/artwork/deplacement.png", titreDeplacement, texteDeplacement);
        LignePanel sous_panel_3 = new LignePanel(largeur_sous_panel, hauteur_sous_panel, CHEMIN_RESSOURCE+"/artwork/construction.png", titreConstruction, texteConstruction);

        /* Adding */
        panel.add(Box.createRigidArea(new Dimension(largeur_sous_panel, hauteur_sous_panel / 10)));
        panel.add(sous_panel_1);
        panel.add(sous_panel_2);
        panel.add(sous_panel_3);

        add(Box.createRigidArea(new Dimension(largeur, hauteur / 20)));
        add(logo);
        add(panel);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 40)));
        add(bRetour);
        setVisible(true);
    }

    private class LignePanel extends JPanel {
        public LignePanel(int largeur_panel, int hauteur_panel, String image_path, JLabel titre, JTextArea texte) {

            int largeur_sous_panel = largeur_panel / 3;

            /* Textes */
            titre.setOpaque(false);

            texte.setPreferredSize(new Dimension(largeur_sous_panel * 2, hauteur_panel));
            texte.setFont(new Font("Arial", Font.PLAIN, 14));
            texte.setEditable(false);
            texte.setOpaque(false);

            /* Images */
            JLabel image = creerImage(image_path, largeur_sous_panel, hauteur_panel);

            /* Panels*/
            JPanel panel_image = new JPanel();
            JPanel panel_texte = new JPanel();

            setOpaque(false);
            setMaximumSize(new Dimension(largeur_panel, hauteur_panel));

            panel_image.setOpaque(false);
            panel_texte.setOpaque(false);

            /* Adding */
            panel_image.add(image);
            panel_image.setPreferredSize(new Dimension(largeur_sous_panel, hauteur_panel));

            panel_texte.add(titre);
            panel_texte.add(Box.createRigidArea(new Dimension(largeur_sous_panel * 2, 0)));
            panel_texte.add(texte);
            panel_texte.setPreferredSize(new Dimension(largeur_sous_panel * 2, hauteur_panel));

            add(panel_image);
            add(panel_texte);
        }
    }

    private class ReglesPanel extends JPanel {
        public ReglesPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            try {
                BufferedImage bg_regles = ImageIO.read(new File(CHEMIN_RESSOURCE+"/artwork/bg_regles.png"));
                g2d.drawImage(
                        bg_regles,
                        0,
                        0,
                        getSize().width,
                        getSize().height,
                        this
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur image de fond: " + e.getMessage());
            }
        }
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getPileCarte().show(f2.panelPrincipal, "menu");
    }

    public JLabel creerImage(String image, int largeur, int hauteur) {
        ImageIcon imgIcon = new ImageIcon(image);
        Image img = imgIcon.getImage();
        Image newimg = img.getScaledInstance(largeur, hauteur, java.awt.Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(newimg);
        return new JLabel(imgIcon);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Chargement de l"image de fond
        try {
            BufferedImage img_colonnes = ImageIO.read(new File(CHEMIN_RESSOURCE+"/artwork/columns.png"));
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
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

}
