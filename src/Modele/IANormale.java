package Modele;

import java.awt.*;
import java.util.ArrayList;
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

        int moi = j.getJoueur_en_cours();

        ArrayList<Point> mesBatisseurs;
        ArrayList<Point> casesAccessibles;

        ArrayList<Point> sequenceGagnante = new ArrayList<>();

        mesBatisseurs = j.getBatisseurs(moi); //lui même ?

        for (Point batisseurActuel : mesBatisseurs){
            casesAccessibles = j.getPlateau().getCasesAccessibles(batisseurActuel);
            for(Point caseAccActuel : casesAccessibles){
                if(j.getPlateau().getTypeBatiments(caseAccActuel) == 3){ // 3 = étage 3 ?
                    sequenceGagnante.add(batisseurActuel);
                    sequenceGagnante.add(caseAccActuel);
                    return sequenceGagnante;
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

        int moi = j.getJoueur_en_cours();
        int adversaire = j.getAutreJoueur(j.getJoueur_en_cours());

        ArrayList<Point> mesBatisseurs;
        ArrayList<Point> casesAccessibles;
        ArrayList<Point> casesConstructible;
        ArrayList<Point> casesVoisine;

        ArrayList<Point> contre = new ArrayList<>();

        mesBatisseurs = j.getBatisseurs(moi);

        for (Point batisseurActuel : mesBatisseurs){
            casesAccessibles = j.getPlateau().getCasesAccessibles(batisseurActuel);

            for(Point caseAccActuel : casesAccessibles){
                casesConstructible = j.getPlateau().getConstructionsPossible(caseAccActuel);

                for(Point typeBatimentActuel : casesConstructible){

                    if(j.getPlateau().getTypeBatiments(typeBatimentActuel) == 3){
                        casesVoisine = j.getPlateau().getCasesVoisines(typeBatimentActuel);

                        for(Point caseVoisineActuel : casesVoisine){
                            if(j.getPlateau().estBatisseur(caseVoisineActuel,adversaire) && j.getPlateau().getTypeBatiments(caseVoisineActuel) == 2 ){
                                    // CONTRE !
                                contre.add(batisseurActuel); // quel batisseur ?
                                contre.add(caseAccActuel); // où bouger ?
                                contre.add(typeBatimentActuel); // où construire ?

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
