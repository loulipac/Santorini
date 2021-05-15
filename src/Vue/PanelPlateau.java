package Vue;

import Modele.Constante;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class PanelPlateau extends JPanel {
    private Jeu jeu;
    private JeuGraphique jg;
    int largeur, hauteur;

    public PanelPlateau(int largeur, int hauteur) {
        this.largeur = getWidth();
        this.hauteur = getHeight();
        initialiserPanel();
    }

    public void initialiserPanel() {
        /* BoxLayout */
        //BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        //setLayout(boxlayout);

        //setAlignmentX(Component.CENTER_ALIGNMENT);


        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.NORTH;

        TopPanel tp = new TopPanel();
        this.jeu = new Jeu(5, 5);
        this.jg = new JeuGraphique(jeu);
        jg.addMouseListener(new EcouteurDeSouris(jg));
        JButton bRetour = new JButton("Retour au menu");
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.25;
        c.gridy = 0;
        add(tp, c);
        c.weighty = 0.65;
        c.gridy = 1;
        add(jg, c);
        c.weighty = 0.10;
        c.gridy = 2;
        add(bRetour, c);


        setBackground(new Color(47, 112, 162));


    }

    public class TopPanel extends JPanel {
        public TopPanel() {
            setBorder(new LineBorder(Color.red));
            setOpaque(false);
            setLayout(new GridLayout(1, 1));

            /*JLabel titre = new JLabel(new ImageIcon(Constante.CHEMIN_RESSOURCE + "/logo/logo.png"));
            titre.setAlignmentX(CENTER_ALIGNMENT);
            titre.setBorder(new LineBorder(Color.cyan));*/

            JLabel joueur = new JLabel("Au tour du joueur 1 !");
            add(joueur);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage img_bg = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/background_in_game.png"));
            g.drawImage(
                    img_bg,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

            BufferedImage img = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/banniere.png"));

//            float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre
            g.drawImage(
                    img,
                    0,
                    0,
                    getWidth(), (int) (getHeight() * 0.25),
                    this
            );

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    public void lancerJeu(int largeur, int hauteur) {
        /*this.jeu = new Jeu(5, 5);

        this.jg = new JeuGraphique(jeu);
        jg.setAlignmentX(CENTER_ALIGNMENT);
        jg.addMouseListener(new EcouteurDeSouris(jg));

        int min = Math.min(largeur, hauteur);
        jg.setMaximumSize(new Dimension(min/2,min/2));*/

        //add(jg);
    }

    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getCardLayout().show(f.mainPanel, "menu");
    }
}
