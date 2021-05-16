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
import java.io.IOException;

public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    int largeur, hauteur;

    public PanelPlateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Ressources/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 28);
        initialiserPanel();
        lancerJeu(largeur, hauteur);
    }

    /**
     * Ajoute tous les composants au panel
     *
     */
    public void initialiserPanel() {
        // Initialisation des règles du grid bag layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = .5;
        c.anchor = GridBagConstraints.CENTER;

        TopPanel tp = new TopPanel();
        JGamePanel jgame = new JGamePanel();

        JButton bRetour = new JButton("Retour au menu");
        bRetour.setAlignmentX(CENTER_ALIGNMENT);
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        // setting position on the grid bag layout
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
    }

    public class JGamePanel extends JPanel {
        public JGamePanel() {
            jeu = new Jeu(5, 5, PanelPlateau.this);
            jg = new JeuGraphique(jeu);
            jg.addMouseListener(new EcouteurDeSouris(jg));

            // Calcul de la taille de la grille selon la taille de la fenêtre
            int taille_case = Math.min(
                    largeur / jeu.getPlateau().getColonnes(),
                    ((int) (hauteur * 0.55)) / jeu.getPlateau().getLignes()
            );
            jg.setPreferredSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes()));
            setPreferredSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), 0));
            setOpaque(false);
            add(jg);
        }
    }

    public class TopPanel extends JPanel {
        public TopPanel() {
            setBorder(new LineBorder(Color.red));
            setOpaque(false);
            setLayout(new BorderLayout());

            /*JLabel titre = new JLabel(new ImageIcon(Constante.CHEMIN_RESSOURCE + "/logo/logo.png"));
            titre.setAlignmentX(CENTER_ALIGNMENT);
            titre.setBorder(new LineBorder(Color.cyan));
            add(titre, BorderLayout.CENTER);*/
            /* Texte de suivi du déroulement de la partie */

            jt = new JLabel("C'est au tour du Joueur 1 (bleu)");
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setBorder(null);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);
            add(jt, BorderLayout.NORTH);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        largeur = getWidth();
        hauteur = getHeight();
        System.out.println("largeur : " + largeur + " Hauteur : " + hauteur);
        try {
            BufferedImage img_bg = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/background_in_game.png"));
            g2d.drawImage(
                    img_bg,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

            BufferedImage img = ImageIO.read(new File(Constante.CHEMIN_RESSOURCE + "/artwork/banniere.png"));

//            float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre

            g2d.drawImage(
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
