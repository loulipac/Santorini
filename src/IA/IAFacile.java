package IA;

import Modele.Jeu;
import Modele.Joueur;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Utile.Constante.*;

/**
 * Classe IA Facile qui fait des coups aléatoires.
 */
public class IAFacile implements IA {
    protected final Jeu j;
    private final Random random;
    private final int joueur;

    public IAFacile(Jeu j, int joueur) {
        this.j = j;
        this.joueur = joueur;
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    /**
     * L'IA joue un coup aléatoire selon la situation du jeu.
     *
     * @return la position (x, y) du coup
     */
    public Point joue() {
        return switch (j.getSituation()) {
            case PLACEMENT -> jouePlacement();
            case SELECTION -> joueSelection();
            case DEPLACEMENT -> joueDeplacement();
            case CONSTRUCTION -> joueConstruction();
            default -> null;
        };
    }

    /**
     * L'IA déplace le batisseur à une position aléatoire récupéré grâce à une fonction qui donne
     * tous les déplacements possibles autour de ce même batisseur.
     *
     * @return position de la case à construire
     */
    protected Point joueDeplacement() {
        Point batisseur = j.getBatisseurEnCours();
        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        return accessibles.get(random.nextInt(accessibles.size()));
    }

    /**
     * L'IA construit à un Point aléatoire récupéré grâce à une fonction qui donne
     * toutes les constructions possibles autour d'un batisseur.
     *
     * @return position de la case à construire
     */
    protected Point joueConstruction() {
        Point batisseur = j.getBatisseurEnCours();
        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        return construction_possible.get(random.nextInt(construction_possible.size()));
    }

    /**
     * L'IA sélectionne un des deux batisseurs aléatoirement, s'il ne peut pas se déplacer, l'autre batisseur est
     * sélectionné. Même si ce dernier ne peut pas bouger, il est quand même sélectionné (défaite de l'IA).
     *
     * @return position du batisseur à sélectionner
     */
    protected Point joueSelection() {
        int index_batisseur = random.nextInt(2);
        if (j.getPlateau().getCasesAccessibles(j.getBatisseursJoueur(joueur).get(index_batisseur)).isEmpty()) {
            index_batisseur = (index_batisseur + 1) % 2;
        }
        return j.getBatisseursJoueur(joueur).get(index_batisseur);
    }

    /**
     * L'IA joue un placement aléatoire, c'est à dire, qu'elle pose un batisseur sur une case aléatoire si elle est vide.
     *
     * @return position du batisseur à poser
     */
    public Point jouePlacement() {
        Point case_alea;
        do {
            case_alea = new Point(
                    random.nextInt(PLATEAU_COLONNES),
                    random.nextInt(PLATEAU_LIGNES)
            );
        } while (!j.getPlateau().estLibre(case_alea));
        return case_alea;
    }
}
