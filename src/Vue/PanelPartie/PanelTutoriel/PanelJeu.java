package Vue.PanelPartie.PanelTutoriel;

import Listener.EcouteurDeSourisTuto;
import Modele.JeuTuto;
import Vue.Bouton;
import Vue.JeuGraphiqueTuto;

import javax.swing.*;
import java.awt.*;

import static Utile.Constante.*;
import static Utile.Constante.PLATEAU_LIGNES;

/**
 * JPanel personnalisé qui affiche la grille de jeu.
 */
public class PanelJeu extends JPanel {
    private final PanelGauche panel_gauche;

    /**
     * Constructeur pour PanelJeu. Rajoute des components au JPanel.
     */
    public PanelJeu(float _taille_h, PanelTutoriel panel_tutoriel) {
        float taille_h = _taille_h - 0.05f;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(panel_tutoriel.getTailleFenetre().width, (int) (panel_tutoriel.getTailleFenetre().height * taille_h)));

        panel_tutoriel.setJeu_tuto(new JeuTuto(panel_tutoriel));
        JeuTuto jeu_tuto = panel_tutoriel.getJeuTuto();

        panel_tutoriel.setJg(new JeuGraphiqueTuto(jeu_tuto, panel_tutoriel.getNumEtape(), panel_tutoriel));

        JeuGraphiqueTuto jg = panel_tutoriel.getJg();
        jg.addMouseListener(new EcouteurDeSourisTuto(panel_tutoriel));

        Dimension dimension_panel_gauche = new Dimension((int)(panel_tutoriel.getTailleFenetre().width * 0.22f), (int)(panel_tutoriel.getTailleFenetre().height * taille_h * 1.05f));
        panel_gauche = new PanelGauche(dimension_panel_gauche, panel_tutoriel);
        PanelTutoriel.definirTaille(panel_gauche, dimension_panel_gauche);

        // Calcul de la taille de la grille selon la taille de la fenêtre
        int taille_case = ((int) (panel_tutoriel.getTailleFenetre().height * (taille_h - 0.1f))) / PLATEAU_LIGNES;

        PanelTutoriel.definirTaille(jg, new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES));

        int bouton_height = (int) (dimension_panel_gauche.height * 0.1);
        Dimension size_bouton = new Dimension((int) ((bouton_height/1.5) * RATIO_BOUTON_CLASSIQUE), (int)(bouton_height/1.5));

        JPanel panel_parametres = new JPanel();
        panel_parametres.setOpaque(false);
        panel_parametres.setPreferredSize(new Dimension(dimension_panel_gauche.width, size_bouton.height));
        panel_parametres.setMaximumSize(new Dimension(dimension_panel_gauche.width, size_bouton.height));

        Bouton bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/quitter_partie.png",CHEMIN_RESSOURCE + "/bouton/quitter_partie_hover.png",size_bouton,panel_tutoriel::actionBoutonRetourMenu);
        panel_tutoriel.setSuivant(new Bouton(CHEMIN_RESSOURCE + "/bouton/suivant.png", CHEMIN_RESSOURCE + "/bouton/suivant_hover.png",size_bouton, panel_tutoriel::actionBoutonSuivant));
        panel_tutoriel.setPrecedent(new Bouton(CHEMIN_RESSOURCE + "/bouton/precedent.png", CHEMIN_RESSOURCE + "/bouton/precedent_hover.png", size_bouton, panel_tutoriel::actionBoutonPrecedent));
        Bouton suivant = panel_tutoriel.getSuivant();
        Bouton precedent = panel_tutoriel.getPrecedent();

        panel_parametres.add(bRetour);
        precedent.setEnabled(false);
        JPanel panel_bouton = new JPanel();
        panel_bouton.setOpaque(false);
        panel_bouton.add(precedent);
        panel_bouton.add(Box.createRigidArea(new Dimension(size_bouton.width/2, size_bouton.height/2)));
        panel_bouton.add(suivant);
        PanelTutoriel.definirTaille(panel_bouton, new Dimension(size_bouton.width*4, size_bouton.height));
        panel_bouton.setAlignmentX(CENTER_ALIGNMENT);

        JPanel jp_jg = new JPanel();
        jp_jg.add(jg);

        Dimension taille_pannel = new Dimension(taille_case * PLATEAU_COLONNES, taille_case * PLATEAU_LIGNES);

        jp_jg.add(panel_bouton);
        jp_jg.add(Box.createRigidArea(taille_pannel));
        PanelTutoriel.definirTaille(jp_jg, taille_pannel);
        jp_jg.setOpaque(false);

        add(panel_gauche, BorderLayout.WEST);
        add(jp_jg, BorderLayout.CENTER);
        add(panel_parametres, BorderLayout.EAST);

    }

    public PanelGauche getPanel_gauche() {
        return panel_gauche;
    }

}