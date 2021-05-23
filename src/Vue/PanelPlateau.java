package Vue;

import static Modele.Constante.*;
import Modele.Jeu;
import static Modele.Constante.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Classe générant la fenêtre de jeu.
 */
public class PanelPlateau extends JPanel implements Observer {

    private Jeu jeu;
    private JeuGraphique jg;
    Font lilly_belle;
    JLabel jt;
    int largeur, hauteur;
    Image colonne_rouge, colonne_bleu, arriere_plan;

    /**
     * Initialise la fenêtre de jeu et charge la police et les images en mémoire.
     *
     * @param largeur
     * @param hauteur
     * @see PanelPlateau#initialiserPanel()
     */
    public PanelPlateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE+"/font/LilyScriptOne.ttf")));

        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle = new Font("Lily Script One", Font.TRUETYPE_FONT, 40);
        initialiserPanel();

        colonne_rouge = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_rouge.png");
        colonne_bleu = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_bleu.png");
        arriere_plan = JeuGraphique.readImage(CHEMIN_RESSOURCE + "/artwork/fond_de_jeu.png");
    }

    /**
     * Ajoute tous les composants au panel.
     *
     * @see TopPanel
     * @see JGamePanel
     */
    public void initialiserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        TopPanel tp = new TopPanel(0.25f);
        JGamePanel jgame = new JGamePanel(0.75f);
        add(tp);
        add(jgame);
    }

    private class ActionEchap extends AbstractAction {
        public ActionEchap() {
            super();
            putValue(SHORT_DESCRIPTION, "Afficher les paramètres");
            putValue(MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(PanelPlateau.this);
            f.getPileCarte().show(f.panelPrincipal, "parametres");
        }
    }

    /**
     * Crée un JPanel modifié qui génère deux zones de boutons de 20% de la taille de la fenêtre.
     * Génère la grille de jeu.
     *
     * @see JeuGraphique
     * @see Jeu
     */
    public class JGamePanel extends JPanel {
        int taille_margin;
        float taille_h;

        /**
         * Constructeur pour JGamePanel. Rajoute des components au JPanel.
         *
         * @param _taille_h
         */
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
                    CHEMIN_RESSOURCE + "/bouton/parametres.png",
                    CHEMIN_RESSOURCE + "/bouton/parametres_hover.png",
                    hauteur / 19,
                    hauteur / 19
            );
            ActionEchap echap = new ActionEchap();
            PanelPlateau.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
            PanelPlateau.this.getActionMap().put("echap", echap);

            bParametres.addActionListener(echap);
            parametres.add(bParametres);

            jeu = new Jeu(5, 5, PanelPlateau.this);
            jg = new JeuGraphique(jeu);
            jg.addMouseListener(new EcouteurDeSouris(jg));
            jg.addMouseMotionListener(new EcouteurDeMouvementDeSouris(jeu, jg));

            JPanel histo_bouton = new JPanel();
            histo_bouton.setOpaque(false);
            histo_bouton.setPreferredSize(size);
            histo_bouton.setMaximumSize(size);

            Bouton histo_annuler = new Bouton(CHEMIN_RESSOURCE + "/bouton/arriere.png", CHEMIN_RESSOURCE + "/bouton/arriere_hover.png", hauteur / 19, hauteur / 19);
            Bouton histo_refaire = new Bouton(CHEMIN_RESSOURCE + "/bouton/avant.png", CHEMIN_RESSOURCE + "/bouton/avant_hover.png", hauteur / 19, hauteur / 19);
            histo_annuler.addActionListener(PanelPlateau.this::actionUndo);
            histo_refaire.addActionListener(PanelPlateau.this::actionRedo);
            histo_bouton.add(histo_annuler);
            histo_bouton.add(histo_refaire);

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
            add(histo_bouton);
            addMargin();
        }

        /**
         * Crée un JPanel servant de marge.
         */
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

    /**
     * Crée un JPanel modifié qui ajoute le logo et le texte designant quel joueur joue.
     */
    public class TopPanel extends JPanel {
        /**
         * Constructeur de TopPanel. Ajoute les élements et définis les valeurs des propriétés de chacuns.
         *
         * @param taille_h
         */
        public TopPanel(float taille_h) {
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);

            Dimension size = new Dimension(largeur, (int) (hauteur * taille_h));
            setPreferredSize(size);
            setMaximumSize(size);
            setOpaque(false);

            JLabel logo = new JLabel(new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo.png"));
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

    /**
     * Dessine l'image de fond et la bannière (colonne).
     *
     * @param g
     */
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

            Image colonne = (jeu.getJoueur_en_cours() == JOUEUR1 ? colonne_bleu : colonne_rouge);
            // float meme_ratio = (float) getWidth()/1232*191; //sert à garder le meme ratio hauteur/largeur au changement de largeur de la fenetre

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

    /**
     * Affiche le menu paramètre.
     *
     * @param e
     * @see PanelParametres
     */
    public void actionBoutonParametres(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.getPileCarte().show(f.panelPrincipal, "parametres");
    }

    public void actionUndo(ActionEvent e) {
        jeu.undo();
        jg.repaint();
    }

    public void actionRedo(ActionEvent e) {
         jeu.redo();
         jg.repaint();
    }

    /**
     * Modifie le texte qui affiche quel joueur doit jouer.
     */
    @Override
    public void miseAjour() {
        String annonce_tour_joueur;
        if (jeu.estJeufini())
            annonce_tour_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? "Joueur 1 gagne" : "Joueur 2 gagne";
        else {
            annonce_tour_joueur = jeu.getJoueur_en_cours() == JOUEUR1 ? "C'est au tour du Joueur 1" : "C'est au tour du Joueur 2";
        }
        jt.setText(annonce_tour_joueur);
        repaint();
    }
}
