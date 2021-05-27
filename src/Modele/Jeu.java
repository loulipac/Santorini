package Modele;

import Vue.Observer;
import Vue.LecteurSon;

import java.awt.*;
import java.util.ArrayList;

import static Modele.Constante.*;

/**
 * Classe permettant de gérer tout le processus d'une partie en éditant les valeurs du plateau.
 *
 * @see Plateau
 */
public class Jeu {
    private Plateau plateau;

    private int joueur_en_cours;
    private int situation;
    private int nombre_batisseurs;
    private Point batisseur_en_cours;
    private final LecteurSon construction_son;
    private Observer observateur;
    private boolean jeu_fini;
    private Historique histo;
    private Joueur gagnant;
    private boolean ia_statut;
    int vitesse_ia;
    Commande cmd;

    Joueur j1, j2;
    Commande cmd;

    /**
     * Instantie une classe jeu.
     *
     * @param l nombre de ligne
     * @param c nombre de colonne
     * @param o observateur
     */
    public Jeu(int l, int c, Observer o, int ia1_mode, int ia2_mode) {
        vitesse_ia = 1;
        ia_statut = true;
        situation = PLACEMENT;
        joueur_en_cours = JOUEUR1;
        plateau = new Plateau(l, c);
        nombre_batisseurs = 0;
        batisseur_en_cours = null;
        construction_son = new LecteurSon("boulder_drop.wav");
        observateur = o;
        jeu_fini = false;
        histo = new Historique(this);
        gagnant = null;

        if (ia2_mode != 0) {
            j1 = new JoueurIA(this, JOUEUR1, setIA(ia1_mode), vitesse_ia);
            j2 = new JoueurIA(this, JOUEUR2, setIA(ia2_mode), vitesse_ia);
        } else if (ia1_mode != 0) {
            j1 = new JoueurHumain(this, JOUEUR1);
            j2 = new JoueurIA(this, JOUEUR2, setIA(ia1_mode), vitesse_ia);
        } else {
            j1 = new JoueurHumain(this, JOUEUR1);
            j2 = new JoueurHumain(this, JOUEUR2);
        }
        iaJoue();
    }

