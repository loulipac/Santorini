package IA;

import Modele.Jeu;
import Modele.Plateau;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Utile.Constante.*;

/**
 * Classe IA Difficile utilisant une heuristique et l'algorithme minimax pour trouver le meilleur coup à faire.
 */
public class IADifficile implements IA {

    private final Jeu jeu;
    private final Plateau plateau;
    private final Random random;
    private Coups meilleur_coup;

    public IADifficile(Jeu _jeu) {
        jeu = _jeu;
        plateau = jeu.getPlateau();
        random = new Random();
    }

    private static class Coups {
        private final Point batisseur;
        private final Point deplacement;
        private final Point construction;

        public Coups(Point batisseur, Point deplacement, Point construction) {
            this.batisseur = batisseur;
            this.deplacement = deplacement;
            this.construction = construction;
        }

        @Override
        public String toString() {
            return "Coup{" +
                    "batisseur=" + batisseur +
                    ", deplacement=" + deplacement +
                    ", construction=" + construction +
                    '}';
        }
    }


    /**
     * Fonction minimax qui implémente l'algorithme du même nom pour trouver le meilleur coup depuis l'heuristique.
     *
     * @param plateau             copie du plateau à analyser
     * @param profondeur_en_cours profondeur de coup à analyser
     * @param batisseurs_j1       tableau des batisseurs du joueur
     * @param batisseurs_j2       tableau des batisseurs du joueur
     * @return meilleure valeur de l'heuristique
     */
    public float minimax(Plateau plateau, int joueur_maximise, int joueur_en_cours, int profondeur_en_cours, ArrayList<Point> batisseurs_j1, ArrayList<Point> batisseurs_j2) {

        float score_actuel;
        float meilleur_score;
        int autre_joueur = Jeu.getAutreJoueur(joueur_en_cours);
        ArrayList<Point> batisseurs_en_cours = joueur_en_cours == JOUEUR1 ? batisseurs_j1 : batisseurs_j2;

        boolean est_sur_toit;
        boolean impossibleDeJouer_j1 = plateau.getCasesAccessibles(batisseurs_j1.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j1.get(1)).isEmpty();
        boolean impossibleDeJouer_j2 = plateau.getCasesAccessibles(batisseurs_j2.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j2.get(1)).isEmpty();
        boolean aGagne_j1 = (plateau.getTypeBatiments(batisseurs_j1.get(0)) == TOIT) || (plateau.getTypeBatiments(batisseurs_j1.get(1)) == TOIT);
        boolean aGagne_j2 = (plateau.getTypeBatiments(batisseurs_j2.get(0)) == TOIT) || (plateau.getTypeBatiments(batisseurs_j2.get(1)) == TOIT);
        boolean jeu_fini = impossibleDeJouer_j1 || impossibleDeJouer_j2 || aGagne_j1 || aGagne_j2;
        boolean est_joueur_maximise = joueur_en_cours == joueur_maximise;

        meilleur_score = est_joueur_maximise ? -10000000000000.f : 10000000000000.f;

        int profondeur_max = 3;
        int multi = jeu_fini ? (profondeur_max - profondeur_en_cours + 1) : 1;

        if (profondeur_en_cours == profondeur_max || jeu_fini) {
            return multi * calculerHeuristique(plateau, joueur_maximise, 1, batisseurs_j1, batisseurs_j2);
        }

        for (Point batisseur : batisseurs_en_cours) {
            for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {

                plateau.enleverJoueur(batisseur);
                plateau.ajouterJoueur(deplacement, joueur_en_cours);

                for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                    est_sur_toit = plateau.getTypeBatiments(deplacement) == TOIT;

                    if (!est_sur_toit) {
                        plateau.ameliorerBatiment(construction);
                    }

                    batisseurs_en_cours.set(batisseurs_en_cours.indexOf(batisseur), deplacement);

                    score_actuel = minimax(plateau, joueur_maximise, autre_joueur, profondeur_en_cours + 1, batisseurs_j1, batisseurs_j2);

                    batisseurs_en_cours.set(batisseurs_en_cours.indexOf(deplacement), batisseur);

                    if (!est_sur_toit) {
                        plateau.degraderBatiment(construction);
                    }

                    if ((est_joueur_maximise && score_actuel > meilleur_score) || (!est_joueur_maximise && score_actuel < meilleur_score)) {
                        meilleur_score = score_actuel;
                        if (profondeur_en_cours == 0) {
                            meilleur_coup = new Coups(batisseur, deplacement, construction);
                        }
                    }
                }
                plateau.enleverJoueur(deplacement);
                plateau.ajouterJoueur(batisseur, joueur_en_cours);
            }
        }
        return meilleur_score;
    }

    private float calculerHeuristiqueDifferenceDesHauteurs(Plateau plateau, ArrayList<Point> batisseurs) {
        float heuristique_joueur = 0;

        for (Point batisseur : batisseurs) {
            switch (plateau.getTypeBatiments(batisseur)) {
                case TOIT:
                    heuristique_joueur += 1000;
                    break;
                case ETAGE:
                    heuristique_joueur += 60;
                    break;
                case RDC:
                    heuristique_joueur += 40;
                    break;
                default:
                    break;
            }
        }

        return heuristique_joueur;
    }


    private float calculerHeuristiqueMobiliteVerticale(Plateau plateau, ArrayList<Point> batisseurs) {
        float heuristique_joueur = 0;
        int etage_batisseur;
        int etage_case_accessible;

        for (Point batisseur : batisseurs) {
            for (Point case_accessible : plateau.getCasesAccessibles(batisseur)) {

                etage_batisseur = plateau.getTypeBatiments(batisseur);
                etage_case_accessible = plateau.getTypeBatiments(case_accessible);

                if (etage_case_accessible > etage_batisseur) {
                    if (plateau.deplacementPossible(case_accessible, batisseur)) {
                        heuristique_joueur += 5;
                    } else {
                        heuristique_joueur -= ((etage_case_accessible - etage_batisseur) * 5);
                    }
                }
            }
        }
        return heuristique_joueur;
    }

    private float calculerHeuristiqueCaseCentrale(ArrayList<Point> batisseurs) {
        for (Point batisseur : batisseurs) {
            if (batisseur == CASE_CENTRALE) {
                return 10;
            }
        }
        return 0;
    }

    private float calculerHeuristiqueMenaceNiveau2(Plateau plateau, ArrayList<Point> batisseurs) {
        float heuristique_joueur = 0;

        for (Point batisseur : batisseurs) {
            if (plateau.getTypeBatiments(batisseur) == ETAGE) {
                for (Point case_voisine : plateau.getCasesAccessibles(batisseur)) {
                    if (plateau.getTypeBatiments(case_voisine) == TOIT) {
                        heuristique_joueur += 500;
                    }
                }
            }
        }
        return heuristique_joueur;
    }

    private float calculerHeuristique(Plateau plateau, int joueur_maximise, int strategie, ArrayList<Point> batisseurs_j1, ArrayList<Point> batisseurs_j2) {

        int poidsDifferenceDesHauteurs = 0;
        int poidsMobiliteVerticale = 0;
        int poidsCaseCentrale = 0;
        int poidsMenaceNiveau2 = 0;

        ArrayList<Point> batisseurs_maximise = joueur_maximise == JOUEUR1 ? batisseurs_j1 : batisseurs_j2;
        ArrayList<Point> batisseurs_autre_joueur = joueur_maximise != JOUEUR1 ? batisseurs_j1 : batisseurs_j2;

        // Pour tester d'autre stratégies, rajouter un case avec des poids différents.
        switch (strategie) {
            case 1:
                poidsDifferenceDesHauteurs = 10;
                poidsMobiliteVerticale = 10;
                poidsCaseCentrale = 15;
                poidsMenaceNiveau2 = 10;
                break;
        }

        float diff_hauteur = poidsDifferenceDesHauteurs * (calculerHeuristiqueDifferenceDesHauteurs(plateau, batisseurs_maximise) - calculerHeuristiqueDifferenceDesHauteurs(plateau, batisseurs_autre_joueur));
        float mobilite_verticale = poidsMobiliteVerticale * (calculerHeuristiqueMobiliteVerticale(plateau, batisseurs_maximise) - calculerHeuristiqueMobiliteVerticale(plateau, batisseurs_autre_joueur));
        float case_centrale = +poidsCaseCentrale * (calculerHeuristiqueCaseCentrale(batisseurs_maximise) - calculerHeuristiqueCaseCentrale(batisseurs_autre_joueur));
        float menace = poidsMenaceNiveau2 * (calculerHeuristiqueMenaceNiveau2(plateau, batisseurs_maximise) - calculerHeuristiqueMenaceNiveau2(plateau, batisseurs_autre_joueur));

        return diff_hauteur + mobilite_verticale + case_centrale + menace;
    }


    @Override
    public Point joue() {
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                minimax(new Plateau(plateau), jeu.getJoueurEnCours().getNum_joueur(), jeu.getJoueurEnCours().getNum_joueur(), 0, new ArrayList<>(jeu.getBatisseursJoueur(JOUEUR1)), new ArrayList<>(jeu.getBatisseursJoueur(JOUEUR2)));
                return meilleur_coup.batisseur;
            case DEPLACEMENT:
                return meilleur_coup.deplacement;
            case CONSTRUCTION:
                return meilleur_coup.construction;
            default:
                break;
        }
        return null;
    }


    private Point jouePlacement() {
        int colonnes = jeu.getPlateau().getColonnes();
        int lignes = jeu.getPlateau().getLignes();
        Point case_alea;
        do {
            case_alea = new Point(
                    random.nextInt(colonnes),
                    random.nextInt(lignes)
            );
        } while (!jeu.getPlateau().estLibre(case_alea));
        return case_alea;
    }
}
