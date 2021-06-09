package Vue.PanelPartie.PanelPlateau;

import Vue.PanelPartie.BackgroundPanel;
import Vue.Bouton;
import Vue.PanelPartie.VictoireBackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static Utile.Constante.CHEMIN_RESSOURCE;
import static Utile.Constante.RATIO_BOUTON_CLASSIQUE;

public class VictoirePanel extends JPanel {

    JPanel titre_victoire;
    JPanel nb_tours;
    PanelPlateau panel_plateau;
    Dimension taille_fenetre;

    public VictoirePanel(PanelPlateau panel_plateau) {
        this.panel_plateau = panel_plateau;
        taille_fenetre = panel_plateau.getTailleFenetre();
        initialiserComposant();
        setOpaque(false);
        setLayout(new GridBagLayout());
        setMaximumSize(taille_fenetre);
    }

    private void initialiserComposant() {
        Dimension real_taille_panel = new Dimension((int) (taille_fenetre.width * 0.35), (int) (taille_fenetre.height * 0.9));
        VictoireBackgroundPanel contenu = new VictoireBackgroundPanel(real_taille_panel);
        Dimension taille_panel = new Dimension((int) (real_taille_panel.width * 0.75), (int) (real_taille_panel.height * 0.8));

        double ratio_titre = 0.1;
        double ratio_marge = 0.03;
        double ratio_texte = 0.05;
        double taille_restante = taille_panel.height - taille_panel.height * (ratio_titre + (ratio_marge * 10) + (ratio_texte * 4));
        float height_bouton = (float) taille_restante / 4;

        Dimension taille_bouton = new Dimension((int) (height_bouton * RATIO_BOUTON_CLASSIQUE), (int) (height_bouton));
        Bouton bVisualiser = new Bouton(CHEMIN_RESSOURCE + "/bouton/visualiser.png", CHEMIN_RESSOURCE + "/bouton/visualiser.png",
                taille_bouton, this::actionVisualiser);
        Bouton bSauvegarder = new Bouton(CHEMIN_RESSOURCE + "/bouton/sauvegarder_partie.png", CHEMIN_RESSOURCE + "/bouton/sauvegarder_partie.png",
                taille_bouton, panel_plateau::actionBoutonSauvergarder);
        Bouton bNouvelle = new Bouton(CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png",
                taille_bouton, panel_plateau::actionBoutonNouvelle);
        Bouton bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter.png", CHEMIN_RESSOURCE + "/bouton/quitter.png",
                taille_bouton, panel_plateau::actionQuitter);

        Dimension dim_texte = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_texte));
        titre_victoire = creerTexte("Victoire du joueur %n", 35,
                new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_titre)), SwingConstants.CENTER);
        nb_tours = creerTexte("%n tours passés", 20, dim_texte, SwingConstants.CENTER);
        Dimension taille_marge = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_marge));

        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        contenu.add(titre_victoire);
        contenu.add(nb_tours);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        addMargin(contenu, taille_marge);
        contenu.add(bNouvelle);
        addMargin(contenu, taille_marge);
        contenu.add(bVisualiser);
        addMargin(contenu, taille_marge);
        contenu.add(bSauvegarder);
        addMargin(contenu, taille_marge);
        contenu.add(bQuitter);
        addMargin(contenu, taille_marge);
        add(contenu);
    }

    private void actionVisualiser(ActionEvent e) {
        this.setVisible(false);
    }

    public void changeTexte(JPanel _jp, String texte) {
        for (Component jc : _jp.getComponents()) {
            JLabel label = (JLabel) jc;
            label.setText(texte);
        }
    }

    private void addMargin(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }

    private JPanel creerTexte(String _t, int _fs, Dimension _s, int alignment) {
        JPanel _jpan = new JPanel();
        JLabel _lab = new JLabel(_t, alignment);
        _lab.setFont(new Font("Lily Script One", Font.PLAIN, _fs));
        _lab.setForeground(new Color(103, 69, 42));
        _lab.setPreferredSize(_s);
        _lab.setMaximumSize(_s);
        _jpan.setLayout(new BorderLayout());
        _jpan.setOpaque(false);
        _jpan.add(_lab, BorderLayout.CENTER);
        _jpan.setPreferredSize(_s);
        _jpan.setMaximumSize(_s);
        return _jpan;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color transparentColor = new Color(0, 0, 0, 0.4f);
        g2d.setColor(transparentColor);
        g2d.fillRect(0, 0, taille_fenetre.width, taille_fenetre.height);
        g2d.setComposite(AlphaComposite.SrcOver);
    }
    public JPanel getTitre_victoire() {
        return titre_victoire;
    }

    public JPanel getNb_tours() {
        return nb_tours;
    }
}