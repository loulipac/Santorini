package Vue;

import Modele.Bouton;
import Modele.BoutonRadio;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

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
//        Border blackline = BorderFactory.createLineBorder(Color.black);
//        boutons_principaux_panel.setBorder(blackline);
        boutons_principaux_panel.setBorder(null);

        /* Boutons*/
        ButtonGroup adversaires_boutons = new ButtonGroup();
        ButtonGroup boutons_IA = new ButtonGroup();
        ButtonGroup boutons_IA2 = new ButtonGroup();

        JRadioButton joueur_joueur = new BoutonRadio("src/Ressources/bouton_menu/joueur_contre_joueur.png", "src/Ressources/bouton_menu/joueur_contre_joueur.png", largeur/6, largeur/30, adversaires_boutons);
        JRadioButton joueur_ia = new BoutonRadio("src/Ressources/bouton_menu/joueur_contre_ia.png", "src/Ressources/bouton_menu/joueur_contre_ia.png", largeur/6, largeur/30, adversaires_boutons);
        JRadioButton ia_ia = new BoutonRadio("src/Ressources/bouton_menu/ia_contre_ia.png", "src/Ressources/bouton_menu/ia_contre_ia.png", largeur/6, largeur/30, adversaires_boutons);

        JRadioButton facile = new BoutonRadio("src/Ressources/bouton_menu/facile.png", "src/Ressources/bouton_menu/facile.png", largeur/6, largeur/30, boutons_IA);
        JRadioButton normale = new BoutonRadio("src/Ressources/bouton_menu/normale.png", "src/Ressources/bouton_menu/normale.png", largeur/6, largeur/30, boutons_IA);
        JRadioButton difficile = new BoutonRadio("src/Ressources/bouton_menu/difficile.png", "src/Ressources/bouton_menu/difficile.png", largeur/6, largeur/30, boutons_IA);

        JRadioButton facile2 = new BoutonRadio("src/Ressources/bouton_menu/facile.png", "src/Ressources/bouton_menu/facile.png", largeur/6, largeur/30, boutons_IA2);
        JRadioButton normale2 = new BoutonRadio("src/Ressources/bouton_menu/normale.png", "src/Ressources/bouton_menu/normale.png", largeur/6, largeur/30, boutons_IA2);
        JRadioButton difficile2 = new BoutonRadio("src/Ressources/bouton_menu/difficile.png", "src/Ressources/bouton_menu/difficile.png", largeur/6, largeur/30, boutons_IA2);

        bSon = new Bouton("src/Ressources/bouton_menu/desactive.png", "src/Ressources/bouton_menu/active.png", largeur/6, largeur/30);
        bCommencer = new Bouton("src/Ressources/bouton_menu/commencer_la_partie.png", "src/Ressources/bouton_menu/commencer_la_partie.png", largeur/3, largeur/15);
        bRetour = new Bouton("src/Ressources/bouton_menu/retour.png", "src/Ressources/bouton_menu/retour.png", largeur/3, largeur/15);


        /* Evenements */
        bRetour.addActionListener(this::actionBoutonRetourMenu);

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

        contenu.add(versus_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(versus_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 20)));
        contenu.add(IA_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(IA_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 20)));
        contenu.add(IA2_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(IA2_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 20)));
        contenu.add(son_texte);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 0)));
        contenu.add(son_panel);
        contenu.add(Box.createRigidArea(new Dimension(largeur, 20)));

        boutons_principaux_panel.add(bCommencer);
        boutons_principaux_panel.add(bRetour);

        /* Selection par défaut des boutons radio*/
        joueur_joueur.setSelected(true);
        facile.setSelected(true);
        facile2.setSelected(true);

        /* Adding */
        add(titre);
        add(Box.createRigidArea(new Dimension(largeur, 20)));
        add(contenu);
        add(Box.createRigidArea(new Dimension(largeur, 20)));
        add(boutons_principaux_panel);
        add(Box.createRigidArea(new Dimension(largeur, 20)));

    }

    
    public void actionBoutonRetourMenu(ActionEvent e) {
        Fenetre f2 = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f2.getCardLayout().show(f2.mainPanel, "menu");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
}