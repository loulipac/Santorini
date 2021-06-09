package Modele;

import Historique.*;
import IA.*;
import Patterns.Observateur;
import Reseau.Reseau;
import Utile.ConfigurationPartie;
import Utile.LecteurSon;
import Vue.PanelPartie.PanelPlateau.PanelPlateau;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static Utile.Constante.*;

/**
 * Classe permettant de gérer tout le processus d'une partie en éditant les valeurs du plateau.
 *
 * @see Plateau
 */
public class Jeu {
    private final LecteurSon constructionSon;

    private final Plateau plateau;
    private int situation;
    private int nombreBatisseurs;
    private int vitesse_ia;
    private int nbTours;
    private int iJoueurs;
    private boolean iaStatut;
    private boolean jeuFini;
    private Point batisseurEnCours;
    private final Observateur observateur;
    private final Historique histo;
    private Commande cmd;
    private Joueur gagnant;
    private final Joueur[] joueurs;
    private Point[] deplacementEnCours;
    private Reseau netUser;

    ConfigurationPartie configurationPartie;

    /**
     * Instantie une classe jeu.
     *
     * @param o observateur
     */
    public Jeu(Observateur o, ConfigurationPartie config) {
        joueurs = new Joueur[2];
        plateau = new Plateau();
        deplacementEnCours = null;
        jeuFini = false;
        iaStatut = true;
        batisseurEnCours = null;
        gagnant = null;

        vitesse_ia = 1;
        configurationPartie = config;
        situation = PLACEMENT;
        nombreBatisseurs = 0;
        nbTours = 0;
        constructionSon = new LecteurSon(SON_CONSTRUCTION);

        observateur = o;
        histo = new Historique(this);

        iJoueurs = config.getIndexJoueurCommence();

        if (config.getIaMode2() != 0) {
            joueurs[0] = new JoueurIA(this, JOUEUR1, setIA(config.getIaMode1(),JOUEUR1), vitesse_ia);
            joueurs[1] = new JoueurIA(this, JOUEUR2, setIA(config.getIaMode2(),JOUEUR2), vitesse_ia);
        } else if (config.getIaMode1() != 0) {
            joueurs[0] = new JoueurHumain(this, JOUEUR1);
            joueurs[1] = new JoueurIA(this, JOUEUR2, setIA(config.getIaMode1(),JOUEUR2), vitesse_ia);
        } else {
            joueurs[0] = new JoueurHumain(this, JOUEUR1);
            joueurs[1] = new JoueurHumain(this, JOUEUR2);
        }
        iaEssayeJouer();
    }

    public Jeu(Observateur o) {
        this(o, new ConfigurationPartie(0, 0));
    }

    private IA setIA(int ia_mode, int joueur) {
        return switch (ia_mode) {
            case 1 -> new IAFacile(this,joueur);
            case 2 -> new IANormale(this,joueur);
            case 3 -> new IADifficile(this);
            default -> null;
        };
    }

    /**
     * Effectue des actions selon la situation du jeu parmi placement des batisseurs, selection de batisseur, déplacement des batisseurs et construction des bâtiments.
     */
    public void jouer(Point position) {
        cmd = null;
        situation = plateau.estBatisseur(position, getJoueurEnCours()) && situation == DEPLACEMENT ? SELECTION : situation;
        switch (situation) {
            case PLACEMENT -> jouePlacement(position);
            case SELECTION -> joueSelection(position);
            case DEPLACEMENT -> joueDeplacement(position);
            case CONSTRUCTION -> joueConstruction(position);
            default -> System.err.println("Unknown situation");
        }
        histo.stocker(cmd);
    }

    /**
     * Joue le placement d'un batisseur si la case est vide.
     */
    private void jouePlacement(Point position) {
        if (plateau.estLibre(position)) {
            cmd = new CoupDeplacer(joueurs[iJoueurs], null, position);
            plateau.ajouterJoueur(position, joueurs[iJoueurs]);
            if (netUser != null) netUser.envoieCoup(position);
            getJoueurEnCours().addBatisseur(position);
            nombreBatisseurs++;
            verificationNbBatisseur();
        }
    }

