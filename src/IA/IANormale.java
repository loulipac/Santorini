package IA;

import Modele.Jeu;

import java.awt.*;
import java.util.ArrayList;

import static Utile.Constante.*;

/**
 * Classe IA Normale qui étend l'IA facile en faisant des coups aléatoires mais qui essaye de faire les coups gagnant
 * et d'empêcher l'adversaire de gaganer.
 */
public class IANormale extends IAFacile {
    private Coup meilleur_coup;
    private boolean coup_random;

    public IANormale(Jeu j, int joueur) {
        super(j,joueur);
        coup_random = false;
    }


    @Override
    public Point joue() {
        return switch (j.getSituation()) {
            case PLACEMENT -> super.jouePlacement();
            case SELECTION -> joueSelection();
            case DEPLACEMENT -> joueDeplacement();
            case CONSTRUCTION -> joueConstruction();
            default -> null;
        };
    }

    @Override
    public Point joueSelection(){
        if(deplacementGagnant() || contre()){
            coup_random = false;
            return meilleur_coup.getBatisseur();
        }
        else{
            coup_random = true;
            return super.joueSelection();
        }
    }

    @Override
    protected Point joueConstruction() {
        if(coup_random)
        {
            return super.joueConstruction();
        }
        else{
            return meilleur_coup.getConstruction();
        }
    }

    @Override
    protected Point joueDeplacement() {
        if(coup_random)
        {
            return super.joueConstruction();
        }
        else{
            return meilleur_coup.getDeplacement();
        }
    }

    /**
     * regarde si le déplacement d'un batisseur ami peut faire gagner la partie
     *
     * @return {le batisseur, le Point sur lequel se déplacer pour gagner}, null sinon.
     */
    public boolean deplacementGagnant() {

        ArrayList<Point> mesBatisseurs;
        ArrayList<Point> casesAccessibles;

        mesBatisseurs = j.getBatisseurs(); //lui même ?
        boolean deplacement_gagnant = false;

        for (Point batisseurActuel : mesBatisseurs) {
            casesAccessibles = j.getPlateau().getCasesAccessibles(batisseurActuel);
            for (Point caseAccActuel : casesAccessibles) {
                if (j.getPlateau().getTypeBatiments(caseAccActuel) == TOIT) {
                    meilleur_coup = new Coup(batisseurActuel,caseAccActuel,batisseurActuel);
                    deplacement_gagnant = true;
                    break;
                }
            }
        }
        return deplacement_gagnant;
    }

    /**
     * Pour chaque batisseur B,
     * Pour chaque cases accessible A par le batisseur B,
     * Pour chaque cases constructible C par le batisseur B depuis la cases accesible A,
     * Si la case C est d'étage trois
     * Pour chaque cases voisines V de la case C (d'étage 3 si vous avez suivi..)
     * Si la case V est occupé par un batisseur ennemi ET qu'il est perché sur un étage 2..
     * CONTRE !
     * Sinon null
     *
     * @return une séquence de trois coups, {Batisseur B,Case accessible A,Case constructible C}
     * qui contre un deplacement gagnant de l'adversaire au prochain tour. null sinon.
     */
    public boolean contre() {

        int adversaire = Jeu.getAutreJoueur(j.getJoueurEnCours().getNum_joueur());

        ArrayList<Point> mesBatisseurs;
        ArrayList<Point> casesAccessibles;
        ArrayList<Point> casesConstructible;
        ArrayList<Point> casesVoisine;
        boolean contre_possible = false;

        mesBatisseurs = j.getBatisseurs();

        for (Point batisseurActuel : mesBatisseurs) {
            casesAccessibles = j.getPlateau().getCasesAccessibles(batisseurActuel);

            for (Point caseAccActuel : casesAccessibles) {
                casesConstructible = j.getPlateau().getConstructionsPossible(caseAccActuel);

                for (Point typeBatimentActuel : casesConstructible) {

                    if (j.getPlateau().getTypeBatiments(typeBatimentActuel) == TOIT) {
                        casesVoisine = j.getPlateau().getCasesVoisines(typeBatimentActuel);

                        for (Point caseVoisineActuel : casesVoisine) {
                            if (j.getPlateau().estBatisseur(caseVoisineActuel, adversaire) && j.getPlateau().getTypeBatiments(caseVoisineActuel) == ETAGE) {
                                // CONTRE !
                                meilleur_coup = new Coup(batisseurActuel,caseAccActuel,typeBatimentActuel);
                                contre_possible = true;
                                break;
                            }
                        }
                    }
                }

            }
        }
        return contre_possible;
    }
}
