package Modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Modele.Constante.*;

public class IADifficile implements IA {

    private Jeu jeu;
    private ArrayList<Coups> coups_liste;
    private Coups coups;
    private Plateau plateau;
    private final Random random;
    private Coups meilleur_coup;
    private final int profondeur_max = 3;

    public IADifficile(Jeu _jeu) {
        coups_liste = new ArrayList<>();
        jeu = _jeu;
        plateau = jeu.getPlateau();
        random = new Random();
    }

    private class Coups {
        Point batisseur, deplacement, construction;

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


    public float minimax(Plateau plateau, int joueur_maximise, int joueur_en_cours, int profondeur_en_cours) {

        float score_actuel,meilleur_score;
        int autre_joueur = Jeu.getAutreJoueur(joueur_en_cours);

        ArrayList<Point> batisseurs_j1 = plateau.rechercherBatisseurs(JOUEUR1);
        ArrayList<Point> batisseurs_j2 = plateau.rechercherBatisseurs(JOUEUR2);

        boolean impossibleDeJouer_j1 = plateau.getCasesAccessibles(batisseurs_j1.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j1.get(1)).isEmpty();
        boolean impossibleDeJouer_j2 = plateau.getCasesAccessibles(batisseurs_j2.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j2.get(1)).isEmpty();
        boolean aGagne_j1 = (plateau.getTypeBatiments(batisseurs_j1.get(0)) == 3) || (plateau.getTypeBatiments(batisseurs_j1.get(1)) == 3);
        boolean aGagne_j2 = (plateau.getTypeBatiments(batisseurs_j2.get(0)) == 3) || (plateau.getTypeBatiments(batisseurs_j2.get(1)) == 3);

        boolean jeu_fini = impossibleDeJouer_j1 || impossibleDeJouer_j2 || aGagne_j1 || aGagne_j2;

        boolean est_joueur_maximise = joueur_en_cours == joueur_maximise;

        meilleur_score = est_joueur_maximise ? Float.MIN_VALUE : Float.MAX_VALUE;
        if (jeu_fini) {
            System.out.println("Jeu est fini");
            return Float.MAX_VALUE;
        }

        if (profondeur_en_cours == profondeur_max) {
            return calculerHeuristique(plateau, autre_joueur, 1);
        }

        ArrayList<Point> batisseurs = new ArrayList<>(plateau.rechercherBatisseurs(joueur_en_cours));

        for (Point batisseur : batisseurs) {
            for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                Plateau nouveau_plateau = new Plateau(plateau);
                nouveau_plateau.supprimerJoueur(batisseur);
                nouveau_plateau.ajouterJoueur(deplacement, joueur_en_cours);

                for (Point construction : nouveau_plateau.getConstructionsPossible(deplacement)) {
                    Coups coup_actuel = new Coups(batisseur, deplacement, construction);

                    nouveau_plateau.ameliorerBatiment(construction);

                    score_actuel = minimax(nouveau_plateau, joueur_maximise, autre_joueur, profondeur_en_cours + 1);

                    nouveau_plateau.degraderBatiment(construction);

                    if ((profondeur_en_cours == 0) && ((est_joueur_maximise && score_actuel > meilleur_score) || (!est_joueur_maximise && score_actuel < meilleur_score)) ) {
                            meilleur_score = score_actuel;
                            meilleur_coup = coup_actuel;
                    }
                }
            }
        }
        return meilleur_score;
    }

    private float calculerHeuristiqueDifferenceDesHauteurs(Plateau plateau, int joueur_evalue) {
        float heuristique_joueur = 0;

        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            switch (plateau.getTypeBatiments(batisseur)) {
                case 3:
                    heuristique_joueur += 1000;
                    break;
                case 2:
                    heuristique_joueur += 60;
                    break;
                case 1:
                    heuristique_joueur += 40;
                    break;
                default:
                    break;
            }
        }

        return heuristique_joueur;
    }


    private float calculerHeuristiqueMobiliteVerticale(Plateau plateau, int joueur_evalue) {
        float heuristique_joueur = 0;
        int etage_batisseur, etage_case_accessible;

        for (Point batisseur : plateau.rechercherBatisseurs(Jeu.getAutreJoueur(joueur_evalue))) {
            for (Point case_accessible : plateau.getCasesAccessibles(batisseur)) {

                etage_batisseur = plateau.getTypeBatiments(batisseur);
                etage_case_accessible = plateau.getTypeBatiments(case_accessible);

                if (etage_case_accessible > etage_batisseur) {
                    if(plateau.deplacementPossible(case_accessible, batisseur)){
                        heuristique_joueur += 5;
                    }
                    else {
                        heuristique_joueur -= ((etage_case_accessible - etage_batisseur) * 5);
                    }
                }
            }
        }
        return heuristique_joueur;
    }

    private float calculerHeuristiqueCaseCentrale(Plateau plateau, int joueur_evalue) {
        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            if (batisseur == CASE_CENTRALE) {
                return 10;
            }
        }
        return 0;
    }

    private float calculerHeuristiqueMenaceNiveau2(Plateau plateau, int joueur_evalue) {
        float heuristique_joueur = 0;

        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            if (plateau.getTypeBatiments(batisseur) == 2) {
                for (Point case_voisine : plateau.getCasesAccessibles(batisseur)) {
                    if (plateau.getTypeBatiments(case_voisine) == 3) {
                        heuristique_joueur += 500;
                    }
                }
            }
        }
        return heuristique_joueur;
    }

    private float calculerHeuristique(Plateau plateau, int joueur_evalue, int strategie) {

        int poidsDifferenceDesHauteurs, poidsMobiliteVerticale,poidsCaseCentrale ,poidsMenaceNiveau2;
        poidsDifferenceDesHauteurs = poidsMobiliteVerticale = poidsCaseCentrale = poidsMenaceNiveau2 = 0;

        int autre_joueur = Jeu.getAutreJoueur(joueur_evalue);

        switch (strategie) {
            case 1:
                poidsDifferenceDesHauteurs = 10;
                poidsMobiliteVerticale = 10;
                poidsCaseCentrale = 15;
                poidsMenaceNiveau2 = 10;
                break;
        }

        float diff_hauteur = poidsDifferenceDesHauteurs * (calculerHeuristiqueDifferenceDesHauteurs(plateau, joueur_evalue) - calculerHeuristiqueDifferenceDesHauteurs(plateau, autre_joueur));
        float mobilite_verticale =  poidsMobiliteVerticale * (calculerHeuristiqueMobiliteVerticale(plateau, joueur_evalue) - calculerHeuristiqueMobiliteVerticale(plateau, autre_joueur));
        float case_centrale = + poidsCaseCentrale * (calculerHeuristiqueCaseCentrale(plateau, joueur_evalue) - calculerHeuristiqueCaseCentrale(plateau, autre_joueur));
        float menace =  poidsMenaceNiveau2 * (calculerHeuristiqueMenaceNiveau2(plateau, joueur_evalue));

        System.out.println();
        System.out.println();
        System.out.println("Print heuristique");
        System.out.println(plateau);
        System.out.println("diff hauteur :"+diff_hauteur);
        System.out.println("mobilite_verticale :"+mobilite_verticale);
        System.out.println("case_centrale :"+case_centrale);
        System.out.println("menace :"+menace);

        return diff_hauteur+mobilite_verticale+case_centrale+menace;
    }


    @Override
    public Point joue() {
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                minimax(plateau, jeu.getJoueur_en_cours(), jeu.getJoueur_en_cours(), 0);
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
