package com.divergent_mindverse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.divergent_mindverse.model.Usuario;

public class UsuariosDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ti2bd";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "PGmo1234";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void executeSQL(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertUsuario(int idNeurodivergencia, String login, String senha, String nome, String email) {
        String sql = "INSERT INTO usuario (id_neurodivergencia, login, senha, nome, email) VALUES (?, ?, ?, ?, ?) RETURNING id_usuario";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNeurodivergencia);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, nome);
            preparedStatement.setString(5, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Usuario getUsuario(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idNeurodivergencia = resultSet.getInt("id_neurodivergencia");
                String login = resultSet.getString("login");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                return new Usuario(idUsuario, idNeurodivergencia, login, senha, nome, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUsuario(int idUsuario, int idNeurodivergencia, String login, String senha, String nome, String email) {
        String sql = "UPDATE usuario SET id_neurodivergencia = ?, login = ?, senha = ?, nome = ?, email = ? WHERE id_usuario = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNeurodivergencia);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, nome);
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}