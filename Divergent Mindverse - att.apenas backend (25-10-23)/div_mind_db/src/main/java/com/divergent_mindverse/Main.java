package com.divergent_mindverse;

import java.sql.Connection;
import java.sql.SQLException;
//###################################################################
//          Esta main È apenas um Teste, Utilizar breakpoints
//
//          Colocar os dados do postgree corretos nos DAOs
//
//###################################################################
public class Main {
    public static void main(String[] args) {

        CreateDatabase.createDatabase();

        Connection connection = null;
        try {
            UsuariosDAO usuariosDAO = new UsuariosDAO();
            
            // Creating a user and retrieving the user ID
            int newUserId = usuariosDAO.insertUsuario(1, "testuser", "testpass", "Test User", "testuser@example.com");
            System.out.println("Created user with ID: " + newUserId);

            // Retrieving a user
            Usuario retrievedUser = usuariosDAO.getUsuario(newUserId);
            if (retrievedUser != null) {
                System.out.println("Retrieved user:");
                System.out.println("ID: " + retrievedUser.getIdUsuario());
                System.out.println("Name: " + retrievedUser.getNome());
                System.out.println("Email: " + retrievedUser.getEmail());
            } else {
                System.out.println("User not found.");
            }

            // Updating a user
            usuariosDAO.updateUsuario(newUserId, 1, "newtestuser", "newtestpass", "New Test User", "newtestuser@example.com");
            System.out.println("User updated.");

            // Deleting a user
            usuariosDAO.deleteUsuario(newUserId);
            System.out.println("User deleted.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
