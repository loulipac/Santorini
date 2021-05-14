package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ce panel permet de modifier les options du jeu
 * @TODO : Changer les images des boutons selectionnés
 */

class PanelOptions extends JPanel {
    private Bouton bRetour, bCommencer, bSon;

    public PanelOptions(int largeur, int hauteur) {
        initialiserPanel(largeur, hauteur);
    }

    public void initialiserPanel(int largeur, int hauteur) {
        setBackground(new Color(47, 112, 162));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        /* Label */
        JLabel titre = new JLabel(new ImageIcon("src/Ressources/logo/logo.png"));
        JLabel versus_texte = new JLabel("Mode de jeu");
        JLabel IA_texte = new JLabel("Difficulté de l'IA");
        JLabel IA2_texte = new JLabel("Difficulté de l'IA 2");
        JLabel son_texte = new JLabel("Son");

        titre.setAlignmentX(CENTER_ALIGNMENT);
        titre.setMaximumSize(new Dimension(415, 100));

        /* JPanel */
        JPanel contenu = new JPanel();
        JPanel versus_panel = new JPanel();
        JPanel IA_panel = new JPanel();
        JPanel IA2_panel = new JPanel();
        JPanel son_panel = new JPanel();
        JPanel boutons_principaux_panel = new JPanel();

        contenu.setAlignmentX(CENTER_ALIGNMENT);
        contenu.setMaximumSize(new Dimension(largeur*2/3,500));

        boutons_principaux_panel.setOpaque(false);
        boutons_principaux_panel.setMaximumSize(new Dimension(largeur, hauteur/10));
        boutons_principaux_panel.setBorder(null);

        /* Boutons*/
        ButtonGroup adversaires_boutons = new ButtonGroup();
        ButtonGroup boutons_IA = new ButtonGroup();
        ButtonGroup boutons_IA2 = new ButtonGroup();

        JRadioButton joueur_joueur = new BoutonRadio("src/Ressources/bouton/joueur_contre_joueur.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, adversaires_boutons);
        JRadioButton joueur_ia = new BoutonRadio("src/Ressources/bouton/joueur_contre_ia.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, adversaires_boutons);
        JRadioButton ia_ia = new BoutonRadio("src/Ressources/bouton/ia_contre_ia.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, adversaires_boutons);

        JRadioButton facile = new BoutonRadio("src/Ressources/bouton/facile.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA);
        JRadioButton normale = new BoutonRadio("src/Ressources/bouton/normale.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA);
        JRadioButton difficile = new BoutonRadio("src/Ressources/bouton/difficile.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA);

        JRadioButton facile2 = new BoutonRadio("src/Ressources/bouton/facile.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA2);
        JRadioButton normale2 = new BoutonRadio("src/Ressources/bouton/normale.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA2);
        JRadioButton difficile2 = new BoutonRadio("src/Ressources/bouton/difficile.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30, boutons_IA2);

        bSon = new Bouton("src/Ressources/bouton/desactive.png", "src/Ressources/bouton/active.png", largeur/6, largeur/30);
        bCommencer = new Bouton("src/Ressources/bouton/commencer_la_partie.png", "src/Ressources/bouton/commencer_la_partie.png", largeur/4, largeur/20);
        bRetour = new Bouton("src/Ressources/bouton/retour.png", "src/Ressources/bouton/retour.png", largeur/4, largeur/20);


        /* Evenements */
        bRetour.addActionListener(this::actionBoutonRetourMenu);

        joueur_joueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IA_texte.setVisible(false);
                IA2_texte.setVisible(false);
                IA_panel.setVisible(false);
                IA2_panel.setVisible(false);
            }
        });

        joueur_ia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IA_texte.setVisible(true);
                IA2_texte.setVisible(false);
                IA_panel.setVisible(true);
                IA2_panel.setVisible(false);
            }
        });

        ia_ia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IA_texte.setVisible(true);
                IA2_texte.setVisible(true);
                IA_panel.setVisible(true);
                IA2_panel.setVisible(true);

            }
        });

        /* Add dans les sous-panel */
        versus_panel.add(joueur_joueur);
        versus_panel.add(joueur_ia);
        versus_panel.add(ia_ia);

        IA_panel.add(facile);
        IA_panel.add(normale);
        IA_panel.add(difficile);

        IA2_panel.add(facile2);
        IA2_panel.add(normale2);
        IA2_panel.add(difficile2);

        son_panel.add(bSon);

        contenu.add(son_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(son_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        contenu.add(versus_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(versus_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        contenu.add(IA_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(IA_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        contenu.add(IA2_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(IA2_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));

        boutons_principaux_panel.add(bRetour);
        boutons_principaux_panel.add(bCommencer);

        /* Selection par défaut des boutons radio*/
        joueur_joueur.setSelected(true);
        facile.setSelected(true);
        facile2.setSelected(true);
        IA_panel.setVisible(false);
        IA2_panel.setVisible(false);
        IA_texte.setVisible(false);
        IA2_texte.setVisible(false);

        /* Adding */
        add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        add(titre);
        add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        add(contenu);
        add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));
        add(boutons_principaux_panel);
        add(Box.createRigidArea(new Dimension(largeur, hauteur/30)));

    }

    
    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }
}