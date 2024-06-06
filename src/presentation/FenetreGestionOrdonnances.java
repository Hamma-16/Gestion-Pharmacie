package presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.OrdonnanceDAO;
import metier.Ordonnance;

public class FenetreGestionOrdonnances extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panelBoutons;
    private JButton btnAjouter;
    private JButton btnSupprimer;
    private JButton btnModifier;
    private JTable tableOrdonnances;

    private OrdonnanceDAO ordonnanceDAO;

    public FenetreGestionOrdonnances() {
        super("Gestion des ordonnances");

        // Initialisation des composants
        panelBoutons = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnSupprimer = new JButton("Supprimer");
        btnModifier = new JButton("Modifier");
        tableOrdonnances = new JTable();
        
        
        // Configuration des composants
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnModifier);

        String[] entetes = { "ID Médicament", "ID Client", "Quantité", "Date" };
        DefaultTableModel model = new DefaultTableModel(entetes, 0);
        tableOrdonnances.setModel(model);

        JScrollPane scrollPane = new JScrollPane(tableOrdonnances);

        // Ajout des composants à la fenêtre
        add(panelBoutons, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configuration de la fenêtre
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création de l'objet OrdonnanceDAO
        try {
            ordonnanceDAO = new OrdonnanceDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chargement des ordonnances dans la table
        chargerOrdonnances();

        // Ajout des listeners sur les boutons
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterOrdonnance();
            }
        });

        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerOrdonnance();
            }
        });
        
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifierOrdonnance();
            }
        });
        
    }

    private void chargerOrdonnances() {
        try {
            // Récupération de la liste des ordonnances depuis la base de données
            List<Ordonnance> ordonnances = ordonnanceDAO.listerOrdonnances();

            // Ajout des ordonnances dans la table
            DefaultTableModel model = (DefaultTableModel) tableOrdonnances.getModel();
            model.setRowCount(0);
            for (Ordonnance ordonnance : ordonnances) {
                Object[] row = { ordonnance.getIdMedicament(), ordonnance.getIdClient(), ordonnance.getQuantite(),
                        ordonnance.getDate() };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void supprimerOrdonnance() {
        // Récupération de l'ordonnance sélectionnée dans la table
        int selectedRow = tableOrdonnances.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ordonnance à supprimer.");
            return;
        }

        // Récupération des valeurs de l'ordonnance sélectionnée
        int idMedicament = (int) tableOrdonnances.getValueAt(selectedRow, 0);
        int idClient = (int) tableOrdonnances.getValueAt(selectedRow, 1);
        int quantite = (int) tableOrdonnances.getValueAt(selectedRow, 2);
        Date date = (Date) tableOrdonnances.getValueAt(selectedRow, 3);

        // Confirmation de la suppression
        int choix = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette ordonnance ?",
                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION) {
            try {
                // Suppression de l'ordonnance dans la base de données
                ordonnanceDAO.supprimerOrdonnance(idMedicament);

                // Rechargement des ordonnances dans la table
                chargerOrdonnances();

                JOptionPane.showMessageDialog(this, "Ordonnance supprimée avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'ordonnance : " + e.getMessage());
            }
        }
    }


    private void ajouterOrdonnance() {
        // Création d'un objet Ordonnance à partir des données saisies
        int idMedicament = Integer.parseInt(JOptionPane.showInputDialog(null, "ID du médicament :", "Ajout d'une ordonnance", JOptionPane.QUESTION_MESSAGE));
        int idClient = Integer.parseInt(JOptionPane.showInputDialog(null, "ID du client :", "Ajout d'une ordonnance", JOptionPane.QUESTION_MESSAGE));
        int quantite = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantité :", "Ajout d'une ordonnance", JOptionPane.QUESTION_MESSAGE));
        Date date = new Date();
        Ordonnance nouvelleOrdonnance = new Ordonnance(idMedicament, idClient, quantite, date);
        try {
            // Ajout de la nouvelle ordonnance à la base de données
            OrdonnanceDAO ordonnanceDAO = new OrdonnanceDAO();
            ordonnanceDAO.ajouterOrdonnance(nouvelleOrdonnance);
            JOptionPane.showMessageDialog(null, "Ordonnance ajoutée avec succès !", "Ajout d'une ordonnance", JOptionPane.INFORMATION_MESSAGE);
            
            // Mise à jour de l'affichage des ordonnances
            listerOrdonnances();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'ordonnance : " + ex.getMessage(), "Ajout d'une ordonnance", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    private void modifierOrdonnance() {
        // Récupération de l'ordonnance à modifier
        int index = tableOrdonnances.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ordonnance à modifier !", "Modification d'une ordonnance", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMedicament = (int) tableOrdonnances.getValueAt(index, 0);
        int idClient = (int) tableOrdonnances.getValueAt(index, 1);
        int quantite = (int) tableOrdonnances.getValueAt(index, 2);
        Date date = (Date) tableOrdonnances.getValueAt(index, 3);

        // Demande de saisie des nouvelles valeurs
        int nouveauIdMedicament = Integer.parseInt(JOptionPane.showInputDialog(null, "ID du médicament :", idMedicament));
        int nouveauIdClient = Integer.parseInt(JOptionPane.showInputDialog(null, "ID du client :", idClient));
        int nouvelleQuantite = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantité :", quantite));
        Date nouvelleDate = new Date();
        Ordonnance nouvelleOrdonnance = new Ordonnance(nouveauIdMedicament, nouveauIdClient, nouvelleQuantite, nouvelleDate);
        
        try {
            // Modification de l'ordonnance dans la base de données
            OrdonnanceDAO.modifierOrdonnance(idMedicament, idClient, quantite, (java.sql.Date) date, nouvelleOrdonnance);
            JOptionPane.showMessageDialog(null, "Ordonnance modifiée avec succès !", "Modification d'une ordonnance", JOptionPane.INFORMATION_MESSAGE);
            
            // Mise à jour de l'affichage des ordonnances
            chargerOrdonnances();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'ordonnance : " + ex.getMessage(), "Modification d'une ordonnance", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void listerOrdonnances() {
        try {
            // Récupération de la liste des ordonnances
            OrdonnanceDAO ordonnanceDAO = new OrdonnanceDAO();
            List<Ordonnance> ordonnances = ordonnanceDAO.listerOrdonnances();
            
            // Mise à jour de l'affichage des ordonnances
            DefaultTableModel model = (DefaultTableModel) tableOrdonnances.getModel();
            model.setRowCount(0);
            
            for (Ordonnance ordonnance : ordonnances) {
                Object[] row = {ordonnance.getIdMedicament(), ordonnance.getIdClient(), ordonnance.getQuantite(), ordonnance.getDate()};
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des ordonnances : " + ex.getMessage(), "Liste des ordonnances", JOptionPane.ERROR_MESSAGE);
        }
    }

}