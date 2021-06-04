package Vue;

import IO.IO;
import IO.Server;
import IO.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static Modele.Constante.CHEMIN_RESSOURCE;

public class LobbyPanel extends JPanel {
    IO netUser;
    JPanel logoPanel;
    JLabel logo;
    JPanel fondPanel;
    JLabel titreLobby;

    JLabel nom_hote, nom_client;
    JLabel versus;
    Bouton demarrer;
    Bouton pret;
    Font lilly_belle_texte, lilly_belle_titre;

    boolean pret_bool = false;


    public LobbyPanel(IO netUser) {
        this.netUser = netUser;
        netUser.setLobby(this);
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/LilyScriptOne.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(CHEMIN_RESSOURCE + "/font/Lora-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Erreur : La police 'LilyScriptOne' est introuvable ");
        }
        lilly_belle_texte = new Font("Lily Script One", Font.PLAIN, 30);
        lilly_belle_titre = new Font("Lily Script One", Font.PLAIN, 35);
        initialiserComposant();
    }


    private void initialiserComposant() {
        setLayout(null);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logo = new JLabel();
        logoPanel.add(logo);

        titreLobby = new JLabel("Lobby");
        titreLobby.setFont(lilly_belle_titre);
        titreLobby.setHorizontalAlignment(JLabel.CENTER);

        nom_hote = new JLabel();
        nom_hote.setHorizontalAlignment(JLabel.CENTER);
        nom_hote.setForeground(new Color(70, 153, 206));
        nom_hote.setFont(lilly_belle_texte);
        if (netUser.getClass() == Server.class) nom_hote.setText(netUser.getUsername());

        nom_client = new JLabel();
        nom_client.setHorizontalAlignment(JLabel.CENTER);
        nom_client.setForeground(new Color(224, 98, 98));
        nom_client.setFont(lilly_belle_texte);
        if (netUser.getClass() == Client.class) nom_client.setText(netUser.getUsername());

        versus = new JLabel("VS");
        versus.setHorizontalAlignment(JLabel.CENTER);
        versus.setFont(lilly_belle_texte);

        if (netUser.getClass() == Server.class) {
            demarrer = new Bouton(CHEMIN_RESSOURCE + "/bouton/demarrer.png", CHEMIN_RESSOURCE + "/bouton/demarrer_hover.png", 1, 1, this::actionStart);
        }

        if (netUser.getClass() == Client.class) {
            pret = new Bouton(CHEMIN_RESSOURCE + "/bouton/pret.png", CHEMIN_RESSOURCE + "/bouton/pret_hover.png", 1, 1, this::actionPret);
        }

        fondPanel = new PanelParchemin();

        fondPanel.add(titreLobby);
        fondPanel.add(nom_hote);
        fondPanel.add(versus);
        fondPanel.add(nom_client);
        if (netUser.getClass() == Server.class) fondPanel.add(demarrer);
        if (netUser.getClass() == Client.class) fondPanel.add(pret);

        add(logoPanel);
        add(fondPanel);

        setBackground(new Color(47, 112, 162));
        if (netUser.getClass() == Client.class) netUser.sendName();
    }

    public void setAdversaireNom(String nom) {
        if (netUser.getClass() == Server.class) nom_client.setText(nom);
        if (netUser.getClass() == Client.class) nom_hote.setText(nom);
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

    public void actionStart(ActionEvent e) {
        if(((Server) netUser).isClient_ready()) {
            Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
            netUser.startPartie();
            f.setPanel(new PanelPlateau(getSize(), netUser));
        }
    }

    public void startClientGame() {
        Fenetre f = (Fenetre) SwingUtilities.getWindowAncestor(this);
        f.setPanel(new PanelPlateau(getSize(), netUser));
    }

    public void actionPret(ActionEvent e) {
        if (!pret_bool) {
            pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret_active.png", CHEMIN_RESSOURCE + "/bouton/pret_active_hover.png");
            System.out.println("changeImage");
        } else {
            pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret.png", CHEMIN_RESSOURCE + "/bouton/pret.png");
        }
        pret_bool = !pret_bool;
        ((Client) netUser).sendReady(pret_bool);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double logo_size = 0.30;

        Dimension taille_fenetre = getSize();
        Utile.dessineBackground(g, taille_fenetre, this);
        setLogoSize(logo_size);

        Dimension parchemin_size = new Dimension((int) (getWidth() * 0.4), (int) (getHeight() * 0.45));
        Point origin = new Point(
                (getWidth() / 2) - (parchemin_size.width / 2),
                (int) (getHeight() * logo_size)
        );
        fondPanel.setBounds(
                origin.x,
                origin.y,
                parchemin_size.width,
                parchemin_size.height
        );

        Dimension parent = titreLobby.getParent().getSize();

        titreLobby.setBounds(
                0, 0,
                parent.width,
                (int) (parent.height * 0.3)
        );

        nom_hote.setBounds(
                (int) (parent.width * 0.05),
                (parent.height / 2) - (int) (parent.height * 0.05),
                (int) (parent.width * 0.4),
                (int) (parent.height * 0.1)
        );

        nom_client.setBounds(
                (int) (parent.width * (1 - 0.05 - 0.4)),
                (parent.height / 2) - (int) (parent.height * 0.05),
                (int) (parent.width * 0.4),
                (int) (parent.height * 0.1)
        );


        versus.setBounds(
                (int) ((parent.width / 2) - (parent.width * 0.05)),
                (parent.height / 2) - (int) (parent.height * 0.05),
                (int) (parent.width * 0.1),
                (int) (parent.height * 0.1)
        );


        if (netUser.getClass() == Server.class) {
            demarrer.setTaille(
                    (int) (parent.width * 0.5),
                    (int) (parent.height * 0.15)
            );

            demarrer.changeImage(demarrer.image, demarrer.imageHover);


            demarrer.setBounds(
                    demarrer.getWidth() / 2,
                    versus.getHeight() + versus.getY() + (int) (parent.height * 0.2),
                    demarrer.getWidth(),
                    demarrer.getHeight()
            );
        }

        if (netUser.getClass() == Client.class) {
            pret.setTaille(
                    (int) (parent.width * 0.5),
                    (int) (parent.height * 0.15)
            );

            if (pret_bool) {
                pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret_active.png", CHEMIN_RESSOURCE + "/bouton/pret_active_hover.png");
            } else {
                pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret.png", CHEMIN_RESSOURCE + "/bouton/pret.png");
            }

            pret.setBounds(
                    pret.getWidth() / 2,
                    versus.getHeight() + versus.getY() + (int) (parent.height * 0.2),
                    pret.getWidth(),
                    pret.getHeight()
            );
        }


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
