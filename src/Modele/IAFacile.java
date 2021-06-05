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

    protected Point joueDeplacement() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        Point case_random = accessibles.get(random.nextInt(accessibles.size()));
        batisseurs.set(index_batisseur, case_random);
        return case_random;
    }

     protected Point joueConstruction() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        Point  case_random = construction_possible.get(random.nextInt(construction_possible.size()));
        return case_random;
    }

    protected Point joueSelection() {
        index_batisseur = random.nextInt(2);
        Point batisseur = batisseurs.get(index_batisseur);
        return batisseur;
    }

    protected Point jouePlacement() {
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
        return case_alea;
    }
}
