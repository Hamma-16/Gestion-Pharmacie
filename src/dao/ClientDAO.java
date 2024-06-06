package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Client;

public class ClientDAO {
    private static Connection connection;

    public ClientDAO() throws SQLException {
        connection = SingletonConnection.getInstance();
    }

    public void ajouterClient(Client client) throws SQLException {
        String query = "INSERT INTO client (nom, prenom, telephone, adresse) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getNom());
        statement.setString(2, client.getPrenom());
        statement.setString(3, client.getTelephone());
        statement.setString(4, client.getAdresse());
        statement.executeUpdate();
    }

    public void supprimerClient(int id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void modifierClient(Client client) throws SQLException {
        String query = "UPDATE client SET nom = ?, prenom = ?, telephone = ?, adresse = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getNom());
        statement.setString(2, client.getPrenom());
        statement.setString(3, client.getTelephone());
        statement.setString(4, client.getAdresse());
        statement.setInt(5, client.getId());
        statement.executeUpdate();
    }

    public static List<Client> listerClients() throws SQLException {
        String query = "SELECT * FROM client";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Client> clients = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String telephone = resultSet.getString("telephone");
            String adresse = resultSet.getString("adresse");
            Client client = new Client(id, nom, prenom, telephone, adresse);
            clients.add(client);
        }
        return clients;
    }

    public Client trouverClientParId(int id) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String telephone = resultSet.getString("telephone");
            String adresse = resultSet.getString("adresse");
            return new Client(id, nom, prenom, telephone, adresse);
        }
        return null;
    }


    public static List<Client> filterClients(List<Client> allClients, String recherche) {
        if (recherche == null || recherche.trim().isEmpty()) {
            // Si la chaîne de recherche est vide ou nulle, retourner la liste complète des clients
            return allClients;
        }
        List<Client> filteredClients = new ArrayList<>();
        for (Client client : allClients) {
            // Rechercher si la chaîne de recherche est contenue dans le nom, le prénom, le téléphone ou l'adresse du client
            if (client.getNom().toLowerCase().contains(recherche.toLowerCase())
                    || client.getPrenom().toLowerCase().contains(recherche.toLowerCase())
                    || client.getTelephone().contains(recherche)
                    || client.getAdresse().toLowerCase().contains(recherche.toLowerCase())) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }


}