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
        case_claire = readImage("src/Ressources/cases/case_claire.png");
        case_fonce = readImage("src/Ressources/cases/case_fonce.png");
        coupole_etage_3 = readImage("src/Ressources/Etages/coupole_etage_3.png");
        etage_1 = readImage("src/Ressources/Etages/etage_1.png");
        etage_2 = readImage("src/Ressources/Etages/etage_2.png");
        etage_3 = readImage("src/Ressources/Etages/etage_3.png");
        batisseur_bleu = readImage("src/Ressources/batisseur/batisseur_bleu.png");
        batisseur_rouge = readImage("src/Ressources/batisseur/batisseur_rouge.png");
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

        FenetreMenu f = (FenetreMenu) SwingUtilities.getWindowAncestor(this);
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        drawable = (Graphics2D) g;

        // On reccupere quelques infos provenant de la partie JComponent

        largeur = f.getSize().width/plateau.getColonnes();
        hauteur = f.getSize().height/plateau.getLignes();


        int taille_case = Math.min(largeur,hauteur);

        // On efface tout
        drawable.clearRect(0, 0, largeur, hauteur);

        for (int l = 0; l < plateau.getLignes(); l++) {
            for (int c = 0; c < plateau.getColonnes(); c++) {

                if((l+c)%2 == 0){
                    drawable.drawImage(case_claire,c*taille_case,l*taille_case,taille_case,taille_case,null);
                }
                else{
                    drawable.drawImage(case_fonce,c*taille_case,l*taille_case,taille_case,taille_case,null);
                }

                switch(plateau.getTypeBatiments(l,c)){
                    case 1:
                        drawable.drawImage(etage_1,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case 2:
                        drawable.drawImage(etage_2,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case 4:
                        drawable.drawImage(etage_3,c*taille_case,l*taille_case,taille_case,taille_case,null);
                        break;
                    case 8:
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
        }
    }
}