    /**
     * Joue la selection d'un batisseur s'il y en a un, et qu'il appartien au joueur, à la position donnée.
     */
    private void joueSelection(Point position) {
        batisseurEnCours = choisirBatisseur(position);
        situation = batisseurEnCours == null ? SELECTION : DEPLACEMENT;
        envoyerCoup(position);
    }

    /**
     * Déplace le batisseur en cours à la position donnée si c'est possible.
     */
    private void joueDeplacement(Point position) {
        Point prevPos = batisseurEnCours;
        if (avancer(position, batisseurEnCours)) {
            ArrayList<Point> batisseurs_en_cours = getJoueurEnCours().getBatisseurs();
            batisseurs_en_cours.set(batisseurs_en_cours.indexOf(prevPos), position);

            setDeplacementEnCours(prevPos, batisseurEnCours);
            cmd = new CoupDeplacer(joueurs[iJoueurs], prevPos, batisseurEnCours);
            situation = CONSTRUCTION;
            envoyerCoup(position);
        }
        victoireJoueur();
    }

    /**
     * Construit un bâtiment à la position donnée si c'est possible.
     */
    private void joueConstruction(Point position) {
        if (!jeuFini && construire(position, batisseurEnCours)) {
            constructionSon.joueSon(false);
            cmd = new CoupConstruire(joueurs[iJoueurs], position, batisseurEnCours);
            finTour();
            situation = SELECTION;
            envoyerCoup(position);
        }
    }

    /**
     * L'IA qui doit jouer joue.
     */
    public void iaEssayeJouer() {
        if (getJoueurEnCours().getClass() == JoueurIA.class) {
            ((JoueurIA) getJoueurEnCours()).timerIaSet(iaStatut);
        }
    }

    /**
     * Vérifie que le nombre de batisseur posé correspond à certaines valeurs pour changer le joueur et la situation du jeu.
     */
    public void verificationNbBatisseur() {
        if (nombreBatisseurs % NOMBRE_BATISSEUR_JOUEUR == 0) {
            finTour();
        }
        if (nombreBatisseurs >= (NOMBRE_BATISSEUR_JOUEUR * 2)) {
            situation = SELECTION;
        }
    }

    /**
     * Choisi le batisseur à la position (l, c).
     *
     * @return le batisseur du joueur s'il existe à cette position
     */
    private Point choisirBatisseur(Point position) {
        return plateau.estBatisseur(position, joueurs[iJoueurs]) ? position : null;
    }

    /**
     * Vérifie que la case (l, c) est atteignable sur la grille (selon la situation)
     *
     * @return vrai s'il on peut atteindre la case
     */
    public boolean estAtteignable(Point position) {
        return switch (situation) {
            case DEPLACEMENT -> (batisseurEnCours != null) && plateau.deplacementPossible(position, batisseurEnCours);
            case CONSTRUCTION -> (batisseurEnCours != null) && plateau.peutConstruire(position, batisseurEnCours);
            case SELECTION -> plateau.estBatisseur(position, joueurs[iJoueurs]);
            case PLACEMENT -> nombreBatisseurs < 4;
            default -> false;
        };
    }

    /**
     * Change le joueur en cours (si l'indice du joueur en cours est 1, ça devient 0 et inversement)
     */
    public void changerJoueur() {
        iJoueurs = (iJoueurs + 1) % joueurs.length;
    }

    /**
     * Met à jour les observateurs.
     * @see PanelPlateau#miseAjour()
     */
    public void MAJObservateur() {
        observateur.miseAjour();
    }

    /**
     * Fini le tour pour le joueur en cours.
     */
    public void finTour() {
        nbTours++;
        changerJoueur();
        batisseurEnCours = null;
        MAJObservateur();
        if (checkPerdu()) {
            return;
        }
        iaEssayeJouer();
    }

    /**
     * Vérifie que le joueur a perdu lors de ce tour, c'est à dire, qu'il n'ait plus aucun mouvement possible pour ces deux bâtisseurs
     */
    private boolean checkPerdu() {
        ArrayList<Point> batisseur_joueur = getJoueurEnCours().getBatisseurs();
        if (batisseur_joueur.size() < 2) return false;
        if (plateau.getCasesAccessibles(batisseur_joueur.get(0)).isEmpty() &&
                plateau.getCasesAccessibles(batisseur_joueur.get(1)).isEmpty()
        ) {
            gagnant = joueurs[(iJoueurs + 1) % joueurs.length];
            jeuFini = true;
            MAJObservateur();
            return true;
        }
        return false;
    }

