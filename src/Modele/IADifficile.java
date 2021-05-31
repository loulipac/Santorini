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

    public float alphabeta(JeuSimulation jeu, int depth, float a, float b, int maximizingPlayer) {
        float value;
        if (depth == 0 || jeu.estJeufini()) {
            return heuristique(jeu);
        }

        ArrayList<Point> batisseur_copy = null;
        batisseur_copy = new ArrayList<>(jeu.getBatisseurs(maximizingPlayer));
        if (maximizingPlayer == Constante.JOUEUR2) { // alpha
            value = Float.NEGATIVE_INFINITY;
            for (Point batisseur : batisseur_copy) {
                for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                    for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                        JeuSimulation jeu_simulation = new JeuSimulation(jeu.getPlateau(), jeu.j1, jeu.j2);

                        jouer(jeu_simulation, new Coups(batisseur, deplacement, construction), maximizingPlayer);
                        value = Math.max(value, alphabeta(jeu_simulation, depth - 1, a, b, (maximizingPlayer % 16) + 8));
                        jeu_simulation.reset();

                        if (value >= a) {
                            System.out.println(value);
                            coups_liste.clear();
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                            a = value;
                        } else if (value == a) {
                            //coups_liste.add(new Coups(batisseur, deplacement, construction));
                        }
                        //if (b <= value) return value;
                    }
                }
            }
            return value;
        } else {
            value = Float.POSITIVE_INFINITY;
            for (Point batisseur : batisseur_copy) {
                for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                    for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                        JeuSimulation jeu_simulation = new JeuSimulation(jeu.getPlateau(), jeu.j1, jeu.j2);

                        jouer(jeu_simulation, new Coups(batisseur, deplacement, construction), maximizingPlayer);
                        value = Math.min(value, alphabeta(jeu_simulation, depth - 1, a, b, (maximizingPlayer % 16) + 8));
                        jeu_simulation.reset();

                        if (value <= b) {
                            coups_liste.clear();
                            coups_liste.add(new Coups(batisseur, deplacement, construction));
                            b = value;
                        } else if (value == b) {
                            //coups_liste.add(new Coups(batisseur, deplacement, construction));
                        }
                        //if (a >= value) return value;
                    }
                }
            }
            return value;
        }
    }


    /**
     * Joue un tour complet
     * TODO: joueur en cours bizarre
     */
    private void jouer(JeuSimulation jeu_simulation, Coups c, int num_joueur) {
        // Selection
        jeu_simulation.setJoueur_en_cours(num_joueur);
        jeu_simulation.jouer(c.batisseur, SELECTION);
        // Déplacement
        jeu_simulation.jouer(c.batisseur, DEPLACEMENT);
        // Construction
        jeu_simulation.jouer(c.batisseur, CONSTRUCTION);
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


    /**
     * Déjoue un tour complet (soit 2 undo)
     */
    private void dejouer(Jeu _jeu, int num_joueur, int index_batisseur, CoupHisto c) {
        /*_jeu.getPlateau().setFloorBis(c.construction, c.prevHaut);
        _jeu.getPlateau().removePlayer(c.batisseur);
        _jeu.getPlateau().ajouterJoueur(c.prevPos, num_joueur);
        _jeu.updateBatisseur(index_batisseur, c.prevPos, num_joueur);*/
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Menace du niveau 2 :
     * Le nombre de cases adjacentes au niveau + 1 où le bâtisseur se trouve au niveau 2, ce qui signifie une victoire imminente.
     * La valeur sera positive pour le joueur actuel et négative pour l’adversaire.
     */
    private float menaceDuNiveau2(JeuSimulation _jeu) {
        float heuristique = 0;
        ArrayList<JeuSimulation.JoueurLambda> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (JeuSimulation.JoueurLambda j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                ArrayList<Point> cases = _jeu.getPlateau().getCasesAccessibles(batisseur);
                int etageBatisseur = _jeu.getPlateau().getTypeBatiments(batisseur);

                for (Point caseAcc : cases) {
                    if ((_jeu.getPlateau().getTypeBatiments(caseAcc) == TOIT) && (etageBatisseur == ETAGE)) {
                        heuristique += index;
                    }
                }
            }
        }
        return clamp(heuristique, -1, 1);
    }

    /**
     * Combinaison des hauteurs des bâtisseurs :
     * Somme des niveaux des bâtisseurs du joueur moins la somme des niveaux des bâtisseurs de l’adversaire.
     */
    private float combinaisonHauteur(JeuSimulation _jeu) {
        float heuristique = 0;
        ArrayList<JeuSimulation.JoueurLambda> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (JeuSimulation.JoueurLambda j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                heuristique += index * _jeu.getPlateau().getTypeBatiments(batisseur);
            }
        }
        return heuristique;
    }


    /**
     * Contrôle de la  case centrale :
     * Si le joueur actuel possède un bâtisseur sur la case centrale.
     */
    private float caseCentrale(JeuSimulation _jeu) {
        ArrayList<JeuSimulation.JoueurLambda> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (JeuSimulation.JoueurLambda j : js) {
            for (Point batisseur : j.getBatisseurs()) {
                if (batisseur == CASE_CENTRALE) {
                    return (js.indexOf(j) == 0) ? 1 : -1;
                }
            }
        }
        return 0;
    }

    /**
     * Mobilité verticale du joueur :
     * Nombre de cases vers lesquelles le joueur peut se déplacer avec les cases au niveau du joueur + 1
     * qui sont plus désirables et les cases étant au niveau + 2 et au niveau + 3 qui sont indésirables.
     * La mobilité de l’adversaire est soustraite à celle du joueur actuel
     */
    private float mobiliteVerticale(JeuSimulation _jeu) {
        float heuristique = 0;
        ArrayList<JeuSimulation.JoueurLambda> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for (JeuSimulation.JoueurLambda j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                ArrayList<Point> _cases = _jeu.getPlateau().getCasesAccessibles(batisseur);
                int etageBatisseur = _jeu.getPlateau().getTypeBatiments(batisseur);
                for (Point _case : _cases) {
                    int etageCase = _jeu.getPlateau().getTypeBatiments(_case);
                    if (etageCase == etageBatisseur + 1) {
                        heuristique += index;
                    }
                }
            }
        }
        return heuristique;
    }

    private float valeurVerticale(JeuSimulation _jeu) {
        float heuristique = 0;
        ArrayList<JeuSimulation.JoueurLambda> js = new ArrayList<>();
        js.add(_jeu.j1);
        js.add(_jeu.j2);
        for(JeuSimulation.JoueurLambda j : js) {
            int index = (js.indexOf(j) == 0) ? 1 : -1;
            for (Point batisseur : j.getBatisseurs()) {
                ArrayList<Point> _cases = _jeu.getPlateau().getCasesVoisines(batisseur);
                int etageBatisseur = _jeu.getPlateau().getTypeBatiments(batisseur);
//                System.out.println("====UN BATISSEUR====");
                for (Point _case : _cases) {
//                    System.out.println(_case);
                    heuristique += POIDS_CASES[_jeu.getPlateau().getTypeBatiments(_case)][etageBatisseur] * index;
                }
            }
        }
        return heuristique;
    }

    private float heuristique(JeuSimulation _jeu) {
        return valeurVerticale(_jeu);
//        return POIDS_BASE * caseCentrale(_jeu) +
//                POIDS_BASE * combinaisonHauteur(_jeu) +
//                10000000 * menaceDuNiveau2(_jeu) +
//                50 * mobiliteVerticale(_jeu) +
//                valeurVerticale(_jeu);
    }


    public float minimax(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur, int profondeur) {
        if (jeu.estJeufini() || profondeur == 0) {
            return calculHeuristique(jeu, joueur);
        }

        float meilleur_score = 0;
        if (jeu.joueur_en_cours == joueur) {
            meilleur_score = Float.NEGATIVE_INFINITY;
        } else {
            meilleur_score = Float.POSITIVE_INFINITY;
        }

        ArrayList<Point>batisseurs = new ArrayList<>(jeu.getBatisseurs(jeu.joueur_en_cours.getNum_joueur()));
        for (Point batisseur : batisseurs) {
            for (Point deplacement : plateau.getCasesAccessibles(batisseur)) {
                for (Point construction : plateau.getConstructionsPossible(deplacement)) {

                    System.out.println("Joueur en cours = " + jeu.joueur_en_cours.getNum_joueur());
                    Coups coup_actuel = new Coups(batisseur, deplacement, construction);
                    System.out.println("Coup actuel => " + coup_actuel);
                    jeu.jouer(coup_actuel.batisseur, SELECTION);
                    jeu.jouer(coup_actuel.deplacement, DEPLACEMENT);
                    jeu.jouer(coup_actuel.construction, CONSTRUCTION);

                    if (joueur == jeu.j1) {
                        jeu.setJoueur_en_cours(jeu.j2.getNum_joueur());
                    } else {
                        jeu.setJoueur_en_cours(jeu.j1.getNum_joueur());
                    }

                    float score_actuel = minimax(jeu, joueur, profondeur - 1);
                    jeu.reset();

                    if (jeu.joueur_en_cours == joueur) {
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

    private float calculHeuristiqueDifferenceDesHauteurs(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;
        Plateau plateau = jeu.getPlateau();

        if (jeu.joueur_en_cours == joueur) {
            for (Point batisseur : joueur.getBatisseurs()) {
                if (plateau.estToit(batisseur)) {
                    heuristique_joueur += 1000;
                } else if (plateau.estEtage(batisseur)) {
                    heuristique_joueur += 60;
                } else if (plateau.estRDC(batisseur)) {
                    heuristique_joueur += 40;
                }
            }
        } else {
            if (jeu.joueur_en_cours == jeu.j1) {
                for (Point batisseur : jeu.j1.getBatisseurs()) {
                    if (plateau.estToit(batisseur)) {
                        heuristique_adversaire += 1000;
                    } else if (plateau.estEtage(batisseur)) {
                        heuristique_adversaire += 60;
                    } else if (plateau.estRDC(batisseur)) {
                        heuristique_adversaire += 40;
                    }
                }
            } else {
                for (Point batisseur : jeu.j2.getBatisseurs()) {
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


    private float calculHeuristiqueMobiliteVerticale(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur) {
        float heuristique_joueur = 0;
        float heuristique_adversaire = 0;
        Plateau plateau = jeu.getPlateau();

        if (jeu.joueur_en_cours == joueur) {
            for (Point batisseur : joueur.getBatisseurs()) {
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
            if (jeu.joueur_en_cours == jeu.j1) {
                for (Point batisseur : jeu.j1.getBatisseurs()) {
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
                for (Point batisseur : jeu.j2.getBatisseurs()) {
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

    private float calculHeuristiqueCaseCentrale(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur) {
        for (Point batisseur : joueur.getBatisseurs()) {
            if (batisseur == CASE_CENTRALE) {
                return 10;
            }
        }
        return 0;
    }

    private float calculHeuristiqueMenaceNiveau2(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur) {
        Plateau plateau = jeu.getPlateau();
        for (Point batisseur : joueur.getBatisseurs()) {
            if (!plateau.estToit(batisseur) && plateau.estEtage(batisseur)) {
                for (Point case_voisine : plateau.getCasesAccessibles(batisseur)) {
                    if (!plateau.estCoupole(case_voisine) && plateau.estToit(case_voisine)) {
                        return 500;
                    }
                }
            }
        }
        return 0;
    }

    public float calculHeuristiquePasPerdre(JeuSimulation _jeu) {
        float valeur=0;

        for (Point batisseur : _jeu.j1.getBatisseurs()) {
            if(plateau.estToit(batisseur)){
                valeur = 1000;
            }
            else if(plateau.estEtage(batisseur)){
                for (Point case_voisine : plateau.getCasesVoisines(batisseur)) {
                    if(plateau.estToit(case_voisine)){
                        return 800;
                }

                }

            }
        }
        return valeur;
    }

    private float calculHeuristique(JeuSimulation jeu, JeuSimulation.JoueurLambda joueur) {
        System.out.println("Entree calcul heuristique");
        float valeur_heuristique = calculHeuristiqueDifferenceDesHauteurs(jeu, joueur)
                + calculHeuristiqueMobiliteVerticale(jeu, joueur)
                + calculHeuristiqueCaseCentrale(jeu, joueur)
                + calculHeuristiqueMenaceNiveau2(jeu, joueur);
        System.out.println("heuristique = " + valeur_heuristique);
        return valeur_heuristique;
    }




    @Override
    public Point joue() {
        switch (jeu.getSituation()) {
            case PLACEMENT:
                return jouePlacement();
            case SELECTION:
                JeuSimulation jeu_simulation = new JeuSimulation(jeu.getPlateau(), jeu.j1, jeu.j2);
                jeu_simulation.setJoueur_en_cours(jeu.getJoueur_en_cours());
                System.out.println("Joueur en cours = " + jeu.getJoueur_en_cours());
                minimax(jeu_simulation, jeu_simulation.j2, 2);
                return meilleur_coup.batisseur;
                //alphabeta(jeu_simulation, 3, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, jeu.getJoueur_en_cours());
                //coups = coups_liste.get(random.nextInt(coups_liste.size()));
                //return coups.batisseur;
            case DEPLACEMENT:
                return meilleur_coup.deplacement;
                //return coups.deplacement;
            case CONSTRUCTION:
                return meilleur_coup.construction;
                //return coups.construction;
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
