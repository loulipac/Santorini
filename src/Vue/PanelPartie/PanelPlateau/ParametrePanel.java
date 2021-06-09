package Vue.PanelPartie.PanelPlateau;

import Vue.PanelPartie.ActionEchap;
import Vue.PanelPartie.BackgroundPanel;
import Vue.Bouton;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static Utile.Constante.*;

public class ParametrePanel extends JPanel {

    PanelPlateau panel_plateau;
    Dimension taille_fenetre;

    public ParametrePanel(PanelPlateau panel_plateau) {
        this.panel_plateau = panel_plateau;
        taille_fenetre = panel_plateau.getTailleFenetre();
        initialiserComposant();
        setOpaque(false);
        setLayout(new GridBagLayout());
        setMaximumSize(taille_fenetre);
    }

    private void initialiserComposant() {
        Dimension taille_panel = new Dimension((int) (taille_fenetre.width * 0.55), taille_fenetre.height * 2 / 3);
        BackgroundPanel contenu = new BackgroundPanel(taille_panel);

        JLabel parametres_texte = new JLabel("Paramètres");
        parametres_texte.setForeground(new Color(82, 60, 43));
        parametres_texte.setFont(new Font(LILY_SCRIPT, Font.PLAIN, 40));
        parametres_texte.setAlignmentX(CENTER_ALIGNMENT);

        double ratio_marge = 0.03;
        double taille_restante = taille_panel.height - (taille_panel.height * ratio_marge) * 9;
        double height = taille_restante / 6;

        Dimension taille_bouton = new Dimension((int) (height * RATIO_BOUTON_CLASSIQUE), (int) (height));

        /* Boutons*/
        Bouton bReprendre = new Bouton(CHEMIN_RESSOURCE + "/bouton/reprendre.png", CHEMIN_RESSOURCE + "/bouton/reprendre_hover.png",
                taille_bouton.width,
                taille_bouton.height);

        Bouton bNouvellePartie = new Bouton(CHEMIN_RESSOURCE + "/bouton/nouvelle_partie.png", CHEMIN_RESSOURCE + "/bouton/nouvelle_partie_hover.png",
                taille_bouton.width,
                taille_bouton.height);

        JPanel charger_sauvegarder = new JPanel();
        charger_sauvegarder.setOpaque(false);
        charger_sauvegarder.setPreferredSize(taille_bouton);
        charger_sauvegarder.setMaximumSize(taille_bouton);
        charger_sauvegarder.setLayout(new GridLayout(1, 2));
        Bouton bSauvegarder = new Bouton(CHEMIN_RESSOURCE + "/bouton/sauvegarder.png", CHEMIN_RESSOURCE + "/bouton/sauvegarder_hover.png",
                taille_bouton.width / 2,
                taille_bouton.height);
        Bouton bCharger = new Bouton(CHEMIN_RESSOURCE + "/bouton/charger.png", CHEMIN_RESSOURCE + "/bouton/charger_hover.png",
                taille_bouton.width / 2,
                taille_bouton.height);

        charger_sauvegarder.add(bSauvegarder);
        charger_sauvegarder.add(bCharger);

        Bouton bQuitter = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter_partie.png", CHEMIN_RESSOURCE + "/bouton/quitter_partie_hover.png",
                taille_bouton.width,
                taille_bouton.height);


        /* Evenements */
        ActionEchap echap = new ActionEchap(panel_plateau);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "echap");
        getActionMap().put("echap", echap);

        bQuitter.addActionListener(panel_plateau::actionQuitter);
        bReprendre.addActionListener(echap);
        bNouvellePartie.addActionListener(panel_plateau::actionQuitter);
        bSauvegarder.addActionListener(panel_plateau::actionQuitter);
        bCharger.addActionListener(this::actionCharger);

        /* Adding */
        Dimension margin_taille = new Dimension(taille_panel.width, (int) (taille_panel.height * ratio_marge));
        addMargin(contenu, margin_taille);
        addMargin(contenu, margin_taille);
        contenu.add(parametres_texte);
        addMargin(contenu, margin_taille);
        addMargin(contenu, margin_taille);
        contenu.add(bReprendre);
        addMargin(contenu, margin_taille);
        contenu.add(bNouvellePartie);
        addMargin(contenu, margin_taille);
        contenu.add(charger_sauvegarder);
        addMargin(contenu, margin_taille);
        addMargin(contenu, taille_bouton);
        addMargin(contenu, margin_taille);
        contenu.add(bQuitter);
        addMargin(contenu, margin_taille);
        add(contenu);
    }

    private void addMargin(JPanel parent, Dimension taille) {
        parent.add(Box.createRigidArea(taille));
    }

    public void actionCharger(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(SAVES_PATH);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sauvegardes", "sav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            panel_plateau.getJeu().charger(chooser.getSelectedFile().getName());
            this.setVisible(false);
        }
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

}