    /**
     * Déplace le batisseur vers les coordonnées l et c si c'est possible.
     *
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai s'il a été possible de se déplacer
     */
    private boolean avancer(Point position, Point batisseur) {
        if (plateau.deplacementPossible(position, batisseur)) {
            plateau.enleverJoueur(batisseur); // enlève le batisseurs de la case du point batisseurs
            plateau.ajouterJoueur(position, joueurs[iJoueurs]); // ajoute un batisseurs à la case en position l,c
            batisseurEnCours = position;
            return true;
        }
        return false;
    }

    /**
     * Construit aux coordonnées l et c une hauteur de bâtiment si c'est possible.
     *
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai si la construction a bien eu lieu
     * @see Plateau#peutConstruire(Point, Point batisseur)
     * @see Plateau#ameliorerBatiment(Point)
     */
    private boolean construire(Point position, Point batisseur) {
        if (plateau.peutConstruire(position, batisseur)) {
            plateau.ameliorerBatiment(position);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si le batisseur du joueur en cours est sur un bâtiment d'hauteur 3.
     * Si c'est le cas, le jeu s'arrête et l'observateur est notifié de la victoire.
     */
    public void victoireJoueur() {
        if (batisseurEnCours != null && plateau.getTypeBatiments(batisseurEnCours) == TOIT) {
            gagnant = getJoueurEnCours();
            jeuFini = true;
            MAJObservateur();
        }
    }

    /**
     * Envoie le mouvement à l'adversaire lors d'une partie en réseau.
     *
     * @param position position (x, y) du coup
     */
    private void envoyerCoup(Point position) {
        if (netUser != null && !netUser.estEnModificationsLocal()) netUser.envoieCoup(position);
    }

    /**
     * Augmente la vitesse des IAs
     *
     * @param index_acceleration coefficient de vitesse de l'IA
     */
    public void accelererIA(double index_acceleration) {
        vitesse_ia = (int) index_acceleration;
        if (joueurs[0].getClass() == JoueurIA.class) {
            ((JoueurIA) joueurs[0]).setVitesseIA(vitesse_ia);
        }
        if (joueurs[1].getClass() == JoueurIA.class) {
            ((JoueurIA) joueurs[1]).setVitesseIA(vitesse_ia);
        }
    }

    /**
     * Demande à l'historique de sauvegarder la partie en cours.
     */
    public String sauvegarder() {
        return histo.sauvegarder();
    }

    /**
     * Charge grâce à l'historique une partie depuis un fichier.
     */
    public void charger(Scanner lecteur) {
        histo.charger(lecteur);
    }

    public void charger(String filename) {
        histo.charger(filename);
    }

    /**
     * Remet à zéro une partie. C'est à dire de remettre les règles de la partie comme au départ.
     */
    public void RAZ() {
        plateau.RAZ();
        jeuFini = false;
        gagnant = null;
        situation = PLACEMENT;
        batisseurEnCours = null;
        nombreBatisseurs = 0;
        iJoueurs = configurationPartie.getIndexJoueurCommence();
        if (configurationPartie.getIaMode2() != 0) {
            joueurs[0] = new JoueurIA(this, JOUEUR1, setIA(configurationPartie.getIaMode1(),JOUEUR1), vitesse_ia);
            joueurs[1] = new JoueurIA(this, JOUEUR2, setIA(configurationPartie.getIaMode2(),JOUEUR2), vitesse_ia);
        } else if (configurationPartie.getIaMode1() != 0) {
            joueurs[0] = new JoueurHumain(this, JOUEUR1);
            joueurs[1] = new JoueurIA(this, JOUEUR2, setIA(configurationPartie.getIaMode1(),JOUEUR2), vitesse_ia);
        } else {
            joueurs[0] = new JoueurHumain(this, JOUEUR1);
            joueurs[1] = new JoueurHumain(this, JOUEUR2);
        }
        MAJObservateur();
    }

    /**
     * Annule le dernier coup joué.
     */
    public void annuler() {
        if (histo.peutAnnuler()) histo.annuler();
    }

    /**
     * Refais le coup suivant de l'historique
     */
    public void refaire() {
        if (histo.peutRefaire()) histo.refaire();
    }

    /**
     * Switch le statut de l'IA, càd la met en pause ou en marche.
     */
    public void iaSwitch() {
        this.iaStatut = !iaStatut;
        if (getJoueurEnCours().getClass() == JoueurIA.class) {
            ((JoueurIA) getJoueurEnCours()).timerIaSet(iaStatut);
        }
    }

    /**
     * Désactive l'IA.
     */
    public void desactiverIA() {
        this.iaStatut = false;
        for (Joueur j : joueurs) {
            if (j.getClass() == JoueurIA.class) {
                ((JoueurIA) j).timerIaSet(iaStatut);
            }
        }

    }

    // GETTER / SETTER

    public boolean estJeufini() {
        return jeuFini;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Point getBatisseurEnCours() {
        return batisseurEnCours;
    }

    public int getSituation() {
        return situation;
    }

    public Joueur getJoueurEnCours() {
        return joueurs[iJoueurs];
    }

    public int getNombreBatisseurs() {
        return nombreBatisseurs;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public void setBatisseurEnCours(Point batisseur) {
        this.batisseurEnCours = batisseur;
    }

    public void setNombreBatisseurs(int nombre_batisseurs) {
        this.nombreBatisseurs = nombre_batisseurs;
    }

    public void setJeuFini(boolean value) {
        jeuFini = value;
    }

    public Joueur getGagnant() {
        return gagnant;
    }

    public ArrayList<Point> getBatisseurs() {
        return getJoueurEnCours().getBatisseurs();
    }

    public ArrayList<Point> getBatisseursJoueur(int joueur) {
        if (joueur == JOUEUR1) {
            return getJ1().getBatisseurs();
        } else {
            return getJ2().getBatisseurs();
        }
    }

    public boolean getIaStatut() {
        return iaStatut;
    }

    public Historique getHistorique() {
        return histo;
    }

    public Joueur getJ1() {
        return joueurs[0];
    }

    public Joueur getJ2() {
        return joueurs[1];
    }

    public int getNbTours() {
        return nbTours;
    }

    public void setNetUser(Reseau netUser) {
        this.netUser = netUser;
        netUser.setJeu(this);
    }

    public ConfigurationPartie getConfigurationPartie() {
        return configurationPartie;
    }

    public void setConfigurationPartie(ConfigurationPartie config) {
        configurationPartie = config;
    }

    public Reseau getNetUser() {
        return netUser;
    }

    public Point[] getDeplacementEnCours() {
        return deplacementEnCours;
    }

    public void setDeplacementEnCours(Point prevPos, Point newPos) {
        if (prevPos != null && newPos != null) {
            deplacementEnCours = new Point[2];
            deplacementEnCours[0] = (Point) prevPos.clone();
            deplacementEnCours[1] = (Point) newPos.clone();
        } else {
            deplacementEnCours = null;
        }
    }

    public void setDeplacementEnCours() {
        setDeplacementEnCours(null, null);
    }

    public static int getAutreJoueur(int joueur) {
        return (joueur == JOUEUR2 ? JOUEUR1 : JOUEUR2);
    }


    // OVERRIDE MÉTHODES HÉRITÉES

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Jeu)) return false;

        Jeu j = (Jeu) o;

        return getJoueurEnCours().equals(j.getJoueurEnCours()) &&
                getJ1().equals(j.getJ1()) &&
                getJ1().getClass() == j.getJ1().getClass() &&
                getJ2().equals(j.getJ2()) &&
                getJ2().getClass() == j.getJ2().getClass() &&
                situation == j.situation &&
                nombreBatisseurs == j.nombreBatisseurs &&
                Objects.equals(batisseurEnCours, j.batisseurEnCours) &&
                plateau.equals(j.plateau) &&
                configurationPartie.equals(j.configurationPartie);
    }
}