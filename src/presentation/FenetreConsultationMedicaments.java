package presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.MedicamentDAO;
import metier.Medicament;

public class FenetreConsultationMedicaments extends JFrame {
    private JPanel panelContent;
    private JTextField textRecherche;
    private JButton buttonRecherche;
    private JTable tableMedicaments;
    private DefaultTableModel modelTable;
    private List<Medicament> allMedicaments;

    public FenetreConsultationMedicaments() {
        setTitle("Consultation des médicaments");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 300);

        panelContent = new JPanel();
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContent.setLayout(new BorderLayout());

        JPanel panelRecherche = new JPanel();
        panelRecherche.setLayout(new BoxLayout(panelRecherche, BoxLayout.X_AXIS));
        panelRecherche.setBorder(new EmptyBorder(0, 0, 10, 0));
        textRecherche = new JTextField();
        textRecherche.setToolTipText("Entrez le nom du médicament à chercher");
        panelRecherche.add(textRecherche);
        buttonRecherche = new JButton("Chercher");
        buttonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recherche = textRecherche.getText().trim();
                if (recherche.isEmpty()) {
                    JOptionPane.showMessageDialog(FenetreConsultationMedicaments.this, "Veuillez entrer le nom du médicament à chercher.");
                } else {
                    List<Medicament> medicaments = MedicamentDAO.filterMedicaments(allMedicaments, recherche);
                    if (medicaments.isEmpty()) {
                        JOptionPane.showMessageDialog(FenetreConsultationMedicaments.this, "Aucun médicament trouvé.");
                    } else {
                        modelTable.setRowCount(0);
                        for (Medicament medicament : medicaments) {
                            modelTable.addRow(new Object[]{medicament.getId(), medicament.getNom(), medicament.getPrix(),
                                    medicament.getQuantite()});
                        }
                    }
                }
            }
        });
        panelRecherche.add(buttonRecherche);
        panelContent.add(panelRecherche, BorderLayout.NORTH);

        try {
            MedicamentDAO dao = new MedicamentDAO();
            allMedicaments = dao.listerMedicaments();
            modelTable = new DefaultTableModel(new Object[][]{}, new String[]{"Id", "Nom", "Prix", "Stock"});
            for (Medicament medicament : allMedicaments) {
                modelTable.addRow(new Object[]{medicament.getId(), medicament.getNom(), medicament.getPrix(),
                        medicament.getQuantite()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des médicaments : " + ex.getMessage());
            modelTable = new DefaultTableModel(new Object[][]{}, new String[]{"Id", "Nom", "Prix", "Stock"});
        }
        tableMedicaments = new JTable(modelTable);
        JScrollPane scrollPane = new JScrollPane(tableMedicaments);
        panelContent.add(scrollPane, BorderLayout.CENTER);

        add(panelContent);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

