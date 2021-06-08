package Historique;

import Modele.Jeu;
import Modele.Joueur;

import static Utile.Constante.*;

/**
 * Classe abstraite servant de modèles pour les coups joués dans le jeu.
 * @see CoupConstruire
 * @see CoupDeplacer
 * @see Historique
 */
public abstract class Commande {
    protected Joueur joueur;

    /**
     * Constructeur d'une commande.
     * @param joueur joueur ayant effectué la commande
     */
    protected Commande(Joueur joueur) {
        this.joueur = joueur;
    }

    protected abstract void action(Jeu jeu, int type);

    /**
     * Exécution de la commande.
     * @param jeu objet sur lequel exécuter la commande
     */
    public void execute(Jeu jeu) {
        action(jeu, REDO);
    }

    /**
     * Désexécution de la commande.
     * @param jeu objet sur lequel exécuter la commande
     */
    public void desexecute(Jeu jeu) {
        action(jeu, UNDO);
    }
}
