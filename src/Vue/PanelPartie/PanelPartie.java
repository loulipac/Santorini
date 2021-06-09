package Vue.PanelPartie;

import Modele.Jeu;
import Patterns.Observateur;
import Vue.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite pour les panels Tutoriels et Plateau.
 */
public abstract class PanelPartie extends Panels implements Observateur {
    protected JLabel jt;
    protected Dimension taille_fenetre;
    protected Font lilyScriptOne;
    protected Jeu jeu;

    public JLabel getJt() {
        return jt;
    }

    protected PanelPartie(Dimension _taille_fenetre, Font _lilyScriptOne){
        taille_fenetre = _taille_fenetre;
        lilyScriptOne = _lilyScriptOne;
    }

    public void setJt(JLabel jt) {
        this.jt = jt;
    }

    public void setTaille_fenetre(Dimension taille_fenetre) {
        this.taille_fenetre = taille_fenetre;
    }

    public void setLilyScriptOne(Font lilyScriptOne) {
        this.lilyScriptOne = lilyScriptOne;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public Dimension getTailleFenetre() {
        return taille_fenetre;
    }

    public Font getLilyScriptOne() {
        return lilyScriptOne;
    }

    public Jeu getJeu() {
        return jeu;
    }
}
