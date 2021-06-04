package Vue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static Modele.Constante.CHEMIN_RESSOURCE;

public class Utile {
    private static BufferedImage bg;
    private static BufferedImage column;
    private static BufferedImage parchemin;

    /**
     * Permet de redimensionner une taille d'image selon une taille max tout en gardant le bon ratio de l'image.
     *
     * @param tailleImage taille de l'image
     * @param tailleMax   taille max atteignable
     * @return
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
            if(bg == null) bg = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/base.png"));

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

            if(column == null) column = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));

            g.drawImage(
                    column,
                    0,
                    0,
                    tailleFenetre.width,
                    tailleFenetre.height,
                    o
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    /**
     * Adapte une image par rapport à l'environnement graphique.
     *
     * @param image l'image à adapter
     * @return l'image adapter
     */
    private static BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();


        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        BufferedImage newImage = gfxConfig.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        Graphics2D g2d = newImage.createGraphics();

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return newImage;
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
            if(parchemin == null) parchemin = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));

            g2d.drawImage(
                    parchemin,
                    0,
                    0,
                    taille_fenetre.width,
                    taille_fenetre.height,
                    o
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur image de fond: " + e.getMessage());
        }
    }

    /**
     * Change une image en mémoire depuis son nom.
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


}
