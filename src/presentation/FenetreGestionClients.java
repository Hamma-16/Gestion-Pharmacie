package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.ClientDAO;
import metier.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FenetreGestionClients extends JFrame implements ActionListener {
    private JButton btnAjouter, btnModifier, btnSupprimer;
    private JTextField txtNom, txtPrenom, txtTelephone, txtAdresse;
    private JTable table;
    private DefaultTableModel model;
    private ClientDAO clientDAO;

    public FenetreGestionClients() throws SQLException {
        clientDAO = new ClientDAO();
        setTitle("Gestion des clients");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création des composants
        JLabel lblNom = new JLabel("Nom:");
        txtNom = new JTextField(20);
        JLabel lblPrenom = new JLabel("Prénom:");
        txtPrenom = new JTextField(20);
        JLabel lblTelephone = new JLabel("Téléphone:");
        txtTelephone = new JTextField(20);
        JLabel lblAdresse = new JLabel("Adresse:");
        txtAdresse = new JTextField(20);

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");

        // Ajout des écouteurs
        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);

        // Création du tableau
        String[] columnNames = {"Id", "Nom", "Prénom", "Téléphone", "Adresse"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Ajout des composants à la fenêtre
        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(lblNom);
        panelForm.add(txtNom);
        panelForm.add(lblPrenom);
        panelForm.add(txtPrenom);
        panelForm.add(lblTelephone);
        panelForm.add(txtTelephone);
        panelForm.add(lblAdresse);
        panelForm.add(txtAdresse);

        JPanel panelButtons = new JPanel(new GridLayout(1, 3));
        panelButtons.add(btnAjouter);
        panelButtons.add(btnModifier);
        panelButtons.add(btnSupprimer);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.add(new JScrollPane(table), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.SOUTH);
        add(panelTable, BorderLayout.CENTER);

        // Affichage des clients dans le tableau
        List<Client> clients = clientDAO.listerClients();
        for (Client client : clients) {
            Object[] rowData = {client.getId(), client.getNom(), client.getPrenom(), client.getTelephone(), client.getAdresse()};
            model.addRow(rowData);
        }
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouter) {
            // Ajout d'un client
            try {
                Client client = new Client(0, txtNom.getText(), txtPrenom.getText(), txtTelephone.getText(), txtAdresse.getText());
                clientDAO.ajouterClient(client);
                Object[] rowData = {client.getId(), client.getNom(), client.getPrenom(), client.getTelephone(), client.getAdresse()};
                model.addRow(rowData);
                JOptionPane.showMessageDialog(this, "Le client a été ajouté avec succès.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du client : " + ex.getMessage());
            }
        } else if (e.getSource() == btnModifier) {
            // Modification d'un client
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            } else {
                try {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    Client client = new Client(id, txtNom.getText(), txtPrenom.getText(), txtTelephone.getText(), txtAdresse.getText());
                    clientDAO.modifierClient(client);
                    model.setValueAt(client.getNom(), selectedRow, 1);
                    model.setValueAt(client.getPrenom(), selectedRow, 2);
                    model.setValueAt(client.getTelephone(), selectedRow, 3);
                    model.setValueAt(client.getAdresse(), selectedRow, 4);
                    JOptionPane.showMessageDialog(this, "Le client a été modifié avec succès.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification du client : " + ex.getMessage());
                }
            }
        } else if (e.getSource() == btnSupprimer) {
            // Suppression d'un client
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            } else {
                try {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    clientDAO.supprimerClient(id);
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Le client a été supprimé avec succès.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du client : " + ex.getMessage());
                }
            }
        }
    }

}