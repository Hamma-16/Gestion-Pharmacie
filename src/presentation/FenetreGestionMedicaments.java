package presentation;

import dao.MedicamentDAO;
import metier.Medicament;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FenetreGestionMedicaments extends JFrame {
    private JList<Medicament> medicamentList;
    private DefaultListModel<Medicament> medicamentListModel;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton enregistrerButton;
    private JButton fermerButton;

    private MedicamentDAO medicamentDAO;

    public FenetreGestionMedicaments() throws SQLException {
        medicamentDAO = new MedicamentDAO();

        // Initialisation de la liste des médicaments
        medicamentListModel = new DefaultListModel<>();
        medicamentList = new JList<>(medicamentListModel);

        // Chargement des médicaments depuis la base de données
        List<Medicament> medicaments = medicamentDAO.listerMedicaments();
        for (Medicament medicament : medicaments) {
            medicamentListModel.addElement(medicament);
        }

        // Initialisation des boutons
        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMedicament();
            }
        });

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					modifierMedicament();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					supprimerMedicament();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					enregistrerMedicaments();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        fermerButton = new JButton("Fermer");
        fermerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fermerFenetre();
            }
        });

        // Ajout des composants à la fenêtre
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(medicamentList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(enregistrerButton);
        buttonPanel.add(fermerButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);

        // Configuration de la fenêtre
        setTitle("Gestion des médicaments");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    private void ajouterMedicament() {
        // Affichage d'une fenêtre de saisie des informations du nouveau médicament
        String nom = JOptionPane.showInputDialog(this, "Nom du médicament :");
        String description = JOptionPane.showInputDialog(this, "Description du médicament :");
        double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix du médicament :"));
        int quantite = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité du médicament :"));

        // Création du médicament
        Medicament medicament = new Medicament(0, nom, description, prix, quantite);

        // Ajout du médicament dans la liste
        medicamentListModel.addElement(medicament);
    }

    private void modifierMedicament() throws SQLException {
        // Récupération du médicament sélectionné dans la liste
        Medicament medicamentSelectionne = medicamentList.getSelectedValue();

        if (medicamentSelectionne != null) {
            // Affichage d'une fenêtre de saisie des informations du médicament modifié
            String nom = JOptionPane.showInputDialog(this, "Nom du médicament :", medicamentSelectionne.getNom());
            String description = JOptionPane.showInputDialog(this, "Description du médicament :", medicamentSelectionne.getDescription());
            double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix du médicament :", medicamentSelectionne.getPrix()));
            int quantite = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité du médicament :", medicamentSelectionne.getQuantite()));

            // Modification du médicament dans la liste
            medicamentSelectionne.setNom(nom);
            medicamentSelectionne.setDescription(description);
            medicamentSelectionne.setPrix(prix);
            medicamentSelectionne.setQuantite(quantite);
            // Mettre à jour le médicament dans la base de données
            medicamentDAO.modifierMedicament(medicamentSelectionne);

            // Mettre à jour la liste des médicaments dans l'interface
            medicamentListModel.setElementAt(medicamentSelectionne, medicamentSelectionne.getId());}
        }

        private void supprimerMedicament() throws SQLException {
            // Récupération du médicament sélectionné dans la liste
            Medicament medicamentSelectionne = medicamentList.getSelectedValue();
            if (medicamentSelectionne == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à supprimer.");
                return;
            }

            // Confirmation de la suppression
            int choix = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce médicament ?",
                    "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                // Suppression du médicament dans la base de données
                medicamentDAO.supprimerMedicament(medicamentSelectionne.getId());

                // Suppression du médicament dans la liste
                medicamentListModel.removeElement(medicamentSelectionne);
            }
        }

        private void enregistrerMedicaments() throws SQLException {
            // Enregistrement de tous les médicaments dans la base de données
            for (int i = 0; i < medicamentListModel.size(); i++) {
                Medicament medicament = medicamentListModel.getElementAt(i);
                if (medicament.getId() == 0) { // Si le médicament est nouveau
                    medicamentDAO.ajouterMedicament(medicament); // Ajout du nouveau médicament
                } else { // Si le médicament existe déjà dans la base de données
                    medicamentDAO.modifierMedicament(medicament); // Mise à jour du médicament existant
                }
            }
            JOptionPane.showMessageDialog(this, "La liste des médicaments a été enregistrée avec succès.");
        }


        private void fermerFenetre() {
            // Confirmation avant de fermer la fenêtre
            int choix = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir quitter l'application ?",
                    "Confirmation de fermeture", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                // Fermeture de la fenêtre
                dispose();
            }
        }
    }

