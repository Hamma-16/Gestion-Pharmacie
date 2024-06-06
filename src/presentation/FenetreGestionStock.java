package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.MedicamentDAO;
import metier.Medicament;

public class FenetreGestionStock extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JLabel labelNom;
    private JLabel labelDescription;
    private JLabel labelPrix;
    private JLabel labelQuantite;
    private JTextField textNom;
    private JTextField textDescription;
    private JTextField textPrix;
    private JTextField textQuantite;
    private JButton buttonAjouter;
    private JButton buttonSupprimer;
    private JButton buttonModifier;
    private JTable tableMedicaments;
    private DefaultTableModel model;
    private MedicamentDAO medicamentDAO;

    public FenetreGestionStock() throws SQLException {
        setTitle("Gestion du stock");
        setSize(1400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panelForm = new JPanel(new FlowLayout());
        labelNom = new JLabel("Nom");
        panelForm.add(labelNom);
        textNom = new JTextField(20);
        panelForm.add(textNom);

        labelDescription = new JLabel("Description");
        panelForm.add(labelDescription);
        textDescription = new JTextField(20);
        panelForm.add(textDescription);

        labelPrix = new JLabel("Prix");
        panelForm.add(labelPrix);
        textPrix = new JTextField(20);
        panelForm.add(textPrix);

        labelQuantite = new JLabel("Quantité");
        panelForm.add(labelQuantite);
        textQuantite = new JTextField(20);
        panelForm.add(textQuantite);

        buttonAjouter = new JButton("Ajouter");
        buttonAjouter.addActionListener(this);
        panelForm.add(buttonAjouter);

        buttonModifier = new JButton("Modifier");
        buttonModifier.addActionListener(this);
        panelForm.add(buttonModifier);

        buttonSupprimer = new JButton("Supprimer");
        buttonSupprimer.addActionListener(this);
        panelForm.add(buttonSupprimer);

        add(panelForm, BorderLayout.NORTH);

        JPanel panelTable = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new String[] { "ID", "Nom", "Description", "Prix", "Quantité" }, 0);
        tableMedicaments = new JTable(model);
        panelTable.add(new JScrollPane(tableMedicaments), BorderLayout.CENTER);

        add(panelTable, BorderLayout.CENTER);

        medicamentDAO = new MedicamentDAO();
        List<Medicament> medicaments = medicamentDAO.listerMedicaments();
        for (Medicament medicament : medicaments) {
            model.addRow(new Object[] { medicament.getId(), medicament.getNom(), medicament.getDescription(),
                    medicament.getPrix(), medicament.getQuantite() });
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == buttonAjouter) {
            ajouterMedicament();
        } else if (source == buttonModifier) {
            modifierMedicament();
        } else if (source == buttonSupprimer) {
            supprimerMedicament();
        }
    }

    private void ajouterMedicament() {
        String nom = textNom.getText();
        String description = textDescription.getText();
        double prix = Double.parseDouble(textPrix.getText()); 
        int quantite = Integer.parseInt(textQuantite.getText()); 
        Medicament medicament = new Medicament(0, nom, description, prix, quantite); 
        try { 
            medicamentDAO.ajouterMedicament(medicament); 
            JOptionPane.showMessageDialog(this, "Le médicament a été ajouté avec succès."); 
            actualiserTableMedicaments(); 
        } catch (SQLException e1) { 
            e1.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du médicament : " + e1.getMessage()); 
        } 
    } 

    
    private void modifierMedicament() {
        int rowIndex = tableMedicaments.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à modifier.");
            return;
        }
        int id = (int) model.getValueAt(rowIndex, 0);
        String nom = textNom.getText();
        String description = textDescription.getText();
        double prix = Double.parseDouble(textPrix.getText());
        int quantite = Integer.parseInt(textQuantite.getText());
        Medicament medicament = new Medicament(id, nom, description, prix, quantite);
        try {
            medicamentDAO.modifierMedicament(medicament);
            JOptionPane.showMessageDialog(this, "Le médicament a été modifié avec succès.");
            actualiserTableMedicaments();
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du médicament : " + e1.getMessage());
        }
    }

    private void supprimerMedicament() {
        int rowIndex = tableMedicaments.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à supprimer.");
            return;
        }
        int id = (int) model.getValueAt(rowIndex, 0);
        try {
            medicamentDAO.supprimerMedicament(id);
            JOptionPane.showMessageDialog(this, "Le médicament a été supprimé avec succès.");
            actualiserTableMedicaments();
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du médicament : " + e1.getMessage());
        }
    }
    
    private void actualiserTableMedicaments() {
        // Effacer le contenu du tableau
        model.setRowCount(0);

        // Récupérer la liste des médicaments depuis la base de données
        List<Medicament> medicaments;
        try {
            medicaments = medicamentDAO.listerMedicaments();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Ajouter chaque médicament au tableau
        for (Medicament medicament : medicaments) {
            model.addRow(new Object[] { medicament.getId(), medicament.getNom(), medicament.getDescription(),
                    medicament.getPrix(), medicament.getQuantite() });
        }
    }


}
