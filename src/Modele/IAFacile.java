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
    Point batisseur_choisi;

    public IAFacile(Jeu j) {
        this.j = j;
        batisseurs = new ArrayList<Point>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public void joue() {
        if(j.getJoueur_en_cours() == NUM_JOUEUR) {
            switch (j.getSituation()) {
                case PLACEMENT:
                    jouePlacement();
                    break;
                case SELECTION:
                    joueSelection();
                    break;
                case DEPLACEMENT:
                    joueAction(j.getPlateau().getCasesAccessibles(batisseur_choisi));
                    break;
                case CONSTRUCTION:
                    joueAction(j.getPlateau().getConstructionsPossible(batisseur_choisi));
                    break;
                default:
                    break;
            }
        }
    }

    private void joueAction(ArrayList<Point> case_disponibles) {
        System.out.println("Action");
        Point case_random = case_disponibles.get(random.nextInt(case_disponibles.size()));
        j.jouer(case_random.x, case_random.y);
    }

    private void joueSelection() {
        System.out.println("Selectionne");
        Point batisseur = batisseurs.get(random.nextInt(2));
        j.jouer(batisseur.y, batisseur.x);
        batisseur_choisi = batisseur;
        assert(j.getBatisseur_en_cours() == batisseur_choisi);
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
        j.jouer(case_alea.y, case_alea.x);
    }
}
