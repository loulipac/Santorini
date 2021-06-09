package IA;

import Modele.Jeu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Utile.Constante.*;

/**
 * Classe IA Facile qui fait des coups aléatoires.
 */
public class IAFacile implements IA {
    private final Jeu j;
    private final Random random;
    private final ArrayList<Point> batisseurs;
    private int index_batisseur;

    public IAFacile(Jeu j) {
        this.j = j;
        batisseurs = new ArrayList<>();
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
    private Point joueDeplacement() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        Point case_random = accessibles.get(random.nextInt(accessibles.size()));
        batisseurs.set(index_batisseur, case_random);
        return case_random;
    }

    /**
     * L'IA construit à un Point aléatoire récupéré grâce à une fonction qui donne
     * toutes les constructions possibles autour d'un batisseur.
     *
     * @return position de la case à construire
     */
    public Point joueConstruction() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        return construction_possible.get(random.nextInt(construction_possible.size()));
    }

    /**
     * L'IA sélectionne un des deux batisseurs aléatoirement, s'il ne peut pas se déplacer, l'autre batisseur est
     * sélectionné. Même si ce dernier ne peut pas bouger, il est quand même sélectionné (défaite de l'IA).
     *
     * @return position du batisseur à sélectionner
     */
    private Point joueSelection() {
        index_batisseur = random.nextInt(2);
        if (j.getPlateau().getCasesAccessibles(batisseurs.get(index_batisseur)).isEmpty()) {
            index_batisseur = (index_batisseur + 1) % 2;
        }
        return batisseurs.get(index_batisseur);
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
        batisseurs.add(case_alea);
        return case_alea;
    }
}
