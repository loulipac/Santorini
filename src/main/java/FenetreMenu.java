package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Component.CENTER_ALIGNMENT;

public class FenetreMenu {

    JFrame menuPrincipal;
    public JSpinner ligne, colonne;
    ButtonGroup buttonGroup, IAMode, IA2Mode;
    public JButton bPlay;
    JRadioButton player_vs_player, player_vs_ia, ia_vs_ia, easy, normal, hard, easy2, normal2, hard2;
    JLabel textSpinner1, textSpinner2, titre, gameModeText, IAText, IA2Text;
    JPanel boxGameButtons, boxIADifficultyButtons, boxIAvsIADifficultyButtons;

    public FenetreMenu(){
        lancerMenu();
    }

    public void lancerMenu() {

        /* Main Panel */
        menuPrincipal = new JFrame("Santorini - Menu Principal");
        menuPrincipal.setMinimumSize(new Dimension(500, 500));
        menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipal.setLocationRelativeTo(null);

        int height = menuPrincipal.getHeight();
        int width = menuPrincipal.getWidth();


        /* Box creation */
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);
        panel.setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));


        /* Input */
        textSpinner1 = creerJLabel("Nombre de lignes :", CENTER_ALIGNMENT);
        textSpinner2 = creerJLabel("Nombre de colonnes :", CENTER_ALIGNMENT);

        SpinnerModel modelLigne = new SpinnerNumberModel(5, 5, 20, 1);
        ligne = new JSpinner(modelLigne);
        ligne.setMaximumSize(new Dimension(width / 3, height / 15));

        SpinnerModel modelColonne = new SpinnerNumberModel(5, 5, 20, 1);
        colonne = new JSpinner(modelColonne);
        colonne.setMaximumSize(new Dimension(width / 3, height / 15));


        /* Button */
        bPlay = new JButton("Jouer");
        bPlay.setAlignmentX(CENTER_ALIGNMENT);


        /* Radio */
        boxGameButtons = new JPanel();
        boxGameButtons.setAlignmentX(CENTER_ALIGNMENT);
        buttonGroup = new ButtonGroup();

        player_vs_player = creerBoutonRadio("Jouer en 1v1", CENTER_ALIGNMENT, buttonGroup);
        player_vs_ia = creerBoutonRadio("Jouer contre l'IA", CENTER_ALIGNMENT, buttonGroup);
        ia_vs_ia = creerBoutonRadio("IA contre IA", CENTER_ALIGNMENT, buttonGroup);

        boxIADifficultyButtons = new JPanel();
        boxIADifficultyButtons.setAlignmentX(CENTER_ALIGNMENT);

        boxIAvsIADifficultyButtons = new JPanel();
        boxIAvsIADifficultyButtons.setAlignmentX(CENTER_ALIGNMENT);

        IAMode = new ButtonGroup();
        easy = creerBoutonRadio("Facile", CENTER_ALIGNMENT, IAMode);
        normal = creerBoutonRadio("Normale", CENTER_ALIGNMENT, IAMode);
        hard = creerBoutonRadio("Difficile", CENTER_ALIGNMENT, IAMode);

        IA2Mode = new ButtonGroup();
        easy2 = creerBoutonRadio("Facile", CENTER_ALIGNMENT, IA2Mode);
        normal2 = creerBoutonRadio( "Normale", CENTER_ALIGNMENT, IA2Mode);
        hard2 = creerBoutonRadio( "Difficile", CENTER_ALIGNMENT, IA2Mode);


        /* Label */
        titre = creerJLabel("Santorini", CENTER_ALIGNMENT);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        gameModeText = creerJLabel("Mode de jeu : ", CENTER_ALIGNMENT);
        IAText = creerJLabel("Difficulté de l'IA : ", CENTER_ALIGNMENT);
        IA2Text = creerJLabel("Difficulté de l'IA 2 : ", CENTER_ALIGNMENT);


        /* Event */
        ajouterEvenements();

        /* Set selected radio button */
        player_vs_player.setSelected(true);
        IAText.setVisible(false);
        IA2Text.setVisible(false);
        boxIADifficultyButtons.setVisible(false);
        boxIAvsIADifficultyButtons.setVisible(false);


        /* Adding */
        ajouterAuPanel(titre, null, null, panel, width, height / 12);
        ajouterAuPanel(textSpinner1, ligne, null, panel, width, 20);
        ajouterAuPanel(textSpinner2, colonne, null, panel, width, 20);

        panel.add(gameModeText);
        boxGameButtons.add(player_vs_player);
        boxGameButtons.add(player_vs_ia);
        boxGameButtons.add(ia_vs_ia);
        ajouterAuPanel(null, null, boxGameButtons, panel, width, 20);

        panel.add(IAText);
        boxIADifficultyButtons.add(easy);
        boxIADifficultyButtons.add(normal);
        boxIADifficultyButtons.add(hard);
        ajouterAuPanel(null, null, boxIADifficultyButtons, panel, width, 20);

        panel.add(IA2Text);
        boxIAvsIADifficultyButtons.add(easy2);
        boxIAvsIADifficultyButtons.add(normal2);
        boxIAvsIADifficultyButtons.add(hard2);
        ajouterAuPanel(null, null, boxIAvsIADifficultyButtons, panel, width, 20);

        panel.add(bPlay);

        menuPrincipal.add(panel);
        menuPrincipal.setVisible(true);
    }

     /**
      * creerBoutonRadio
      * @param nom le nom du futur bouton radio
      * @param alignement l'alignement du futur bouton
      * @param groupe le groupe contenant le futur bouton
      * @return crée et retourne un bouton radio
      */
    private JRadioButton creerBoutonRadio(String nom, float alignement, ButtonGroup groupe){
        JRadioButton j = new JRadioButton(nom);
        j.setAlignmentX(alignement);
        groupe.add(j);
        return j;
    }

    /**
     * creerJLabel
     * @param content le contenu du label
     * @param alignement l'alignement du label
     * @return crée et retourne un label
     */
    private JLabel creerJLabel(String content, float alignement){
        JLabel l = new JLabel(content);
        l.setAlignmentX(alignement);
        return l;
    }

    /**
     * ajouterAuPanel
     * @param label le label ajouté au panel
     * @param spinner le spinner ajouté au panel
     * @param pan le panel  ajouté au panel principal
     * @param p le panel où les composants sont ajoutés
     * @param longueur la longueur de la RigidArea
     * @param hauteur la hauteur de la RigidArea
     */
    private void ajouterAuPanel(JLabel label, JSpinner spinner, JPanel pan, JPanel p, int longueur, int hauteur){
        if(label != null){
            p.add(label);
        }
        if(spinner != null){
            p.add(spinner);
        }
        if(pan != null){
            p.add(pan);
        }
        p.add(Box.createRigidArea(new Dimension(longueur, hauteur)));
    }

    private void ajouterEvenements(){
        player_vs_ia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IAText.setVisible(true);
                IA2Text.setVisible(false);
                boxIADifficultyButtons.setVisible(true);
                boxIAvsIADifficultyButtons.setVisible(false);
            }
        });

        player_vs_player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IAText.setVisible(false);
                IA2Text.setVisible(false);
                boxIADifficultyButtons.setVisible(false);
                boxIAvsIADifficultyButtons.setVisible(false);
            }
        });

        ia_vs_ia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IAText.setVisible(true);
                IA2Text.setVisible(true);
                boxIADifficultyButtons.setVisible(true);
                boxIAvsIADifficultyButtons.setVisible(true);

            }
        });
    }

}
