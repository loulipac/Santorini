package Vue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;

import static Modele.Constante.CHEMIN_RESSOURCE;

public class Utile {

    public static Dimension conserverRatio(Dimension imageSize, Dimension boundary) {
        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width * ratio),
                (int) (imageSize.height * ratio));
    }

    public static void dessineBackground(Graphics g, Dimension taille_fenetre, ImageObserver o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            GraphicsConfiguration gfx = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

            BufferedImage img = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/base.png"));

            BufferedImage newImage = Utile.toCompatibleImage(img);

            Dimension img_dim = new Dimension(img.getWidth(), img.getHeight());
            Dimension taille_max = new Dimension((int) (taille_fenetre.width * 0.7), (int) (taille_fenetre.height * 0.7));
            Dimension taille_redimensionnee = Utile.conserverRatio(img_dim, taille_max);

            g2d.drawImage(
                    newImage,
                    taille_fenetre.width / 2 - ((int) (taille_redimensionnee.getWidth() / 2)),
                    taille_fenetre.height / 2 - ((int) (taille_redimensionnee.getHeight() / 2)),
                    taille_redimensionnee.width,
                    taille_redimensionnee.height,
                    o
            );

            BufferedImage img_colonnes = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/columns.png"));
            img_colonnes = Utile.toCompatibleImage(img_colonnes);
            g.drawImage(
                    img_colonnes,
                    0,
                    0,
                    taille_fenetre.width,
                    taille_fenetre.height,
                    o
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur image de fond: " + e.getMessage());
        }
    }

    private static BufferedImage toCompatibleImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return newImage;
    }

    public static void dessinePanelBackground(Graphics g, Dimension taille_fenetre, ImageObserver o) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            BufferedImage bg_panel = ImageIO.read(new File(CHEMIN_RESSOURCE + "/artwork/bg_regles.png"));
            bg_panel = Utile.toCompatibleImage(bg_panel);
            g2d.drawImage(
                    bg_panel,
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
     * @param _name
     * @return l'image chargé.
     */
    public static Image readImage(String _name) {
        try {
            return ImageIO.read(new FileInputStream(_name));
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }


}
