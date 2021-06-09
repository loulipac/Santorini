package Utile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static Utile.Constante.*;

/**
 * Classe Utile gérant la lecture d'image et le fait des dessiner des images en fond sur des JPanels.
 */
public class Utile {
    private static BufferedImage bg;
    private static BufferedImage column;
    private static BufferedImage parchemin;
    private static BufferedImage parcheminVictoire;
    private static BufferedImage arrierePlanPlateau;
    private static BufferedImage colonneFin;
    private static BufferedImage colonneBleu;
    private static BufferedImage colonneRouge;

    /**
     * Utile ne dois jamais être instancié et seulement utilisé par ses fonctions statiques.
     */
    private Utile() {
    }

    /**
     * Permet de redimensionner une taille d'image selon une taille max tout en gardant le bon ratio de l'image.
     *
     * @param tailleImage taille de l'image
     * @param tailleMax   taille max atteignable
     * @return la Dimension redimensionnée selon la taille max
     */
    public static Dimension conserverRatio(Dimension tailleImage, Dimension tailleMax) {
        double largeurRatio = tailleMax.getWidth() / tailleImage.getWidth();
        double hauteurRatio = tailleMax.getHeight() / tailleImage.getHeight();
        double ratio = Math.min(largeurRatio, hauteurRatio);

        return new Dimension((int) (tailleImage.width * ratio),
                (int) (tailleImage.height * ratio));
    }

    /**
     * Dessine le dessin d'arrière plan
     *
     * @param tailleFenetre taille de la fenetre
     */
    public static void dessineBackground(Graphics g, Dimension tailleFenetre, ImageObserver o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            if (bg == null) bg = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/base.png"));

            Dimension img_dim = new Dimension(bg.getWidth(), bg.getHeight());
            Dimension taille_max = new Dimension((int) (tailleFenetre.width * 0.7), (int) (tailleFenetre.height * 0.7));
            Dimension taille_redimensionnee = Utile.conserverRatio(img_dim, taille_max);

            g2d.drawImage(
                    bg,
                    tailleFenetre.width / 2 - ((int) (taille_redimensionnee.getWidth() / 2)),
                    tailleFenetre.height / 2 - ((int) (taille_redimensionnee.getHeight() / 2)),
                    taille_redimensionnee.width,
                    taille_redimensionnee.height,
                    o
            );

            if (column == null) column = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));

            g.drawImage(
                    column,
                    0,
                    0,
                    tailleFenetre.width,
                    tailleFenetre.height,
                    o
            );
        } catch (Exception e) {
            System.err.println(ERREUR_IMAGE_FOND + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Dessine le background style papyrus sur une JPanel.
     *
     * @param taille_fenetre taille du dessin background
     */
    public static void dessinePanelBackground(Graphics g, Dimension taille_fenetre, ImageObserver o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            if (parchemin == null) parchemin = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));

            g2d.drawImage(
                    parchemin,
                    0,
                    0,
                    taille_fenetre.width,
                    taille_fenetre.height,
                    o
            );
        } catch (Exception e) {
            System.err.println(ERREUR_IMAGE_FOND + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Dessine le background style parchemin sur une JPanel de victoire.
     *
     * @param tailleFenetre taille du dessin parcheminVictoire
     */
    public static void dessineParcheminVictoire(Graphics g, Dimension tailleFenetre, ImageObserver o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            if (parcheminVictoire == null)
                parcheminVictoire = ImageIO.read(new File(CHEMIN_RESSOURCE + "/assets_recurrents/parchemin.png"));

            g2d.drawImage(
                    parcheminVictoire,
                    0,
                    0,
                    (int) tailleFenetre.getWidth(),
                    (int) tailleFenetre.getHeight(),
                    o
            );
        } catch (Exception e) {
            System.err.println(ERREUR_IMAGE_FOND + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Dessine le background et la bannière suur le panel plateau.
     *
     * @param tailleFenetre    taille de la zone de dessin
     * @param estFini          si le jeu est fini
     * @param config           configuration de la partie
     * @param numJoueurEnCours numéro du joueur en cours
     */
    public static void dessineDecorationPlateau(Graphics g, Dimension tailleFenetre, ImageObserver o, boolean estFini, ConfigurationPartie config, int numJoueurEnCours) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            if (arrierePlanPlateau == null)
                arrierePlanPlateau = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/fond_de_jeu.png"));
            g2d.drawImage(
                    arrierePlanPlateau,
                    0,
                    0,
                    (int) tailleFenetre.getWidth(),
                    (int) tailleFenetre.getHeight(),
                    o
            );

            if (colonneFin == null)
                colonneFin = ImageIO.read(new File(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_berger.png"));
            if (colonneRouge == null)
                colonneRouge = ImageIO.read(new File(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_rouge.png"));
            if (colonneBleu == null)
                colonneBleu = ImageIO.read(new File(CHEMIN_RESSOURCE + "/assets_recurrents/colonne_bleu.png"));

            Image colonne;
            if (estFini) {
                colonne = colonneFin;
            } else {
                int numJoueur = (config.isJoueur1Bleu() ? JOUEUR1 : JOUEUR2);
                colonne = (numJoueurEnCours == numJoueur ? colonneBleu : colonneRouge);
            }

            g2d.drawImage(
                    colonne,
                    0,
                    0,
                    (int) (tailleFenetre.getWidth()), (int) (tailleFenetre.getHeight() * 0.20),
                    o
            );

        } catch (Exception e) {
            System.err.println(ERREUR_IMAGE_FOND + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Charge une image en mémoire depuis son nom.
     *
     * @param _name nom de l'image (url entière depuis la racine)
     * @return l'image chargé.
     */
    public static Image readImage(String _name) {
        try {
            return ImageIO.read(new FileInputStream(_name));
        } catch (IOException e) {
            System.err.println("Erreur : ouverture du fichier : " + _name);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Charge en mémoire la police Lily Script One.
     */
    public static void chargerFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/Lora-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : Un fichier de police est introuvable ou non valide.");
            e.printStackTrace();
        }
    }


}
