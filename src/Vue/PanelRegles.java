package Vue;

import Modele.Constante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelRegles extends JPanel {
    private Bouton bRetour;
    private JLabel titre;
    Font lilly_belle;

    public PanelRegles(int largeur, int hauteur) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Ressources/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT , 28);
        initialiserPanel(largeur, hauteur);
    }

    public void initialiserPanel(int largeur, int hauteur) {
        int largeur_panel = largeur / 2;
        int hauteur_panel = hauteur / 2;
        int largeur_sous_panel = largeur_panel / 3;
        int hauteur_sous_panel = hauteur_panel / 3;
        int espacement_entre_texte = hauteur_sous_panel / 25;

        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 10)));
        add(titre);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 20)));


        JLabel titreCommentJouer = new JLabel("Comment jouer");
        JLabel titreDeplacement = new JLabel("Déplacement");
        JLabel titreConstruction = new JLabel("Construction");

        titreCommentJouer.setFont(lilly_belle);
        titreDeplacement.setFont(lilly_belle);
        titreConstruction.setFont(lilly_belle);

        /* TextArea */
        JTextArea texteCommentJouer = new JTextArea("En partant du premier joueur, chaque joueur réalise son tour.\n" +
                "A votre tour, sélectionnez un de vos Ouvriers.\n" +
                "Vous devez le déplacer et ensuite construire avec l'Ouvrier choisi.");

        JTextArea texteDeplacement = new JTextArea("L'Ouvrier choisi peut se déplacer sur un \n" +
                "des (jusqu'à) 8 emplacements adjacents.\n" +
                "Un Ouvrier peut monter au maximum un étage, descendre d'autant d'étages qu'il le\n" +
                "souhaite et se déplacer sur un même niveau.\n" +
                "Un Ouvrier ne peut pas monter plus d'un étage.");

        JTextArea texteConstruction = new JTextArea("Vous pouvez construire sur n'importe quel niveau.\n" +
                "On considère une tour avec 3 Blocs et un Dôme\n" +
                "comme une \"Tour Complète”.");

        texteCommentJouer.setOpaque(false);
        texteDeplacement.setOpaque(false);
        texteConstruction.setOpaque(false);

        texteCommentJouer.setEditable(false);
        texteDeplacement.setEditable(false);
        texteConstruction.setEditable(false);

        /* Boutons */
        bRetour = new Bouton("src/Ressources/bouton/retour.png", "src/Ressources/bouton/retour.png", 415, 90);
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        /* Images */
        JLabel image_jouer = creerImage("src/Ressources/artwork/comment_jouer.png", hauteur_sous_panel, hauteur_sous_panel);
        JLabel image_deplacement = creerImage("src/Ressources/artwork/deplacement.png", hauteur_sous_panel, hauteur_sous_panel);
        JLabel image_construction = creerImage("src/Ressources/artwork/construction.png", hauteur_sous_panel, hauteur_sous_panel);

        /* Panel */
        ReglesPanel panel = new ReglesPanel();
        JPanel sous_panel_1 = new JPanel(new BorderLayout());
        JPanel sous_panel_2 = new JPanel(new BorderLayout());
        JPanel sous_panel_3 = new JPanel(new BorderLayout());
        JPanel panel_image_jouer = new JPanel();
        JPanel panel_texte_jouer = new JPanel();
        JPanel panel_texte_deplacement = new JPanel();
        JPanel panel_image_deplacement = new JPanel();
        JPanel panel_image_construction = new JPanel();
        JPanel panel_texte_construction = new JPanel();

        panel.setMaximumSize(new Dimension(largeur_panel, hauteur_panel));
        sous_panel_1.setMaximumSize(new Dimension(largeur_panel, hauteur_sous_panel));
        sous_panel_2.setMaximumSize(new Dimension(largeur_panel, hauteur_sous_panel));
        sous_panel_3.setMaximumSize(new Dimension(largeur_panel, hauteur_sous_panel));

        sous_panel_1.setOpaque(false);
        sous_panel_2.setOpaque(false);
        sous_panel_3.setOpaque(false);
        panel_image_jouer.setOpaque(false);
        panel_texte_jouer.setOpaque(false);
        panel_texte_deplacement.setOpaque(false);
        panel_image_deplacement.setOpaque(false);
        panel_image_construction.setOpaque(false);
        panel_texte_construction.setOpaque(false);

        panel_image_jouer.add(image_jouer);
        panel_image_jouer.setPreferredSize(new Dimension(largeur_sous_panel, hauteur_sous_panel));

        panel_texte_jouer.add(titreCommentJouer);
        panel_texte_jouer.add(Box.createRigidArea(new Dimension(largeur_sous_panel * 2, espacement_entre_texte)));
        panel_texte_jouer.add(texteCommentJouer);
        panel_texte_jouer.setPreferredSize(new Dimension(largeur_sous_panel * 2, hauteur_sous_panel));

        panel_texte_deplacement.add(titreDeplacement);
        panel_texte_deplacement.add(Box.createRigidArea(new Dimension(largeur_sous_panel * 2, espacement_entre_texte)));
        panel_texte_deplacement.add(texteDeplacement);
        panel_texte_deplacement.setPreferredSize(new Dimension(largeur_sous_panel * 2, hauteur_sous_panel));

        panel_image_deplacement.add(image_deplacement);
        panel_image_deplacement.setPreferredSize(new Dimension(largeur_sous_panel, hauteur_sous_panel));

        panel_image_construction.add(image_construction);
        panel_image_construction.setPreferredSize(new Dimension(largeur_sous_panel, hauteur_sous_panel));

        panel_texte_construction.add(titreConstruction);
        panel_texte_construction.add(Box.createRigidArea(new Dimension(largeur_sous_panel * 2, espacement_entre_texte)));
        panel_texte_construction.add(texteConstruction);
        panel_texte_construction.setPreferredSize(new Dimension(largeur_sous_panel * 2, hauteur_sous_panel));

        /* Adding */
        sous_panel_1.add(panel_image_jouer, BorderLayout.WEST);
        sous_panel_1.add(panel_texte_jouer, BorderLayout.EAST);
        sous_panel_2.add(panel_texte_deplacement, BorderLayout.WEST);
        sous_panel_2.add(panel_image_deplacement, BorderLayout.EAST);
        sous_panel_3.add(panel_image_construction, BorderLayout.WEST);
        sous_panel_3.add(panel_texte_construction, BorderLayout.EAST);

        panel.add(sous_panel_1);
        panel.add(sous_panel_2);
        panel.add(sous_panel_3);

        add(panel);
        add(Box.createRigidArea(new Dimension(largeur, hauteur / 30)));
        add(bRetour);
        setVisible(true);
    }

    private class ReglesPanel extends JPanel {
        public ReglesPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                BufferedImage bg_regles = ImageIO.read(new File("src/Ressources/artwork/bg_regles.png"));
                g.drawImage(
                        bg_regles,
                        0,
                        0,
                        getSize().width,
                        getSize().height,
                        this
                );
                System.out.println("draw.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur image de fond: " + e.getMessage());
            }
        }
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
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
        //Chargement de l"image de fond
        try {
            BufferedImage img_colonnes = ImageIO.read(new File("src/Ressources/artwork/columns.png"));
            g.drawImage(
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
