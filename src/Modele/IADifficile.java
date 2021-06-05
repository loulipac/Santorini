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
    private Random random;
    private Coups meilleur_coup;

    public IADifficile(Jeu _jeu) {
        coups_liste = new ArrayList<>();
        jeu = _jeu;
        plateau = jeu.getPlateau();
        random = new Random();
    }

    private class Coups {
        Point batisseur;
        Point deplacement;
        Point construction;

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

    private class CoupHisto {
        Point batisseur;
        Point prevPos;
        Point construction;
        int prevHaut;

        public CoupHisto(Point batisseur, Point prevPos, Point construction, int prevHaut) {
            this.batisseur = batisseur;
            this.prevPos = prevPos;
            this.construction = construction;
            this.prevHaut = prevHaut;
        }
    }


    public float minimax(Plateau plateau, int joueur_maximise, int joueur_en_cours, int profondeur_en_cours, int profondeur_max) {
        ArrayList<Point> batisseurs_j1 = new ArrayList<Point>(plateau.rechercherBatisseurs(JOUEUR1));
        ArrayList<Point> batisseurs_j2 = new ArrayList<Point>(plateau.rechercherBatisseurs(JOUEUR2));

        boolean impossibleDeJouer_j1 = plateau.getCasesAccessibles(batisseurs_j1.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j1.get(1)).isEmpty();
        boolean impossibleDeJouer_j2 = plateau.getCasesAccessibles(batisseurs_j2.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j2.get(1)).isEmpty();
        boolean aGagne_j1 = (plateau.getTypeBatiments(batisseurs_j1.get(0)) == 3) || (plateau.getTypeBatiments(batisseurs_j1.get(1)) == 3);
        boolean aGagne_j2 = (plateau.getTypeBatiments(batisseurs_j2.get(0)) == 3) || (plateau.getTypeBatiments(batisseurs_j2.get(1)) == 3);

        boolean jeu_fini = impossibleDeJouer_j1 || impossibleDeJouer_j2 || aGagne_j1 || aGagne_j2;

        if (jeu_fini) {
            return Float.POSITIVE_INFINITY;
        }

        if (profondeur_en_cours == profondeur_max) {
            if (joueur_en_cours == JOUEUR1) {
                return calculerHeuristique(plateau, JOUEUR2);
            } else {
                return calculerHeuristique(plateau, JOUEUR1);
            }
        }

        float meilleur_score = 0;

        if (joueur_en_cours == joueur_maximise) {
            meilleur_score = Float.NEGATIVE_INFINITY;
        } else {
            meilleur_score = Float.POSITIVE_INFINITY;
        }

        ArrayList<Point> batisseurs = new ArrayList<>(plateau.rechercherBatisseurs(joueur_en_cours));
        for (Point batisseur : batisseurs) {
            for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                Plateau nouveau_plateau = new Plateau(plateau);
                nouveau_plateau.supprimerJoueur(batisseur);
                nouveau_plateau.ajouterJoueur(deplacement, joueur_en_cours);

                for (Point construction : nouveau_plateau.getConstructionsPossible(deplacement)) {
                    Coups coup_actuel = new Coups(batisseur, deplacement, construction);
                    // System.out.println("Coup à jouer pour le joueur " + joueur_en_cours + " => " + coup_actuel);

                    nouveau_plateau.ameliorerBatiment(construction);

                    float score_actuel = 0;
                    if (joueur_en_cours == JOUEUR1) {
                        score_actuel += minimax(nouveau_plateau, joueur_maximise, JOUEUR2, profondeur_en_cours + 1, profondeur_max);
                    } else {
                        score_actuel += minimax(nouveau_plateau, joueur_maximise, JOUEUR1, profondeur_en_cours + 1, profondeur_max);
                    }

                    nouveau_plateau.degraderBatiment(construction);

                    if (joueur_en_cours == joueur_maximise) {
                        if (score_actuel > meilleur_score && profondeur_en_cours == 0) {
                            meilleur_score = score_actuel;
                            meilleur_coup = coup_actuel;
                        }
                    } else {
                        if (score_actuel < meilleur_score && profondeur_en_cours == 0) {
                            meilleur_score = score_actuel;
                            meilleur_coup = coup_actuel;
                        }
                    }
                }
            }
        }
        return meilleur_score;
    }

    private float calculerHeuristiqueDifferenceDesHauteurs(Plateau plateau, int joueur_evalue) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;

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

        if (joueur_evalue == JOUEUR1) {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR2)) {
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
        } else {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR1)) {
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
        }

        return heuristique_joueur - Math.abs(heuristique_adversaire);
    }


    private float calculerHeuristiqueMobiliteVerticale(Plateau plateau, int joueur_evalue) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;

        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            int etage_batisseur = plateau.getTypeBatiments(batisseur);
            for (Point case_accessible : plateau.getCasesAccessibles(batisseur)) {
                int etage_case_accessible = plateau.getTypeBatiments(case_accessible);
                if (etage_case_accessible == etage_batisseur + 1) {
                    heuristique_joueur += 5;
                } else if (etage_case_accessible == etage_batisseur + 2) {
                    heuristique_joueur -= 10;
                } else if (etage_case_accessible == etage_batisseur + 3) {
                    heuristique_joueur -= 15;
                } else if (etage_case_accessible == etage_batisseur + 4) {
                    heuristique_joueur -= 20;
                }
            }
        }

        if (joueur_evalue == JOUEUR1) {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR2)) {
                int etage_batisseur = plateau.getTypeBatiments(batisseur);
                for (Point case_accessible : plateau.getCasesAccessibles(batisseur)) {
                    int etage_case_accessible = plateau.getTypeBatiments(case_accessible);
                    if (etage_case_accessible == etage_batisseur + 1) {
                        heuristique_adversaire += 5;
                    } else if (etage_case_accessible == etage_batisseur + 2) {
                        heuristique_adversaire -= 10;
                    } else if (etage_case_accessible == etage_batisseur + 3) {
                        heuristique_adversaire -= 15;
                    } else if (etage_case_accessible == etage_batisseur + 4) {
                        heuristique_adversaire -= 20;
                    }
                }
            }
        } else {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR1)) {
                int etage_batisseur = plateau.getTypeBatiments(batisseur);
                for (Point case_accessible : plateau.getCasesAccessibles(batisseur)) {
                    int etage_case_accessible = plateau.getTypeBatiments(case_accessible);
                    if (etage_case_accessible == etage_batisseur + 1) {
                        heuristique_adversaire += 5;
                    } else if (etage_case_accessible == etage_batisseur + 2) {
                        heuristique_adversaire -= 10;
                    } else if (etage_case_accessible == etage_batisseur + 3) {
                        heuristique_adversaire -= 15;
                    } else if (etage_case_accessible == etage_batisseur + 4) {
                        heuristique_adversaire -= 20;
                    }
                }
            }
        }
        return heuristique_joueur - Math.abs(heuristique_adversaire);
    }

    private float calculerHeuristiqueCaseCentrale(Plateau plateau, int joueur_evalue) {
        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            if (batisseur == CASE_CENTRALE) {
                return 10;
            }
        }

        if (joueur_evalue == JOUEUR1) {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR2)) {
                if (batisseur == CASE_CENTRALE) {
                    return -10;
                }
            }
        } else {
            for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR1)) {
                if (batisseur == CASE_CENTRALE) {
                    return -10;
                }
            }
        }

        return 0;
    }

    private float calculerHeuristiqueMenaceNiveau2(Plateau plateau, int joueur_evalue) {
        String joueur = joueur_evalue == JOUEUR1 ? "1": "2";
        System.out.println();
        System.out.println();
        System.out.println("le joueur"+joueur+" de valeur "+joueur_evalue+" est rentre dans la fonction calculerHeuristiqueMenaceNiveau2");
        float heuristique_joueur = 0;
        for (Point batisseur : plateau.rechercherBatisseurs(joueur_evalue)) {
            if (plateau.getTypeBatiments(batisseur) == 2) {
                System.out.println("le batisseurs du joueur est sur une case de hauteur 2, à la position :"+batisseur.x+", "+batisseur.y);
                for (Point case_voisine : plateau.getCasesAccessibles(batisseur)) {
                    if (plateau.getTypeBatiments(case_voisine) == 3) {
                        System.out.println("il peut atteindre une case de hauteur 3");
                        heuristique_joueur += 500;
                    }
                }
            }
        }
        return heuristique_joueur;
    }

    private float calculerHeuristique(Plateau plateau, int joueur_evalue) {
        float valeur_heuristique =
                calculerHeuristiqueDifferenceDesHauteurs(plateau, joueur_evalue)
                + calculerHeuristiqueMobiliteVerticale(plateau, joueur_evalue)
                + calculerHeuristiqueCaseCentrale(plateau, joueur_evalue)
                + calculerHeuristiqueMenaceNiveau2(plateau, joueur_evalue);
        return valeur_heuristique;
    }


    @Override
    public Point joue() {
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                minimax(jeu.getPlateau(), JOUEUR2, JOUEUR2, 0, 3);
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
        Point case_alea = null;
        do {
            case_alea = new Point(
                    random.nextInt(colonnes),
                    random.nextInt(lignes)
            );
        } while (!jeu.getPlateau().estLibre(case_alea));
        return case_alea;
    }
}
