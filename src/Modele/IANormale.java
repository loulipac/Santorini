package Modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static Modele.Constante.*;
import static Modele.Constante.CONSTRUCTION;

public class IANormale extends IAFacile {
    Jeu j;

    public IANormale(Jeu j) {
        super(j);
        this.j = j;
    }


    @Override
    public Point joue() {


        ArrayList<Point> sequenceGagnante = deplacementGagnant();

        if (sequenceGagnante != null) {
            //System.out.println("déplacement gagnant !");
            return switch (j.getSituation()) {
                case PLACEMENT -> super.jouePlacement();
                case SELECTION -> sequenceGagnante.get(0);
                case DEPLACEMENT -> sequenceGagnante.get(1);
                case CONSTRUCTION -> super.joueConstruction();
                default -> null;
            };
        }

        ArrayList<Point> sequenceContre = contre();

        if(sequenceContre != null){
            //System.out.println("contre !");
            return switch (j.getSituation()) {
                case PLACEMENT -> super.jouePlacement();
                case SELECTION -> sequenceContre.get(0);
                case DEPLACEMENT -> sequenceContre.get(1);
                case CONSTRUCTION -> sequenceContre.get(2);
                default -> null;
            };
        }else{
            //System.out.println("random");
            return super.joue();
        }
    }

    /**
     * regarde si le déplacement d'un batisseur ami peut faire gagner la partie
     * @return {le batisseur, le Point sur lequel se déplacer pour gagner}, null sinon.
     */

    public ArrayList<Point> deplacementGagnant(){

        ArrayList<Point> mes_batisseurs;
        ArrayList<Point> cases_accessibles;

        ArrayList<Point> sequence_gagnante = new ArrayList<>();

        mes_batisseurs = j.getBatisseurs(); //lui même ?

        for (Point batisseur_actuel : mes_batisseurs){
            cases_accessibles = j.getPlateau().getCasesAccessibles(batisseur_actuel);
            for(Point case_acc_actuel : cases_accessibles){
                if(j.getPlateau().getTypeBatiments(case_acc_actuel) == 3){ // 3 = étage 3 ?
                    sequence_gagnante.add(batisseur_actuel);
                    sequence_gagnante.add(case_acc_actuel);
                    return sequence_gagnante;
                }
            }
        }
        return null;
    }

    /**
     * Pour chaque batisseur B,
     *  Pour chaque cases accessible A par le batisseur B,
     *   Pour chaque cases constructible C par le batisseur B depuis la cases accesible A,
     *    Si la case C est d'étage trois
     *      Pour chaque cases voisines V de la case C (d'étage 3 si vous avez suivi..)
     *       Si la case V est occupé par un batisseur ennemi ET qu'il est perché sur un étage 2..
     *        CONTRE !
     * Sinon null
     * @return une séquence de trois coups, {Batisseur B,Case accessible A,Case constructible C}
     * qui contre un deplacement gagnant de l'adversaire au prochain tour. null sinon.
     */

    public ArrayList<Point> contre(){

        int adversaire = Jeu.getAutreJoueur(j.getJoueur_en_cours().getNum_joueur());

        ArrayList<Point> mes_batisseurs;
        ArrayList<Point> cases_accessibles;
        ArrayList<Point> cases_constructible;
        ArrayList<Point> cases_voisine;

        ArrayList<Point> contre = new ArrayList<>();

        mes_batisseurs = j.getBatisseurs();

        for (Point batisseur_actuel : mes_batisseurs){
            cases_accessibles = j.getPlateau().getCasesAccessibles(batisseur_actuel);

            for(Point case_acc_actuel : cases_accessibles){
                cases_constructible = j.getPlateau().getConstructionsPossible(case_acc_actuel);

                for(Point type_batiment_actuel : cases_constructible){

                    if(j.getPlateau().getTypeBatiments(type_batiment_actuel) == 3){
                        cases_voisine = j.getPlateau().getCasesVoisines(type_batiment_actuel);

                        for(Point case_voisine_actuel : cases_voisine){

                            if(j.getPlateau().estBatisseur(case_voisine_actuel,adversaire) && j.getPlateau().getTypeBatiments(case_voisine_actuel) == 2 ){
                                    // CONTRE !
                                contre.add(batisseur_actuel); // quel batisseur ?
                                contre.add(case_acc_actuel); // où bouger ?
                                contre.add(type_batiment_actuel); // où construire ?

                                return contre;

                            }
                        }
                    }
                }

            }
        }
        return null;

    }
}
