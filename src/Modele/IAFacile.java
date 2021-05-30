package Modele;

import java.awt.*;
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
        batisseurs = new ArrayList<>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public Point joue() {
        return switch (j.getSituation()) {
            case PLACEMENT -> jouePlacement();
            case SELECTION -> joueSelection();
            case DEPLACEMENT -> joueDeplacement();
            case CONSTRUCTION -> joueConstruction();
            default -> null;
        };
    }

    private Point joueDeplacement() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> accessibles = j.getPlateau().getCasesAccessibles(batisseur);
        Point case_random = accessibles.get(random.nextInt(accessibles.size()));
        batisseurs.set(index_batisseur, case_random);
        return case_random;
    }

    private Point joueConstruction() {
        Point batisseur = batisseurs.get(index_batisseur);
        ArrayList<Point> construction_possible = j.getPlateau().getConstructionsPossible(batisseur);
        return construction_possible.get(random.nextInt(construction_possible.size()));
    }

    private Point joueSelection() {
        System.out.println("Selectionne");
        index_batisseur = random.nextInt(2);
        return batisseurs.get(index_batisseur);
    }

    private Point jouePlacement() {
        Point case_alea;
        do {
            case_alea = new Point(
                    random.nextInt(PLATEAU_COLONNES),
                    random.nextInt(PLATEAU_LIGNES)
            );
        } while (!j.getPlateau().estLibre(case_alea));
        batisseurs.add(case_alea);
        System.out.println("Batisseur ajouté : (" + case_alea.x + ", " + case_alea.y + ")");
        return case_alea;
    }
}
