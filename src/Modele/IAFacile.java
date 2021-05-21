package Modele;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static Modele.Constante.*;

public class IAFacile implements IA{
    static final int NUM_JOUEUR = JOUEUR2;
    Jeu j;
    Random random;
    private ArrayList<Point> batisseurs;
    int index_batisseur;

    public IAFacile(Jeu j) {
        this.j = j;
        batisseurs = new ArrayList<Point>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public int joue() {
        if(j.getJoueur_en_cours() == NUM_JOUEUR) {
            switch (j.getSituation()) {
                case PLACEMENT:
                    jouePlacement();
                    return PLACEMENT;
                case SELECTION:
                    joueSelection();
                    return SELECTION;
                case DEPLACEMENT:
                    joueDeplacement();
                    return DEPLACEMENT;
                case CONSTRUCTION:
                    joueConstruction();
                    return CONSTRUCTION;
                default:
                    break;
            }
        }
        return -1;
    }

    private void joueDeplacement() {
        System.out.println("Déplacement");
        index_batisseur = random.nextInt(2);
        Point batisseur = batisseurs.get(index_batisseur);

        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        Point case_random = accessibles.get(random.nextInt(accessibles.size()));

        batisseurs.set(index_batisseur, case_random);
        System.out.println("Batisseur déplacé : (" + case_random.x + ", " + case_random.y + ")");
        j.jouer(case_random.y, case_random.x);
    }

    private void joueConstruction() {
        System.out.println("Construction");
        index_batisseur = random.nextInt(2);
        Point batisseur = batisseurs.get(index_batisseur);

        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        Point case_random = construction_possible.get(random.nextInt(construction_possible.size()));

        j.jouer(case_random.x, case_random.y);
    }

    private void joueSelection() {
        System.out.println("Selectionne");
        index_batisseur = random.nextInt(2);
        Point batisseur = batisseurs.get(index_batisseur);
        j.jouer(batisseur.y, batisseur.x);
    }

    private void jouePlacement() {
        int colonnes = j.getPlateau().getColonnes();
        int lignes = j.getPlateau().getLignes();
        Point case_alea = null;
        do {
            case_alea = new Point(
                    random.nextInt(colonnes),
                    random.nextInt(lignes)
            );
        } while (!j.getPlateau().estLibre(case_alea.y, case_alea.x));
        batisseurs.add(case_alea);
        System.out.println("Batisseur ajouté : (" + case_alea.x + ", " + case_alea.y + ")");
        j.jouer(case_alea.y, case_alea.x);
    }
}
