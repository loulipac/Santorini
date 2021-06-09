package Reseau;

import Modele.Jeu;
import Vue.PanelLobby;

import java.awt.*;
import java.io.ObjectOutputStream;

/**
 * Classe abstraite composé d'élèment commun pour client et serveur.
 * Elle comporte une réfèrence au jeu pour envoyer les coups joués en local.
 * Le numéro et nom du joueur ainsi que le nom récupéré de l'adversaire.
 * Posède un flux d'envoie pour l'envoie de messages.
 * Modifie dynamiquement l'interface de LobbyPanel pour l'affichage du nom des joueurs.
 * @see PanelLobby#setAdversaireNom(String)
 * @see PanelLobby#setClient_ready(boolean)
 * @see Jeu
 */
public abstract class Reseau {
    private Jeu jeu;
    protected int numJoueur;
    protected String nomJoueur;
    protected String nomAdversaire;
    protected PanelLobby lobby;
    protected ObjectOutputStream streamEnvoie;
    protected Thread thread;
    protected boolean statut_modification = false;

    protected Reseau(String username, int numJoueur) {
        this.nomJoueur = username;
        this.numJoueur = numJoueur;
    }

    /**
     * Dans le cas du serveur, on crée une socket serveur et on attend une connexion client à l'aide d'un Thread.
     * Dans le cas du client, on se connecte au serveur depuis son ip et port et on démarre un Thread pour la communication.
     */
    public abstract void connexion();

    /**
     * Méthode analysant le contenu de la socket venant de l'adversaire.
     * @param _message objet contenant un code et un contenu
     */
    public abstract void analyserMessage(Message _message);

    /**
     * Ferme la socket de communication
     */
    public abstract void deconnexion();

    /**
     * Dans le cas du client, la fenêtre devient celle de Vue.PanelPartie.PanelPlateau.PanelPlateau. Pour le serveur, on envoie au client
     * que le jeu démarre et on change la fenêtre pour Vue.PanelPartie.PanelPlateau.PanelPlateau.
     */
    public abstract void demarrerPartie();

    /**
     * Envoie le coup qui vient d'être joué à l'adversaire.
     * @param coup position du coup
     */
    public abstract void envoieCoup(Point coup);

    /**
     * Envoie le nom du joueur à l'adversaire.
     */
    public abstract void envoyerNomUtilisateur();

    /**
     * Récupère le nom du joueur adverse pour l'avoir en local.
     * @param nom nom du joueur adverse
     */
    public abstract void setAdversaireNom(String nom);

    /**
     * Effectue les modifications de l'adversaire sur la grille locale.
     * @param coup position du coup
     */
    public void jouerCoupLocal(Point coup) {
        statut_modification = true;
        jeu.jouer(coup);
        statut_modification = false;
    }

    // SETTER / GETTER

    public String getNomJoueur() {
        return nomJoueur;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public String getNomAdversaire() {
        return nomAdversaire;
    }

    public boolean estEnModificationsLocal() {
        return statut_modification;
    }

    public void setLobby(PanelLobby lobby) {
        this.lobby = lobby;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}
