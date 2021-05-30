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
    final static String TITRE_SECTION1 = "Comment jouer ?";
    final static String TITRE_SECTION2 = "Déplacement";
    final static String TITRE_SECTION3 = "Construction";
    final static String CONTENU_SECTION1 = "Ce jeu se joue à 2 joueurs au tour par tour.\n" +
            "Le but du jeu est de monter le plus vite possible au sommet \n" +
            "d'une tour de 3 étages qu'il faut construire.\n" +
            "Chaque joueur dispose de 2 bâtisseur qu'il place sur le plateau au début de la partie.\n" +
            "A chaque tour, le joueur doit sélectionner un bâtisseur à déplacer\n" +
            "et construire un étage dans les cases adjacentes.";
    final static String CONTENU_SECTION2 = "Le bâtisseur choisi peut se déplacer sur un des emplacements proposés. \n" +
            "Le bâtisseur ne peut monter que d'un étage à la fois et ne peut pas se\n" +
            "déplacer sur un dôme.";
    final static String CONTENU_SECTION3 = "Un bâtisseur peut construire un étage sur les emplacements proposés.\n" +
            "Le bâtisseur peut poser un dôme en haut de la tour pour bloquer son adversaire. \n" +
            "On considère une tour de 3 étages et un dôme comme une \"Tour Complète\".";

    Font lilly_belle;
    Dimension taille_fenetre;

    public PanelRegles(Dimension _taille_fenetre) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 26);
        taille_fenetre = _taille_fenetre;
        initialiserPanel();
    }

    /**
     * Ajoute tous les composants au panel
     */
    public void initialiserPanel() {
        setBackground(new Color(47, 112, 162));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Label */
        JLabel logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(415, 100));

        /* Boutons */
        Bouton bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png", taille_fenetre.width / 4, taille_fenetre.height / 20, this::actionBoutonRetourMenu);

        /* Panel */
        ReglesPanel panel = new ReglesPanel();

        /* Adding */
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 20)));
        add(logo);
        add(panel);
        add(Box.createRigidArea(new Dimension(taille_fenetre.width, taille_fenetre.height / 40)));
        add(bRetour);
        setVisible(true);
    }

    private class LignePanel extends JPanel {
        public LignePanel(Dimension taille_panel, String image_path, JLabel titre, JTextArea texte) {
            setOpaque(false);
            setMaximumSize(taille_panel);

            /* Images */
            JLabel image = creerImage(image_path, taille_panel.width, taille_panel.height);

            /* Panels*/
            JPanel panel_image = new JPanel();
            JPanel panel_texte = new JPanel();

            panel_image.setOpaque(false);
            panel_image.setPreferredSize(new Dimension(taille_panel.width, taille_panel.height));

            panel_texte.setOpaque(false);
            panel_texte.setPreferredSize(new Dimension(taille_panel.width * 2, taille_panel.height));

            texte.setPreferredSize(new Dimension(taille_panel.width * 2, taille_panel.height));

            /* Adding */
            panel_image.add(image);
            panel_texte.add(titre);
            panel_texte.add(Box.createRigidArea(new Dimension(taille_panel.width * 2, 0)));
            panel_texte.add(texte);
            add(panel_image);
            add(panel_texte);
        }
    }

    private class ReglesPanel extends JPanel {
        public ReglesPanel() {
            setOpaque(false);

            JLabel titreCommentJouer = creerTitre(TITRE_SECTION1);
            JLabel titreDeplacement = creerTitre(TITRE_SECTION2);
            JLabel titreConstruction = creerTitre(TITRE_SECTION3);

            /* TextArea */
            JTextArea texteCommentJouer = creerContenu(CONTENU_SECTION1);
            JTextArea texteDeplacement = creerContenu(CONTENU_SECTION2);
            JTextArea texteConstruction = creerContenu(CONTENU_SECTION3);

            int largeur_panel = taille_fenetre.width * 5 / 8;
            int hauteur_panel = taille_fenetre.height * 5 / 8;
            int largeur_sous_panel = (int) Math.round(largeur_panel * 0.9);
            int hauteur_sous_panel = (int) Math.round(hauteur_panel / 3.5);
            Dimension taille_sous_panel = new Dimension(largeur_sous_panel / 3, hauteur_sous_panel);
            setMaximumSize(new Dimension(largeur_panel, hauteur_panel));

            LignePanel sous_panel_1 = new LignePanel(taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/comment_jouer.png", titreCommentJouer, texteCommentJouer);
            LignePanel sous_panel_2 = new LignePanel(taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/deplacement.png", titreDeplacement, texteDeplacement);
            LignePanel sous_panel_3 = new LignePanel(taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/construction.png", titreConstruction, texteConstruction);

            add(Box.createRigidArea(new Dimension(largeur_sous_panel, hauteur_sous_panel / 10)));
            add(sous_panel_1);
            add(sous_panel_2);
            add(sous_panel_3);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            try {
                BufferedImage bg_regles = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));
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
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    private JLabel creerTitre(String titre) {
        JLabel _e = new JLabel(titre);
        _e.setFont(lilly_belle);
        _e.setForeground(new Color(82, 60, 43));
        return _e;
    }

    private JTextArea creerContenu(String contenu) {
        JTextArea _e = new JTextArea(contenu);
        _e.setOpaque(false);
        _e.setEditable(false);
        _e.setFont(new Font("Arial", Font.PLAIN, 14));
        return _e;
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
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

}
