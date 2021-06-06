package Vue;

import IO.Client;
import IO.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static Modele.Constante.*;

public class PanelMultijoueur extends JPanel {
    JPanel logoPanel;
    JLabel logo;
    PanelParchemin rejoindrePanel, hebergerPanel;
    JLabel titreRejoindre, titreHeberger;
    Font lilly_belle_texte, lilly_belle_titre;

    JLabel placeholder_nom_rejoindre;
    JTextField nom_rejoindre;
    JLabel placeholder_ip;
    JTextField ip;
    Bouton bRejoindre;

    JLabel placeholder_nom_heberger;
    JTextField nom_heberger;
    Bouton bHeberger;

    Bouton bRetour;
    Fenetre f;

    boolean first = true;

    final String TITRE_REJOINDRE = "Rejoindre une partie";
    final String TITRE_HEBERGER = "Héberger une partie";

    public PanelMultijoueur(Fenetre f) {
        this.f = f;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/Lora-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle_texte = new Font("Lily Script One", Font.PLAIN, 20);
        lilly_belle_titre = new Font("Lily Script One", Font.PLAIN, 30);
        initialiserComposant();
    }


    private void initialiserComposant() {
        setLayout(null);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logo = new JLabel();
        logoPanel.add(logo);

        rejoindrePanel = new PanelParchemin();
        titreRejoindre = new JLabel(TITRE_REJOINDRE);
        titreRejoindre.setFont(lilly_belle_titre);
        titreRejoindre.setHorizontalAlignment(JLabel.CENTER);

        placeholder_nom_rejoindre = new JLabel("Nom d'utilisateur : ");
        placeholder_nom_rejoindre.setFont(lilly_belle_texte);
        nom_rejoindre = new JTextField();
        nom_rejoindre.setFont(lilly_belle_texte);

        placeholder_ip = new JLabel("Adresse IP : ");
        placeholder_ip.setFont(lilly_belle_texte);
        ip = new JTextField();
        ip.setFont(lilly_belle_texte);

        bRejoindre = new Bouton(CHEMIN_RESSOURCE + "/bouton/rejoindre.png", CHEMIN_RESSOURCE + "/bouton/rejoindre_hover.png",
                1,
                1, this::actionRejoindre);

        bRetour = new Bouton(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png",
                1,
                1, this::actionRetour);


        rejoindrePanel.add(titreRejoindre);
        rejoindrePanel.add(placeholder_nom_rejoindre);
        rejoindrePanel.add(nom_rejoindre);
        rejoindrePanel.add(placeholder_ip);
        rejoindrePanel.add(ip);
        rejoindrePanel.add(bRejoindre);

        hebergerPanel = new PanelParchemin();
        titreHeberger = new JLabel(TITRE_HEBERGER);
        titreHeberger.setFont(lilly_belle_titre);
        titreHeberger.setHorizontalAlignment(JLabel.CENTER);

        placeholder_nom_heberger = new JLabel("Nom d'utilisateur : ");
        placeholder_nom_heberger.setFont(lilly_belle_texte);
        nom_heberger = new JTextField();
        nom_heberger.setFont(lilly_belle_texte);

        bHeberger = new Bouton(CHEMIN_RESSOURCE + "/bouton/heberger.png", CHEMIN_RESSOURCE + "/bouton/heberger_hover.png",
                1,
                1, this::actionHeberger);

        hebergerPanel.add(titreHeberger);
        hebergerPanel.add(placeholder_nom_heberger);
        hebergerPanel.add(nom_heberger);
        hebergerPanel.add(bHeberger);

        add(logoPanel);
        add(rejoindrePanel);
        add(hebergerPanel);
        add(bRetour);
        setBackground(new Color(47, 112, 162));
    }

    private class PanelParchemin extends JPanel {
        public PanelParchemin() {
            setOpaque(false);
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Utile.dessinePanelBackground(g, getSize(), this);
        }
    }

    public void actionRejoindre(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);

