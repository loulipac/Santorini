package Vue;

import Utile.Utile;

import static Utile.Constante.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelRegles extends JPanel {
    private static final String TITRE_SECTION1 = "Comment jouer ?";
    private static final String TITRE_SECTION2 = "Déplacement";
    private static final String TITRE_SECTION3 = "Construction";
    private static final String CONTENU_SECTION1 = """
            Ce jeu se joue à 2 joueurs au tour par tour.
            Le but du jeu est de monter le plus vite possible au sommet d'une tour de 3 étages qu'il faut construire.
            Chaque joueur dispose de 2 bâtisseur qu'il place sur le plateau au début de la partie.
            A chaque tour, le joueur doit sélectionner un bâtisseur à déplacer et construire un étage dans les cases adjacentes.""";
    private static final String CONTENU_SECTION2 = """
            Le bâtisseur choisi peut se déplacer sur un des emplacements proposés.
            Le bâtisseur ne peut monter que d'un étage à la fois et ne peut pas se déplacer sur un dôme.""";
    private static final String CONTENU_SECTION3 = """
            Un bâtisseur peut construire un étage sur les emplacements proposés.
            Le bâtisseur peut poser un dôme en haut de la tour pour bloquer son adversaire.
            On considère une tour de 3 étages et un dôme comme une "Tour Complète".""";

    private final Font lilly_belle;
    private final Dimension taille_fenetre;

    public PanelRegles(Dimension _taille_fenetre) {
        lilly_belle = new Font(LILY_SCRIPT, Font.PLAIN, 26);
        taille_fenetre = _taille_fenetre;
        initialiserPanel();
    }

    /**
     * Ajoute tous les composants au panel
     */
    private void initialiserPanel() {
        setBackground(new Color(47, 112, 162));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Panel */
        Dimension taille_panel = new Dimension((int) (taille_fenetre.width * 0.6), (int) (taille_fenetre.height * 0.8));
        ReglesPanel panel = new ReglesPanel(taille_panel);

        /* Adding */
        addMargin(this, new Dimension(taille_fenetre.width, (int) (taille_fenetre.height * 0.10)));
        add(panel);
        setVisible(true);
    }

    private void addMargin(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }

    private class LignePanel extends JPanel {

        public LignePanel(Dimension taille_panel, String image_path, String titre, String texte) {
            setOpaque(false);
            setMaximumSize(taille_panel);
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            double ratio_image = 0.25;
            double ratio_marge = 0.05;
            double ratio_texte = 1 - ratio_image - ratio_marge * 2;

            JPanel panel_image = new JPanel();
            panel_image.setLayout(new GridBagLayout());
            panel_image.setOpaque(false);
            Dimension taille_panel_image = new Dimension((int) (taille_panel.width * ratio_image), taille_panel.height);
            panel_image.setPreferredSize(taille_panel_image);
            panel_image.setMaximumSize(taille_panel_image);
            panel_image.add(creerImage(image_path, taille_panel_image));

            JPanel contenu = new JPanel();
            contenu.setLayout(new BorderLayout());
            contenu.setOpaque(false);
            Dimension taille_panel_contenu = new Dimension((int) (taille_panel.width * ratio_texte), taille_panel.height);
            contenu.setMaximumSize(taille_panel_contenu);
            contenu.setPreferredSize(taille_panel_contenu);
            contenu.add(creerTitre(titre), BorderLayout.NORTH);
            contenu.add(creerContenu(texte), BorderLayout.CENTER);

            add(panel_image);
            addMargin(this, new Dimension((int) (taille_panel.width * ratio_marge), taille_panel.height));
            add(contenu);
            addMargin(this, new Dimension((int) (taille_panel.width * ratio_marge), taille_panel.height));
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
            _e.setFont(new Font(LORA, Font.PLAIN, 14));
            _e.setForeground(new Color(56, 56, 56));
            _e.setLineWrap(true);
            _e.setWrapStyleWord(true);
            return _e;
        }

        private JLabel creerImage(String _in, Dimension _t) {
            ImageIcon _imIcon = new ImageIcon(_in);
            Image _im = _imIcon.getImage();
            Dimension _nt = Utile.conserverRatio(new Dimension(_imIcon.getIconWidth(), _imIcon.getIconHeight()), _t);
            Image _nim = _im.getScaledInstance(_nt.width, _nt.height, java.awt.Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(_nim));
        }
    }

    private class ReglesPanel extends JPanel {

        public ReglesPanel(Dimension taille_panel) {

            setMaximumSize(taille_panel);
            setPreferredSize(taille_panel);
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            int largeur_sous_panel = (int) Math.round(taille_panel.width * 0.9);

            double ratio_marge = 0.02;
            double ratio_titre = 0.15;
            double ratio_bouton = 0.10;
            double ratio_taille = 1 - ratio_marge * 3 - ratio_titre - ratio_bouton;
            int hauteur_sous_panel = (int) (taille_panel.height * (ratio_taille / 3));


            JPanel panel_titre = new JPanel();
            JLabel titre = new JLabel("Règles");
            titre.setFont(new Font(LILY_SCRIPT, Font.PLAIN, 45));
            panel_titre.setLayout(new GridBagLayout());
            panel_titre.setOpaque(false);
            panel_titre.setMaximumSize(new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_titre)));
            panel_titre.setPreferredSize(new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_titre)));
            panel_titre.add(titre);

            Dimension taille_sous_panel = new Dimension(largeur_sous_panel, hauteur_sous_panel);

            LignePanel sous_panel_1 = new LignePanel(
                    taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/comment_jouer.png",
                    TITRE_SECTION1, CONTENU_SECTION1);
            LignePanel sous_panel_2 = new LignePanel(
                    taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/deplacement.png",
                    TITRE_SECTION2, CONTENU_SECTION2);
            LignePanel sous_panel_3 = new LignePanel(
                    taille_sous_panel, CHEMIN_RESSOURCE + "/artwork/construction.png",
                    TITRE_SECTION3, CONTENU_SECTION3);


            double height_bouton = taille_panel.height * ratio_bouton;
            Dimension taille_bouton = new Dimension(
                    (int) (height_bouton * RATIO_BOUTON_CLASSIQUE),
                    (int) (height_bouton)
            );
            Bouton bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png",
                    taille_bouton.width,
                    taille_bouton.height,
                    PanelRegles.this::actionBoutonRetourMenu);
            Bouton bTuto = new Bouton(CHEMIN_RESSOURCE + "/bouton/suivre_tutoriel.png", CHEMIN_RESSOURCE + "/bouton/suivre_tutoriel_hover.png",
                    taille_bouton.width,
                    taille_bouton.height,
                    PanelRegles.this::actionTutoriel);

            JPanel boutonsBas = new JPanel();
            boutonsBas.setOpaque(false);
            boutonsBas.setMaximumSize(new Dimension(taille_panel.width, taille_bouton.height));
            boutonsBas.setPreferredSize(new Dimension(taille_panel.width, taille_bouton.height));
            boutonsBas.add(bRetour);
            boutonsBas.add(bTuto);

            add(panel_titre);
            add(sous_panel_1);
            add(sous_panel_2);
            add(sous_panel_3);
            add(boutonsBas);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Utile.dessinePanelBackground(g, getSize(), null);
        }
    }

    /**
     * Retourne au menu principal.
     */
    private void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    /**
     * Affiche le tutoriel.
     */
    private void actionTutoriel(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("tutoriel");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utile.dessineBackground(g, getSize(), this);
    }
}
