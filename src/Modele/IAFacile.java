package Modele;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static Modele.Constante.*;

public class IAFacile implements IA {
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

    public Point joue() {
        switch (j.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                return joueSelection();
            case DEPLACEMENT:
                return joueDeplacement();
            case CONSTRUCTION:
                return joueConstruction();
            default:
                return null;
        }
    }

    private Point joueDeplacement() {
        System.out.println("Déplacement");
        Point batisseur = batisseurs.get(index_batisseur);
        System.out.println("Batisseur à la position (" + batisseur.x + ", " + batisseur.y + ")");
        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        Point case_random = accessibles.get(random.nextInt(accessibles.size()));
        batisseurs.set(index_batisseur, case_random);
        System.out.println("Envoie position déplacement. Est libre ?" + j.getPlateau().estLibre(case_random));
        //System.out.println("Batisseur déplacé : (" + case_random.x + ", " + case_random.y + ")");
        return case_random;
    }

    private Point joueConstruction() {
        System.out.println("Construction");
        Point batisseur = batisseurs.get(index_batisseur);
        System.out.println("Batisseur à la position (" + batisseur.x + ", " + batisseur.y + ")");

        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        Point  case_random = construction_possible.get(random.nextInt(construction_possible.size()));
        System.out.println("Envoie position construction. Est libre ?" + j.getPlateau().estLibre(case_random));
        return case_random;
    }

    private Point joueSelection() {
        System.out.println("Selectionne");
        index_batisseur = random.nextInt(2);
        Point batisseur = batisseurs.get(index_batisseur);
        return batisseur;
    }

    private Point jouePlacement() {
        int colonnes = j.getPlateau().getColonnes();
        int lignes = j.getPlateau().getLignes();
        Point case_alea = null;
        do {
            case_alea = new Point(
                    random.nextInt(colonnes),
                    random.nextInt(lignes)
            );
        } while (!j.getPlateau().estLibre(case_alea));
        batisseurs.add(case_alea);
        System.out.println("Batisseur ajouté : (" + case_alea.x + ", " + case_alea.y + ")");
        return case_alea;
    }
}
