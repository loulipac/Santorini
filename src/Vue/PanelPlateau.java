package Vue;

import Modele.Constante;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    GraphicsEnvironment ge;
    Font lilly_belle;
    JLabel jt;
    int largeur, hauteur;

    public PanelPlateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Ressources/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            //Handle exception
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT , 28);
        initialiserPanel();
        lancerJeu(largeur, hauteur);
    }

    public void initialiserPanel() {
        /* BoxLayout */
        //BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        //setLayout(boxlayout);

        //setAlignmentX(Component.CENTER_ALIGNMENT);

        /* Label */
        JLabel titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = .5;
        c.anchor = GridBagConstraints.CENTER;

        TopPanel tp = new TopPanel();
        this.jeu = new Jeu(5, 5, this);
        this.jg = new JeuGraphique(jeu);
        jg.addMouseListener(new EcouteurDeSouris(jg));

        JPanel jgame = new JPanel();
        Dimension taille_plateau = new Dimension(
                (int) (hauteur * 0.55),
                largeur
        );
        jgame.setBounds(0, 0, taille_plateau.width, taille_plateau.height);

        int largeur2 = taille_plateau.width / jeu.getPlateau().getColonnes();
        int hauteur2 = taille_plateau.height / jeu.getPlateau().getLignes();

        int taille_case = Math.min(largeur2, hauteur2);

        jgame.setPreferredSize(new Dimension(
                taille_case * jeu.getPlateau().getColonnes(),
                taille_case * jeu.getPlateau().getLignes())
        );
        jg.setPreferredSize(new Dimension(
                taille_case * jeu.getPlateau().getColonnes(),
                taille_case * jeu.getPlateau().getLignes())
        );
        jgame.setAlignmentX(Component.CENTER_ALIGNMENT);
        jgame.setBorder(new LineBorder(Color.MAGENTA));
        jgame.add(jg);

        JButton bRetour = new JButton("Retour au menu");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        //bRetour.setMaximumSize(new Dimension(300, 40));
        bRetour.addActionListener(this::actionBoutonRetourMenu);


        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0.25;
        c.gridy = 0;
        add(tp, c);
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0.65;
        c.gridy = 1;
        add(jgame, c);
        c.fill = GridBagConstraints.VERTICAL;
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
            /* Texte de suivi du déroulement de la partie */

            jt = new JLabel("C'est au tour du Joueur 1 (bleu)");
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setBorder(null);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);

            add(jt);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        largeur = getWidth();
        hauteur = getHeight();
        System.out.println("largeur : "+largeur+" Hauteur : "+hauteur);
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

    @Override
    public void miseAjour() {
        String annonce_tour_joueur;
        if (jeu.estJeufini())
            annonce_tour_joueur = jeu.getJoueur_en_cours() == Jeu.JOUEUR1 ? "Joueur 1 gagne (bleu)" : "Joueur 2 gagne (rouge)";
        else {
            annonce_tour_joueur = jeu.getJoueur_en_cours() == Jeu.JOUEUR1 ? "C'est au tour du Joueur 1 (bleu)" : "C'est au tour du Joueur 2 (rouge)";
        }
        jt.setText(annonce_tour_joueur);
    }
}
