package Vue;

import Modele.Plateau;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class JeuGraphique extends JComponent {
    private Plateau plateau;
    private Jeu jeu;
    private Graphics2D drawable;
    private int largeur;
    private int hauteur;
    private int taille_case;
    private Image case_claire, case_fonce, coupole_etage_3, etage_1, etage_2, etage_3, batisseur_bleu, batisseur_rouge;

    public JeuGraphique(Jeu j) {
        this.jeu = j;
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

        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        drawable = (Graphics2D) g;

        largeur = f.getSize().width / jeu.getPlateau().getColonnes();
        hauteur = f.getSize().height / jeu.getPlateau().getLignes();

        taille_case = Math.min(largeur / 2, hauteur / 2);

        // On efface tout
        drawable.clearRect(0, 0, taille_case * jeu.getPlateau().getColonnes(), taille_case * jeu.getPlateau().getLignes());

        for (int l = 0; l < jeu.getPlateau().getLignes(); l++) {
            for (int c = 0; c < jeu.getPlateau().getColonnes(); c++) {
                if ((l + c) % 2 == 0) {
                    drawable.drawImage(case_claire, c * taille_case, l * taille_case, taille_case, taille_case, null);
                } else {
                    drawable.drawImage(case_fonce, c * taille_case, l * taille_case, taille_case, taille_case, null);
                }

                switch (jeu.getPlateau().getTypeBatiments(l, c)) {
                    case Plateau.RDC:
                        drawable.drawImage(etage_1, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                    case Plateau.ETAGE:
                        drawable.drawImage(etage_2, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                    case Plateau.TOIT:
                        drawable.drawImage(etage_3, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                    case Plateau.COUPOLE:
                        drawable.drawImage(coupole_etage_3, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                }
                switch (jeu.getPlateau().getTypeBatisseurs(l, c)) {
                    case Jeu.JOUEUR1:
                        drawable.drawImage(batisseur_bleu, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                    case Jeu.JOUEUR2:
                        drawable.drawImage(batisseur_rouge, c * taille_case, l * taille_case, taille_case, taille_case, null);
                        break;
                }
            }
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getTailleCase() {
        return taille_case;
    }

    public void setTailleCase(int taille_case) {
        this.taille_case = taille_case;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}