    private IA setIA(int ia_mode) {
        switch (ia_mode) {
            case 1 -> {
                return new IAFacile(this);
            }
            case 2 -> {
                return new IANormale(this);
            }
            case 3 -> {
                return new IADifficile(this);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Effectue des actions selon la situation du jeu parmi placement des batisseurs, selection de batisseur, déplacement des batisseurs et construction des bâtiments.
     */
    public void jouer(Point position) {
        cmd = null;
        switch (situation) {
            case PLACEMENT:
                jouePlacement(position);
                break;
            case SELECTION:
                joueSelection(position);
                break;
            case DEPLACEMENT:
                joueDeplacement(position);
                break;
            case CONSTRUCTION:
                joueConstruction(position);
                break;
            default:
                System.err.println("Unknown situation");
                break;
        }
        histo.store(cmd);
    }

    private void jouePlacement(Point position) {
        if (plateau.estLibre(position)) {
            cmd = new CoupDeplacer(joueur_en_cours, null, position);
            plateau.ajouterJoueur(position, joueur_en_cours);
            getJoueurType_en_cours().addBatisseur(position);
            nombre_batisseurs++;
            checkBuilderNumber();
        }
    }


    private void joueSelection(Point position) {
        batisseur_en_cours = choisirBatisseur(position);
        situation = batisseur_en_cours == null ? SELECTION : DEPLACEMENT;
    }

    private void joueDeplacement(Point position) {
        Point prevPos = batisseur_en_cours;
        if (avancer(position, batisseur_en_cours)) {
            ArrayList<Point> batisseurs_en_cours = getJoueurType_en_cours().getBatisseurs();
            batisseurs_en_cours.set(batisseurs_en_cours.indexOf(prevPos), position);

            cmd = new CoupDeplacer(joueur_en_cours, prevPos, batisseur_en_cours);
            situation = CONSTRUCTION;
        }
        victoireJoueur();
    }

    private void joueConstruction(Point position) {
        if (!jeu_fini && construire(position, batisseur_en_cours)) {
            construction_son.joueSon(false);
            cmd = new CoupConstruire(joueur_en_cours, position, batisseur_en_cours);
            finTour();
            situation = SELECTION;
        }
    }

    public void iaJoue() {
        if (getJoueurType_en_cours().getClass() == JoueurIA.class) {
            ((JoueurIA) getJoueurType_en_cours()).timerIaSet(ia_statut);
        }
    }

    public void checkBuilderNumber() {
        if (nombre_batisseurs % 2 == 0) {
            finTour();
        }
        if (nombre_batisseurs >= 4) {
            situation = SELECTION;
        }
    }

    /**
     * Choisi le batisseur à la position (l, c).
     *
     * @return le batisseur du joueur s'il existe à cette position
     */
    private Point choisirBatisseur(Point position) {
        return plateau.estBatisseur(position, joueur_en_cours) ? position : null;
    }

    /**
     * Vérifie que la case (l, c) est atteignable sur la grille (selon la situation)
     *
     * @return vrai s'il on peut atteindre la case
     */
    public boolean estAtteignable(Point position) {
        if (situation == DEPLACEMENT)
            return (batisseur_en_cours != null) && plateau.deplacementPossible(position, batisseur_en_cours);
        else if (situation == CONSTRUCTION)
            return (batisseur_en_cours != null) && plateau.peutConstruire(position, batisseur_en_cours);
        else if (situation == SELECTION) return plateau.estBatisseur(position, joueur_en_cours);
        else return nombre_batisseurs < 4;
    }

    public void switchPlayer() {
        joueur_en_cours = (joueur_en_cours % 16) + 8;
    }

    public void MAJObservateur() {
        observateur.miseAjour();
    }

    /**
     * Fini le tour pour le joueur en cours.
     */
    public void finTour() {
        switchPlayer();
        batisseur_en_cours = null;
        MAJObservateur();
        iaJoue();
    }

    /**
     * Déplace le batisseur vers les coordonnées l et c si c'est possible.
     *
     * @param batisseur position (x;y) d'un batisseur
     * @return vrai s'il a été possible de se déplacer
     */
    private boolean avancer(Point position, Point batisseur) {
        if (plateau.deplacementPossible(position, batisseur)) {
            plateau.ajouterJoueur(batisseur, 0); // enlève le batisseurs de la case du point batisseurs
            plateau.ajouterJoueur(position, joueur_en_cours); // ajoute un batisseurs à la case en position l,c
            batisseur_en_cours = position;
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
            return plateau.ameliorerBatiment(position);
        } else {
            return false;
        }
    }

    /**
     * Vérifie si le batisseur du joueur en cours est sur un bâtiment d'hauteur 3.
     * Si c'est le cas, le jeu s'arrête et l'observateur est notifié de la victoire.
     */
    public void victoireJoueur() {
        if (batisseur_en_cours != null && plateau.getTypeBatiments(batisseur_en_cours) == Plateau.TOIT) {
            System.out.println("cest fini");
            gagnant = getJoueurType_en_cours();
            jeu_fini = true;
            observateur.miseAjour();
        }
    }

    public void accelererIA(double index_acceleration) {
        vitesse_ia = (int) index_acceleration;
        if (j1.getClass() == JoueurIA.class) {
            ((JoueurIA) j1).setVitesseIA(vitesse_ia);
        }
        if (j2.getClass() == JoueurIA.class) {
            ((JoueurIA) j2).setVitesseIA(vitesse_ia);
        }
    }

    public void sauvegarder() {
        histo.save();
    }

    public void charger(String filename) {
        RAZ();
        histo.load(filename);
    }

    public void RAZ() {
        plateau.RAZ();
        situation = PLACEMENT;
        batisseur_en_cours = null;
        nombre_batisseurs = 0;
        joueur_en_cours = JOUEUR1;
    }

    public void undo() {
        if (histo.canUndo()) histo.undo();
    }

    public void redo() {
        if (histo.canRedo()) histo.redo();
    }

    public boolean estJeufini() {
        return jeu_fini;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Point getBatisseur_en_cours() {
        return batisseur_en_cours;
    }

    public int getSituation() {
        return situation;
    }

    public int getJoueur_en_cours() {
        return joueur_en_cours;
    }

    public Joueur getJoueurType_en_cours() {
        if (joueur_en_cours == j1.getNum_joueur()) {
            return j1;
        } else if (joueur_en_cours == j2.getNum_joueur()) {
            return j2;
        } else {
            return null;
        }
    }

    public int getNombre_batisseurs() {
        return nombre_batisseurs;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public void setBatisseur_en_cours(Point batisseur) {
        this.batisseur_en_cours = batisseur;
    }

    public void setNombre_batisseurs(int nombre_batisseurs) {
        this.nombre_batisseurs = nombre_batisseurs;
    }

    public void setJeu_fini(boolean value) {
        jeu_fini = value;
    }

    public void iaSwitch() {
        this.ia_statut = !ia_statut;
        if (getJoueurType_en_cours().getClass() == JoueurIA.class) {
            ((JoueurIA) getJoueurType_en_cours()).timerIaSet(ia_statut);
        }
    }

    public Joueur getGagnant() {
        return gagnant;
    }

    public ArrayList<Point> getBatisseurs(int joueur) {
        return getJoueurType_en_cours().getBatisseurs();
    }

    public boolean getIa_statut() {
        return ia_statut;
    }
}
