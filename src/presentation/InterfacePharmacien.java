package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InterfacePharmacien extends JFrame {

    public InterfacePharmacien() {
        super("Interface Pharmacien");
        this.setSize(new Dimension(900, 200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création des boutons
        JButton btnConsulterClients = new JButton("Consulter les clients");
        JButton btnConsulterMedicaments = new JButton("Consulter les médicaments");
        JButton btnGererOrdonnances = new JButton("Gérer les ordonnances");

        // Style des boutons
        Font font = new Font("Arial", Font.BOLD, 20);
        Color bgColor = new Color(0, 153, 204);
        Color fgColor = Color.WHITE;

        btnConsulterClients.setFont(font);
        btnConsulterClients.setBackground(bgColor);
        btnConsulterClients.setForeground(fgColor);
        btnConsulterClients.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnConsulterMedicaments.setFont(font);
        btnConsulterMedicaments.setBackground(bgColor);
        btnConsulterMedicaments.setForeground(fgColor);
        btnConsulterMedicaments.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnGererOrdonnances.setFont(font);
        btnGererOrdonnances.setBackground(bgColor);
        btnGererOrdonnances.setForeground(fgColor);
        btnGererOrdonnances.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

     // Ajout des listeners pour les boutons
        btnConsulterClients.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Code pour ouvrir la fenêtre de consultation des clients
        FenetreConsultationClients fenetreConsultationClients = new FenetreConsultationClients();
        fenetreConsultationClients.setVisible(true);
        }
        });

        btnConsulterMedicaments.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Code pour ouvrir la fenêtre de consultation des médicaments
        FenetreConsultationMedicaments fenetreConsultationMedicaments = new FenetreConsultationMedicaments();
        fenetreConsultationMedicaments.setVisible(true);
        }
        });

        btnGererOrdonnances.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Code pour ouvrir la fenêtre de gestion des ordonnances
        FenetreGestionOrdonnances fenetreGestionOrdonnances = null;
		fenetreGestionOrdonnances = new FenetreGestionOrdonnances();
        fenetreGestionOrdonnances.setVisible(true);
        }
        });

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBoutons.setBorder(new EmptyBorder(50, 0, 0, 0));
        panelBoutons.setBackground(Color.WHITE);
        // Ajout des boutons au panel
        panelBoutons.add(btnConsulterClients);
        panelBoutons.add(btnConsulterMedicaments);
        panelBoutons.add(btnGererOrdonnances);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(230, 230, 230));

        // Titre de la fenêtre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(new Color(120, 150, 190));
        JLabel labelTitre = new JLabel("Interface Pharmacien");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitre.setForeground(Color.WHITE);
        panelTitre.add(labelTitre);
        
        // Ajout du panel titre à la fenêtre
        this.getContentPane().add(panelTitre, BorderLayout.NORTH);

        // Création d'un panel pour les boutons
        JPanel panelBoutons1 = new JPanel(new GridBagLayout());
        panelBoutons1.setBackground(Color.WHITE);

        // Définition des contraintes pour le placement des boutons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Ajout des boutons au panel
        panelBoutons1.add(btnConsulterClients, gbc);
        gbc.gridx++;
        panelBoutons1.add(btnConsulterMedicaments, gbc);
        gbc.gridx++;
        panelBoutons1.add(btnGererOrdonnances, gbc);

        // Ajout du panel des boutons à la fenêtre
        this.getContentPane().add(panelBoutons1, BorderLayout.CENTER);
    }
}