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


    public float minimax(Plateau plateau, int joueur_maximise, int joueur_en_cours, int profondeur) {
        ArrayList<Point> batisseurs_j1 = new ArrayList<Point>(plateau.rechercherBatisseurs(JOUEUR1));
        ArrayList<Point> batisseurs_j2 = new ArrayList<Point>(plateau.rechercherBatisseurs(JOUEUR2));

        boolean jeu_fini = (plateau.getCasesAccessibles(batisseurs_j1.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j1.get(1)).isEmpty())
                || (plateau.getCasesAccessibles(batisseurs_j2.get(0)).isEmpty() && plateau.getCasesAccessibles(batisseurs_j2.get(1)).isEmpty())
                || (plateau.getTypeBatiments(batisseurs_j1.get(0)) == 3)
                || (plateau.getTypeBatiments(batisseurs_j1.get(1)) == 3)
                || (plateau.getTypeBatiments(batisseurs_j2.get(0)) == 3)
                || (plateau.getTypeBatiments(batisseurs_j2.get(1)) == 3);

        if (jeu_fini || profondeur == 0) {
            if (jeu_fini) {
                System.out.println("Jeu fini");
            }
            return calculHeuristique(plateau, joueur_maximise, joueur_en_cours);
        }

        float meilleur_score = 0;

        if (joueur_en_cours == joueur_maximise) {
            meilleur_score = Float.NEGATIVE_INFINITY;
        } else {
            meilleur_score = Float.POSITIVE_INFINITY;
        }

        ArrayList<Point>batisseurs = new ArrayList<>(plateau.rechercherBatisseurs(joueur_en_cours));
        for (Point batisseur : batisseurs) {
            for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                    Coups coup_actuel = new Coups(batisseur, deplacement, construction);

                    Plateau nouveau_plateau = new Plateau(plateau);
                    nouveau_plateau.supprimerJoueur(batisseur);
                    nouveau_plateau.ajouterJoueur(deplacement, joueur_en_cours);
                    nouveau_plateau.ameliorerBatiment(construction);

                    float score_actuel = 0;
                    if (joueur_en_cours == JOUEUR1) {
                        score_actuel += minimax(nouveau_plateau, joueur_maximise, JOUEUR2, profondeur - 1);
                    } else {
                        score_actuel += minimax(nouveau_plateau, joueur_maximise, JOUEUR1, profondeur - 1);
                    }

                    if (joueur_en_cours == joueur_maximise) {
                        if (score_actuel > meilleur_score) {
                            meilleur_score = score_actuel;
                            meilleur_coup = coup_actuel;
                        }
                    } else {
                        if (score_actuel < meilleur_score) {
                            meilleur_score = score_actuel;
                            meilleur_coup = coup_actuel;
                        }
                    }
                }
            }
        }
        return meilleur_score;
    }

    private float calculHeuristiqueDifferenceDesHauteurs(Plateau plateau, int joueur_maximise, int joueur_en_cours) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;

        if (joueur_en_cours == joueur_maximise) {
            for (Point batisseur : plateau.rechercherBatisseurs(joueur_en_cours)) {
                if (plateau.estToit(batisseur)) {
                    heuristique_joueur += 1000;
                } else if (plateau.estEtage(batisseur)) {
                    heuristique_joueur += 60;
                } else if (plateau.estRDC(batisseur)) {
                    heuristique_joueur += 40;
                }
            }
        } else {
            if (joueur_en_cours == JOUEUR1) {
                for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR1)) {
                    if (plateau.estToit(batisseur)) {
                        heuristique_adversaire += 1000;
                    } else if (plateau.estEtage(batisseur)) {
                        heuristique_adversaire += 60;
                    } else if (plateau.estRDC(batisseur)) {
                        heuristique_adversaire += 40;
                    }
                }
            } else {
                for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR2)) {
                    if (plateau.estToit(batisseur)) {
                        heuristique_adversaire += 1000;
                    } else if (plateau.estEtage(batisseur)) {
                        heuristique_adversaire += 60;
                    } else if (plateau.estRDC(batisseur)) {
                        heuristique_adversaire += 40;
                    }
                }
            }
        }
        return heuristique_joueur - heuristique_adversaire;
    }


    private float calculHeuristiqueMobiliteVerticale(Plateau plateau, int joueur_maximise, int joueur_en_cours) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;

        if (joueur_en_cours == joueur_maximise) {
            for (Point batisseur : plateau.rechercherBatisseurs(joueur_en_cours)) {
                int etage_batisseur = plateau.getTypeBatiments(batisseur);
                for (Point case_voisine : plateau.getCasesVoisines(batisseur)) {
                    int etage_case_voisine = plateau.getTypeBatiments(case_voisine);
                    if (etage_case_voisine == etage_batisseur + 1) {
                        heuristique_joueur += 5;
                    } else if (etage_case_voisine == etage_batisseur + 2) {
                        heuristique_joueur -= 10;
                    } else if (etage_case_voisine == etage_batisseur + 3) {
                        heuristique_joueur -= 15;
                    } else {
                        heuristique_joueur -= 20;
                    }
                }
            }
        } else {
            if (joueur_en_cours == JOUEUR1) {
                for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR1)) {
                    int etage_batisseur = plateau.getTypeBatiments(batisseur);
                    for (Point case_voisine : plateau.getCasesVoisines(batisseur)) {
                        int etage_case_voisine = plateau.getTypeBatiments(case_voisine);
                        if (etage_case_voisine == etage_batisseur + 1) {
                            heuristique_adversaire += 5;
                        } else if (etage_case_voisine == etage_batisseur + 2) {
                            heuristique_adversaire -= 10;
                        } else if (etage_case_voisine == etage_batisseur + 3) {
                            heuristique_adversaire -= 15;
                        } else {
                            heuristique_adversaire -= 20;
                        }
                    }
                }
            } else {
                for (Point batisseur : plateau.rechercherBatisseurs(JOUEUR2)) {
                    int etage_batisseur = plateau.getTypeBatiments(batisseur);
                    for (Point case_voisine : plateau.getCasesVoisines(batisseur)) {
                        int etage_case_voisine = plateau.getTypeBatiments(case_voisine);
                        if (etage_case_voisine == etage_batisseur + 1) {
                            heuristique_adversaire += 5;
                        } else if (etage_case_voisine == etage_batisseur + 2) {
                            heuristique_adversaire -= 10;
                        } else if (etage_case_voisine == etage_batisseur + 3) {
                            heuristique_adversaire -= 15;
                        } else {
                            heuristique_adversaire -= 20;
                        }
                    }
                }
            }
        }
        return heuristique_joueur - heuristique_adversaire;
    }

    private float calculHeuristiqueCaseCentrale(Plateau plateau, int joueur_maximise, int joueur_en_cours) {
        for (Point batisseur : plateau.rechercherBatisseurs(joueur_en_cours)) {
            if (batisseur == CASE_CENTRALE) {
                return 10;
            }
        }
        return 0;
    }

    private float calculHeuristiqueMenaceNiveau2(Plateau plateau, int joueur_maximise, int joueur_en_cours) {
        float heuristique_joueur = 0;
        for (Point batisseur : plateau.rechercherBatisseurs(joueur_en_cours)) {
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

    private float calculHeuristique(Plateau plateau, int joueur_maximise, int joueur_en_cours) {
        float valeur_heuristique = calculHeuristiqueDifferenceDesHauteurs(plateau, joueur_maximise, joueur_en_cours)
                + calculHeuristiqueMobiliteVerticale(plateau, joueur_maximise, joueur_en_cours)
                + calculHeuristiqueCaseCentrale(plateau, joueur_maximise, joueur_en_cours)
                + calculHeuristiqueMenaceNiveau2(plateau, joueur_maximise, joueur_en_cours);
        return valeur_heuristique;
    }




    @Override
    public Point joue() {
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                minimax(jeu.getPlateau(), JOUEUR2, JOUEUR2, 1);
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
