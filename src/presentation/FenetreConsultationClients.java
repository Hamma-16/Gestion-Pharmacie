package presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ClientDAO;
import metier.Client;

public class FenetreConsultationClients extends JFrame implements ActionListener {
    private JPanel panelContent;
    private JTextField textRecherche;
    private JButton buttonRecherche;
    private JTable tableClients;
    private DefaultTableModel modelTable;
    private List<Client> allClients;

    public FenetreConsultationClients() {
        setTitle("Consultation des clients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 300);

        panelContent = new JPanel();
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContent.setLayout(new BorderLayout());

        JPanel panelRecherche = new JPanel();
        panelRecherche.setLayout(new BoxLayout(panelRecherche, BoxLayout.X_AXIS));
        panelRecherche.setBorder(new EmptyBorder(0, 0, 10, 0));
        textRecherche = new JTextField();
        textRecherche.setToolTipText("Entrez le nom ou le prénom du client à chercher");
        panelRecherche.add(textRecherche);
        buttonRecherche = new JButton("Chercher");
        buttonRecherche.addActionListener(this);
        panelRecherche.add(buttonRecherche);
        panelContent.add(panelRecherche, BorderLayout.NORTH);

        try {
            ClientDAO dao = new ClientDAO();
            allClients = dao.listerClients();
            modelTable = new DefaultTableModel(new Object[][]{}, new String[]{"Id", "Nom", "Prénom", "Téléphone", "Adresse"});
            for (Client client : allClients) {
                modelTable.addRow(new Object[]{client.getId(), client.getNom(), client.getPrenom(),
                        client.getTelephone(), client.getAdresse()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des clients : " + ex.getMessage());
            modelTable = new DefaultTableModel(new Object[][]{}, new String[]{"Id", "Nom", "Prénom", "Téléphone", "Adresse"});
        }
        tableClients = new JTable(modelTable);
        JScrollPane scrollPane = new JScrollPane(tableClients);
        panelContent.add(scrollPane, BorderLayout.CENTER);

        add(panelContent);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonRecherche) {
            String recherche = textRecherche.getText().trim();
            if (recherche.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nom ou un prénom à chercher.");
            } else {
                List<Client> clients = ClientDAO.filterClients(allClients, recherche);
				if (clients.isEmpty()) {
				    JOptionPane.showMessageDialog(this, "Aucun client trouvé.");
				} else {
				    modelTable.setRowCount(0);
				    for (Client client : clients) {
				        modelTable.addRow(new Object[]{client.getId(), client.getNom(), client.getPrenom(),
				                client.getTelephone(), client.getAdresse()});
				    }
				}
            }
        }
    }
}
