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

public class InterfaceAdmin extends JFrame {

    public InterfaceAdmin() {
        super("Interface Admin");
        this.setSize(new Dimension(900, 200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création des boutons
        JButton btnGestionClients = new JButton("Gestion des clients");
        JButton btnGestionMedicaments = new JButton("Gestion des médicaments");
        JButton btnGestionStock = new JButton("Gestion du stock");

        // Style des boutons
        Font font = new Font("Arial", Font.BOLD, 20);
        Color bgColor = new Color(0, 153, 204);
        Color fgColor = Color.WHITE;

        btnGestionClients.setFont(font);
        btnGestionClients.setBackground(bgColor);
        btnGestionClients.setForeground(fgColor);
        btnGestionClients.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnGestionMedicaments.setFont(font);
        btnGestionMedicaments.setBackground(bgColor);
        btnGestionMedicaments.setForeground(fgColor);
        btnGestionMedicaments.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnGestionStock.setFont(font);
        btnGestionStock.setBackground(bgColor);
        btnGestionStock.setForeground(fgColor);
        btnGestionStock.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Ajout des listeners pour les boutons
        btnGestionClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour ouvrir la fenêtre de gestion des clients
                FenetreGestionClients fgc = null;
				try {
					fgc = new FenetreGestionClients();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                fgc.setVisible(true);
            }
        });

        btnGestionMedicaments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour ouvrir la fenêtre de gestion des médicaments
                FenetreGestionMedicaments fgm = null;
				try {
					fgm = new FenetreGestionMedicaments();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                fgm.setVisible(true);
            }
        });

        btnGestionStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour ouvrir la fenêtre de gestion du stock
                FenetreGestionStock fgs = null;
				try {
					fgs = new FenetreGestionStock();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                fgs.setVisible(true);
            }
        });

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBoutons.setBorder(new EmptyBorder(50, 0, 0, 0));
        panelBoutons.setBackground(Color.WHITE);

        // Ajout des boutons au panel
        panelBoutons.add(btnGestionClients);
        panelBoutons.add(btnGestionMedicaments);
        panelBoutons.add(btnGestionStock);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(230, 230, 230));

        // Titre de la fenêtre
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitre.setBackground(new Color(120, 150, 190));
        JLabel labelTitre = new JLabel("Interface Admin");
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
        panelBoutons1.add(btnGestionClients, gbc);
        gbc.gridx++;
        panelBoutons1.add(btnGestionMedicaments, gbc);
        gbc.gridx++;
        panelBoutons1.add(btnGestionStock, gbc);

        // Ajout du panel des boutons à la fenêtre
        this.getContentPane().add(panelBoutons1, BorderLayout.CENTER);
    }
}
