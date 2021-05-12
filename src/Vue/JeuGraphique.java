package Vue;

import Modele.Plateau;
import Modele.Jeu;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class JeuGraphique extends JComponent {
    private Plateau plateau;
    private Graphics2D drawable;
    private int largeur,hauteur;
    private Image case_claire,case_fonce,coupole_etage_3,etage_1,etage_2,etage_3,batisseur_bleu,batisseur_rouge;

    public JeuGraphique(Plateau p) {
        this.plateau = p;
        case_claire = readImage("case_claire.png");
        case_fonce = readImage("case_fonce.png");
        coupole_etage_3 = readImage("coupole_etage_3.png");
        etage_1 = readImage("etage_1.png");
        etage_2 = readImage("etage_2.png");
        etage_3 = readImage("etage_3.png");
        batisseur_bleu = readImage("batisseur_bleu.png");
        batisseur_rouge = readImage("batisseur_rouge.png");
    }

    public Image readImage(String _name) {
        try {
            return ImageIO.read(new FileInputStream(_name));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        drawable = (Graphics2D) g;

        // On reccupere quelques infos provenant de la partie JComponent
        largeur = getSize().width/plateau.getColonnes();
        hauteur = getSize().height/plateau.getLignes();

        int taille_case = Math.min(largeur,hauteur);

        // On efface tout
        drawable.clearRect(0, 0, largeur, hauteur);

        /*for (int l = 0; l < plateau.getLignes(); l++) {
            for (int c = 0; c < plateau.getColonnes(); c++) {

                if((l+c)%2 == 0){
                    drawable.drawImage(case_claire,c*taille_case,l*taille_case,taille_case,taille_case,null);
                }
                else{
                    drawable.drawImage(case_fonce,c*taille_case,l*taille_case,taille_case,taille_case,null);
                }

                switch(plateau.getTypeBatiments(l,c)){
                    case plateau.RDC:
                        drawable.drawImage(etage_1,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case plateau.ETAGE:
                        drawable.drawImage(etage_2,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case plateau.TOIT:
                        drawable.drawImage(etage_3,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case plateau.COUPOLE:
                        drawable.drawImage(coupole_etage_3,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                }
                switch (plateau.getTypeBatisseurs(l,c)){
                    case Jeu.JOUEUR1:
                        drawable.drawImage(batisseur_bleu,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case Jeu.JOUEUR2:
                        drawable.drawImage(batisseur_rouge,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                }
            }
        }*/
    }
}
