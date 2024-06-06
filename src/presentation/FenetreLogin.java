package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class FenetreLogin extends JFrame {
    private JLabel loginLabel;
    private JLabel pwdLabel;
    private JTextField loginField;
    private JPasswordField pwdField;
    private JButton btnValider;

    public FenetreLogin() {
        // Initialisation des composants de la fenêtre
        loginLabel = new JLabel("Login : ");
        pwdLabel = new JLabel("Mot de passe : ");
        loginField = new JTextField();
        pwdField = new JPasswordField();
        btnValider = new JButton("Valider");

        // Disposition des composants
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(pwdLabel);
        panel.add(pwdField);
        panel.add(btnValider);
        panel.setBackground(new Color(230, 230, 250)); // Fond du panel en gris clair

        // Ajout d'une bordure au panel contenant les composants
        Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), border));

        // Changement de la police et de la taille des labels
        Font font = new Font("Arial", Font.PLAIN, 16);
        loginLabel.setFont(font);
        pwdLabel.setFont(font);

        // Changement de la police et de la taille du bouton
        btnValider.setFont(new Font("Arial", Font.BOLD, 16));

        // Ajout des composants à la fenêtre
        setContentPane(panel);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(pwdField.getPassword());
                if (login.equalsIgnoreCase("ad") && password.equals("ad")) {
                    InterfaceAdmin ia = new InterfaceAdmin();
                    ia.setVisible(true);
                    dispose(); // ferme la fenêtre de login
                } else if (login.equalsIgnoreCase("ph") && password.equals("ph")) {
                    InterfacePharmacien ip = new InterfacePharmacien();
                    ip.setVisible(true);
                    dispose(); // ferme la fenêtre de login
                } else {
                    JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect!");
                }
            }
        });

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null); // centre la fenêtre sur l'écran
        setResizable(false);
        setVisible(true);
    }

}