        if (nom_rejoindre.getText().equals("") && ip.getText().equals("")) {
            Client client = new Client("127.0.0.1", "anonyme_client");
            LobbyPanel lp = new LobbyPanel(client);
            f.setPanel(lp);
        } else if (nom_rejoindre.getText().equals("")) {
            Client client = new Client(ip.getText(), "anonyme_client");
            LobbyPanel lp = new LobbyPanel(client);
            f.setPanel(lp);
        } else {
            Client client = new Client(ip.getText(), nom_rejoindre.getText());
            LobbyPanel lp = new LobbyPanel(client);
            f.setPanel(lp);
        }
    }

    public void actionHeberger(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);

        if (!nom_heberger.getText().equals("")) {
            Server serveur = new Server(nom_heberger.getText());
            LobbyPanel lp = new LobbyPanel(serveur);
            f.setPanel(lp);
        } else {
            Server serveur = new Server("anonyme_hote");
            LobbyPanel lp = new LobbyPanel(serveur);
            f.setPanel(lp);
        }
    }

    public void actionRetour(ActionEvent e) {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.displayPanel("menu");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension taille_fenetre = getSize();
        Utile.dessineBackground(g, taille_fenetre, this);

        if(!first && !f.should_resize) return;
        first = false;

        double logo_size = 0.30;

        setLogoSize(logo_size);
        drawRejoindre(logo_size);
        drawHeberger(logo_size);

        bRetour.setTaille(
                (int) (taille_fenetre.width * 0.2),
                (int) (taille_fenetre.height * 0.07)
        );

        bRetour.changeImage(CHEMIN_RESSOURCE + "/bouton/retour.png", CHEMIN_RESSOURCE + "/bouton/retour_hover.png");


        bRetour.setBounds(
                (int) ((taille_fenetre.width / 2) - (bRetour.getWidth() * 0.5)),
                (int) (taille_fenetre.height - taille_fenetre.height * 0.20),
                bRetour.getWidth(),
                bRetour.getHeight()
        );
    }

    private void drawRejoindre(double logo_size) {
        Dimension parchemin_size = new Dimension((int) (getWidth() * 0.3), (int) (getHeight() * 0.4));
        Point origin = new Point(
                (getWidth() / 4) - (parchemin_size.width / 2),
                (int) (getHeight() * logo_size)
        );
        rejoindrePanel.setBounds(
                origin.x,
                origin.y,
                parchemin_size.width,
                parchemin_size.height
        );
        rejoindrePanel.setSize(parchemin_size);

        Dimension parent = titreRejoindre.getParent().getSize();

        titreRejoindre.setBounds(
                0, 0,
                parent.width,
                (int) (parent.height * 0.4)
        );

        placeholder_nom_rejoindre.setBounds(
                (int) (parent.width * 0.05),
                titreRejoindre.getHeight(),
                (int) (parent.width * 0.4),
                (int) (parent.height * 0.1)
        );

        nom_rejoindre.setBounds(
                placeholder_nom_rejoindre.getWidth() + (int) (parent.width * 0.05),
                titreRejoindre.getHeight(),
                (int) (parent.width * 0.50),
                (int) (parent.height * 0.1)

        );

        placeholder_ip.setBounds(
                (int) (parent.width * 0.05),
                placeholder_nom_rejoindre.getHeight() + placeholder_nom_rejoindre.getY() + (int) (parent.height * 0.05),
                (int) (parent.width * 0.4),
                (int) (parent.height * 0.1)
        );

        ip.setBounds(
                placeholder_ip.getWidth() + (int) (parent.width * 0.05),
                placeholder_ip.getY(),
                (int) (parent.width * 0.50),
                (int) (parent.height * 0.1)

        );

        bRejoindre.setTaille(
                (int) (parent.width * 0.5),
                (int) (parent.height * 0.15)
        );

        bRejoindre.changeImage(CHEMIN_RESSOURCE + "/bouton/rejoindre.png", CHEMIN_RESSOURCE + "/bouton/rejoindre_hover.png");


        bRejoindre.setBounds(
                bRejoindre.getWidth() / 2,
                placeholder_ip.getHeight() + placeholder_ip.getY() + (int) (parent.height * 0.1),
                bRejoindre.getWidth(),
                bRejoindre.getHeight()
        );

    }

    private void drawHeberger(double logo_size) {
        Dimension parchemin_size = new Dimension((int) (getWidth() * 0.3), (int) (getHeight() * 0.4));
        hebergerPanel.setBounds(
                (int) (getWidth() * 0.75) - (parchemin_size.width / 2),
                (int) (getHeight() * logo_size),
                parchemin_size.width,
                parchemin_size.height
        );
        hebergerPanel.setSize(parchemin_size);


        Dimension parent = titreHeberger.getParent().getSize();

        titreHeberger.setBounds(
                0, 0,
                parent.width,
                (int) (parent.height * 0.4)
        );

        placeholder_nom_heberger.setBounds(
                (int) (parent.width * 0.05),
                titreHeberger.getHeight(),
                (int) (parent.width * 0.4),
                (int) (parent.height * 0.1)
        );

        nom_heberger.setBounds(
                placeholder_nom_heberger.getWidth() + (int) (parent.width * 0.05),
                titreHeberger.getHeight(),
                (int) (parent.width * 0.50),
                (int) (parent.height * 0.1)

        );

        bHeberger.setTaille(
                (int) (parent.width * 0.5),
                (int) (parent.height * 0.15)
        );

        bHeberger.changeImage(CHEMIN_RESSOURCE + "/bouton/heberger.png", CHEMIN_RESSOURCE + "/bouton/heberger_hover.png");


        bHeberger.setBounds(
                bHeberger.getWidth() / 2,
                placeholder_ip.getHeight() + placeholder_ip.getY() + (int) (parent.height * 0.1),
                bHeberger.getWidth(),
                bHeberger.getHeight()
        );
    }

    private void setLogoSize(double ratio) {
        ImageIcon logo_img = new ImageIcon(CHEMIN_RESSOURCE + "/logo/logo_hd.png");
        double ratio_logo_img = (double) logo_img.getIconWidth() / logo_img.getIconHeight();
        double taille_logo = getSize().height * (ratio / 2);
        ImageIcon logo_resize = new ImageIcon(logo_img.getImage().getScaledInstance((int) (taille_logo * ratio_logo_img), (int) (taille_logo), Image.SCALE_SMOOTH));
        logo.setIcon(logo_resize);
        Dimension logoPanel_taille = new Dimension(getSize().width, (int) (getSize().height * ratio));
        logoPanel.setSize(logoPanel_taille);
        logoPanel.setBounds(
                0,
                0,
                logoPanel_taille.width, logoPanel_taille.height);
        logo.setBounds(
                (logoPanel.getSize().width / 2) - (logo.getWidth() / 2),
                (logoPanel.getSize().height / 2) - (logo.getHeight() / 2),
                logo.getWidth(),
                logo.getHeight()
        );
    }
}
