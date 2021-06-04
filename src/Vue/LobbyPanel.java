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
import static Modele.Constante.RATIO_BOUTON_CLASSIQUE_INVERSE;

public class LobbyPanel extends JPanel {
    IO netUser;
    JPanel fondPanel;
    JLabel titreLobby;

    JLabel nom_hote, nom_client;
    JLabel versus;
    Bouton demarrer;
    Bouton pret;
    Font lilly_belle_texte, lilly_belle_titre;

    JLabel ip_hote, client_ready;

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
        lilly_belle_titre = new Font("Lily Script One", Font.PLAIN, 50);
        initialiserComposant();
    }


    private void initialiserComposant() {
        setLayout(null);

        titreLobby = new JLabel("Lobby");
        titreLobby.setFont(lilly_belle_titre);
        titreLobby.setHorizontalAlignment(JLabel.CENTER);

        nom_hote = new JLabel();
        nom_hote.setHorizontalAlignment(JLabel.CENTER);
        nom_hote.setForeground(new Color(70, 153, 206));
        nom_hote.setFont(lilly_belle_texte);
        if (netUser.getClass() == Server.class) nom_hote.setText(netUser.getUsername());


        if (netUser.getClass() == Server.class) {
            ip_hote = new JLabel(((Server) netUser).getIPAddress());
            ip_hote.setHorizontalAlignment(JLabel.CENTER);
            ip_hote.setFont(lilly_belle_texte);
        }

        nom_client = new JLabel();
        nom_client.setHorizontalAlignment(JLabel.CENTER);
        nom_client.setForeground(new Color(224, 98, 98));
        nom_client.setFont(lilly_belle_texte);
        if (netUser.getClass() == Client.class) nom_client.setText(netUser.getUsername());

        client_ready = new JLabel();
        client_ready.setHorizontalAlignment(JLabel.CENTER);
        client_ready.setFont(lilly_belle_texte);

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
        if (netUser.getClass() == Server.class) fondPanel.add(ip_hote);
        fondPanel.add(nom_hote);
        fondPanel.add(versus);
        fondPanel.add(nom_client);
        fondPanel.add(client_ready);
        if (netUser.getClass() == Server.class) fondPanel.add(demarrer);
        if (netUser.getClass() == Client.class) fondPanel.add(pret);

        add(fondPanel);

        setBackground(new Color(47, 112, 162));
        if (netUser.getClass() == Client.class) netUser.sendName();
    }

    public void setAdversaireNom(String nom) {
        if (netUser.getClass() == Server.class) nom_client.setText(nom);
        if (netUser.getClass() == Client.class) nom_hote.setText(nom);
        client_ready.setText("X");
        client_ready.setForeground(Color.BLACK);
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
        if (((Server) netUser).isClient_ready()) {
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
        setClient_ready(pret_bool);
    }

    public void setClient_ready(boolean status) {
        if (!status) {
            client_ready.setForeground(Color.BLACK);
        } else {
            client_ready.setForeground(new Color(87, 213, 59));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double logo_size = 0.20;

        Dimension taille_fenetre = getSize();
        Utile.dessineBackground(g, taille_fenetre, this);

        Dimension parchemin_size = new Dimension((int) (getWidth() * 0.6), (int) (getHeight() * 0.7));
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
                (int) (parent.height * 0.2)
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

        client_ready.setBounds(
                (int) (parent.width * (1 - 0.05 - 0.4)),
                (parent.height / 2) + (int) (parent.height * 0.05),
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

            ip_hote.setBounds(
                    0,
                    (int) (parent.height * 0.2),
                    (int) (parent.width),
                    (int) (parent.height * 0.1)
            );

            demarrer.setTaille(
                    (int) (parent.width * 0.4),
                    (int) (parent.width * 0.4 * RATIO_BOUTON_CLASSIQUE_INVERSE)
            );

            demarrer.changeImage(demarrer.image, demarrer.imageHover);


            demarrer.setBounds(
                    (int) ((parent.width / 2) - (parent.width * 0.2)),
                    versus.getHeight() + versus.getY() + (int) (parent.height * 0.2),
                    demarrer.getWidth(),
                    demarrer.getHeight()
            );
        }

        if (netUser.getClass() == Client.class) {
            pret.setTaille(
                    (int) (parent.width * 0.4),
                    (int) (parent.width * 0.4 * RATIO_BOUTON_CLASSIQUE_INVERSE)
            );

            if (pret_bool) {
                pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret_active.png", CHEMIN_RESSOURCE + "/bouton/pret_active_hover.png");
            } else {
                pret.changeImage(CHEMIN_RESSOURCE + "/bouton/pret.png", CHEMIN_RESSOURCE + "/bouton/pret.png");
            }

            pret.setBounds(
                    (int) ((parent.width / 2) - (parent.width * 0.2)),
                    versus.getHeight() + versus.getY() + (int) (parent.height * 0.2),
                    pret.getWidth(),
                    pret.getHeight()
            );
        }


    }
}
