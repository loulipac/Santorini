package Vue;

import Modele.Constante;
import Modele.Jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    int largeur, hauteur;
    Image colonne_rouge, colonne_bleu, arriere_plan;

    public PanelPlateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Ressources/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 40);
        initialiserPanel();
        lancerJeu(largeur, hauteur);

        colonne_rouge = JeuGraphique.readImage(Constante.CHEMIN_RESSOURCE + "/assets_recurrents/colonne_rouge.png");
        colonne_bleu = JeuGraphique.readImage(Constante.CHEMIN_RESSOURCE + "/assets_recurrents/colonne_bleu.png");
        arriere_plan = JeuGraphique.readImage(Constante.CHEMIN_RESSOURCE + "/artwork/fond_de_jeu.png");
    }

    /**
     * Ajoute tous les composants au panel
     */
    public void initialiserPanel() {
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        TopPanel tp = new TopPanel(0.25f);
        JGamePanel jgame = new JGamePanel(0.75f);
        add(tp);
        add(jgame);
        setVisible(true);
    }

    /*public void initialiserPanel1() {
        // Initialisation des règles du grid bag layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = .5;
        c.anchor = GridBagConstraints.CENTER;

        TopPanel tp = new TopPanel();
        JGamePanel jgame = new JGamePanel();
        //JButtonPanel panel_bouton = new JButtonPanel();

        // setting position on the grid bag layout
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0.25;
        c.gridy = 0;
        add(tp, c);
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0.75;
        c.gridy = 1;
        add(jgame, c);
    }*/

    public class JButtonPanel extends JPanel {
        public JButtonPanel() {
            setOpaque(false);
            setLayout(new GridLayout(1, 5));
            Dimension bouton_taille = new Dimension(
                    largeur / 5,
                    hauteur / 19
            );

            JPanel cote = new JPanel();
            cote.setOpaque(false);
            //cote.setLayout(new GridLayout(2, 1));

            Bouton bRetour = new Bouton(
                    Constante.CHEMIN_RESSOURCE + "/bouton/quitter.png",
                    Constante.CHEMIN_RESSOURCE + "/bouton/quitter_hover.png",
                    bouton_taille.width,
                    bouton_taille.height
            );
            bRetour.addActionListener(PanelPlateau.this::actionBoutonRetourMenu);

            Bouton bNouvelle = new Bouton(
                    Constante.CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png",
                    Constante.CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png",
                    bouton_taille.width,
                    bouton_taille.height
            );
            bNouvelle.addActionListener(PanelPlateau.this::actionBoutonNouvellePartie);


            Bouton bFin = new Bouton(
                    Constante.CHEMIN_RESSOURCE + "/bouton/fin_tour.png",
                    Constante.CHEMIN_RESSOURCE + "/bouton/fin_tour.png",
                    bouton_taille.width,
                    bouton_taille.height
            );
            bFin.addActionListener(PanelPlateau.this::actionBoutonFinDuTour);

            Bouton bAnnuler = new Bouton(
                    Constante.CHEMIN_RESSOURCE + "/bouton/annuler.png",
                    Constante.CHEMIN_RESSOURCE + "/bouton/annuler.png",
                    bouton_taille.width,
                    bouton_taille.height
            );
            bAnnuler.addActionListener(PanelPlateau.this::actionBoutonAnnuler);


            //add(bNouvelle);
            add(bAnnuler);
            add(bFin);
            //add(bRetour);
        }
    }

    public class JGamePanel extends JPanel {
        int taille_margin;
        float taille_h;

        public JGamePanel(float _taille_h) {
            this.taille_h = _taille_h - 0.1f;

            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            setPreferredSize(new Dimension((int) (largeur), (int) (hauteur * taille_h)));

            JPanel parametres = new JPanel();

            Dimension size = new Dimension((int) (largeur * 0.2), (int) (hauteur * taille_h));
            parametres.setOpaque(false);
            parametres.setPreferredSize(size);
            parametres.setMaximumSize(size);

            //parametres.setBorder(new LineBorder(Color.GREEN));
            Bouton bParametres = new Bouton(
                    Constante.CHEMIN_RESSOURCE + "/bouton/parametres.png",
                    Constante.CHEMIN_RESSOURCE + "/bouton/parametres_hover.png",
                    hauteur / 19,
                    hauteur / 19
            );
            bParametres.addActionListener(PanelPlateau.this::actionBoutonParametres);
            parametres.add(bParametres);

            jeu = new Jeu(5, 5, PanelPlateau.this);
            jg = new JeuGraphique(jeu);
            jg.addMouseListener(new EcouteurDeSouris(jg));
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg));

            // Calcul de la taille de la grille selon la taille de la fenêtre

            //int taille_case_largeur = largeur / jeu.getPlateau().getColonnes();
            int taille_case = ((int) (hauteur * taille_h)) / jeu.getPlateau().getLignes();

            jg.setPreferredSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes()));
            jg.setMaximumSize(new Dimension(taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes()));

            int taille = largeur;

            // place de la grille
            taille -= taille_case * jeu.getPlateau().getColonnes();

            // place des menus
            taille -= largeur * 0.4;

            taille_margin = taille / 4;

            addMargin();
            add(parametres);
            addMargin();
            add(jg);
            addMargin();

            // placeholder pour de futurs boutons
            JPanel j_placeholder = new JPanel();
            j_placeholder.setOpaque(false);
            j_placeholder.setPreferredSize(size);
            j_placeholder.setMaximumSize(size);
            //j_placeholder.setBorder(new LineBorder(Color.GREEN));
            add(j_placeholder);
            addMargin();
        }

        private void addMargin() {
            JPanel j = new JPanel();
            j.setOpaque(false);
            //j.setBorder(new LineBorder(Color.BLUE));
            Dimension size = new Dimension(taille_margin, (int) (hauteur * taille_h));
            j.setPreferredSize(size);
            j.setMaximumSize(size);
            add(j);
        }
    }

    public class TopPanel extends JPanel {
        public TopPanel(float taille_h) {
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);

            Dimension size = new Dimension(largeur, (int) (hauteur * taille_h));
            setPreferredSize(size);
            setMaximumSize(size);
            setOpaque(false);

            JLabel logo = new JLabel(new ImageIcon(Constante.CHEMIN_RESSOURCE + "/logo/logo.png"));
            logo.setAlignmentX(CENTER_ALIGNMENT);
            add(logo);

            jt = new JLabel("C'est au tour du Joueur 1");
            jt.setAlignmentX(CENTER_ALIGNMENT);
            jt.setOpaque(false);
            jt.setFont(lilly_belle);
            jt.setForeground(Color.WHITE);
            add(jt);
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
        try {

            g2d.drawImage(
                    arriere_plan,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

            Image colonne = (jeu.getJoueur_en_cours() == Jeu.JOUEUR1 ? colonne_bleu : colonne_rouge);
//            float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre

            g2d.drawImage(
                    colonne,
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
        f.getPileCarte().show(f.panelPrincipal, "menu");
    }

    public void actionBoutonFinDuTour(ActionEvent e) {
        // TODO : Changement joueur
        System.out.println("NOT IMPLEMENTED");
    }

    public void actionBoutonAnnuler(ActionEvent e) {
        // TODO : Redémarrer le tour pour le joueur en cours
        System.out.println("NOT IMPLEMENTED");
    }

    public void actionBoutonNouvellePartie(ActionEvent e) {
        // TODO : Reset la grille et tout ce qui va avec
    }

    public void actionBoutonParametres(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "parametres");
    }

    @Override
    public void miseAjour() {
        String annonce_tour_joueur;
        if (jeu.estJeufini())
            annonce_tour_joueur = jeu.getJoueur_en_cours() == Jeu.JOUEUR1 ? "Joueur 1 gagne" : "Joueur 2 gagne";
        else {
            annonce_tour_joueur = jeu.getJoueur_en_cours() == Jeu.JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2";
        }
        jt.setText(annonce_tour_joueur);
        repaint();
    }
}
