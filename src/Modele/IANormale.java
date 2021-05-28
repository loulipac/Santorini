package Modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Modele.Constante.*;
import static Modele.Constante.CONSTRUCTION;

public class IANormale implements IA {
    Jeu jeu;
    private ArrayList<Point> batisseurs;
    int index_batisseur;
    Random random;

    private BatisseurBestMove bestMoveToDo;

    public IANormale(Jeu _jeu) {
        this.jeu = _jeu;
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    @Override
    public Point joue() {
        // TODO: faire que ça marche
        switch (jeu.getSituation()) {
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
        // TODO: Déplacer le batisseur vers celui en hauteur
        if(bestMoveToDo != null) {
            batisseurs.set(index_batisseur, bestMoveToDo._case);
            return bestMoveToDo._case;
        } else {
            Point batisseur = batisseurs.get(index_batisseur);
            ArrayList<Point> accessibles = jeu.getPlateau().getCasesAccessibles(batisseur);
            Point case_random = accessibles.get(random.nextInt(accessibles.size()));
            return case_random;
        }
    }

    private Point joueConstruction() {
        // TODO: Construire près du batisseur en hauteur
        ArrayList<Point> moves = getBestConstruction();
        if(moves.size() > 0) {
            return moves.get(random.nextInt(moves.size()));
        } else {
            return null;
        }
    }

    private Point joueSelection() {
        // TODO: Choisir l'autre batisseur si un est en hauteur par rapport à l'autre
        ArrayList<BatisseurBestMove> moves = getBestDeplacement();
        if (moves.size() > 0) {
            BatisseurBestMove bestMove = moves.get(random.nextInt(moves.size()));
            bestMoveToDo = bestMove;
            index_batisseur = bestMove.index_batisseur;
            return batisseurs.get(index_batisseur);
        } else {
            Point batisseur = batisseurs.get(random.nextInt(batisseurs.size()));
            bestMoveToDo = null;
            return batisseur;
        }
    }

    private Point jouePlacement() {
        // TODO: Mettre les batisseurs proches et vers le centre
        return null;
    }

    private class BatisseurBestMove {
        int index_batisseur;
        Point _case;

        public BatisseurBestMove(int _index_batisseur, Point _case) {
            this._case = _case;
            this.index_batisseur = _index_batisseur;
        }
    }

    private ArrayList<BatisseurBestMove> getBestDeplacement() {
        // TODO: se déplacer vesr le batisseur en hauteur
        ArrayList<BatisseurBestMove> bestMove = new ArrayList<>();
        for (Point batisseur : batisseurs) {
            ArrayList<Point> _cases = jeu.getPlateau().getCasesAccessibles(batisseur);
            int etageBatisseur = jeu.getPlateau().getTypeBatiments(batisseur);

            for (Point _case : _cases) {
                if (jeu.getPlateau().getTypeBatiments(_case) == etageBatisseur + 1) {
                    bestMove.add(new BatisseurBestMove(batisseurs.indexOf(batisseur), _case));
                }
            }
        }
        return bestMove;
    }

    private ArrayList<Point> getBestConstruction() {
        // TODO: Récupérer la meilleur construction selon celle autour du batisseur en hauteur
        ArrayList<Point> bestMove = new ArrayList<>();

        int index_other_batisseur = (index_batisseur % 2) + 1;
        int maxBatiment = 0;

        ArrayList<Point> _cases = jeu.getPlateau().getConstructionsPossible(batisseurs.get(index_other_batisseur));
        for (Point _case : _cases) {
            if(jeu.getPlateau().getTypeBatiments(_case) > maxBatiment) {
                bestMove.clear();
                bestMove.add(_case);
            } else if(jeu.getPlateau().getTypeBatiments(_case) == maxBatiment) {
                bestMove.add(_case);
            }
        }
        return bestMove;
    }

}
