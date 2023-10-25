package com.divergent_mindverse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClinicaDAO {
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

    public int insertClinica(int idNeurodivergencia) {
        String sql = "INSERT INTO clinica (id_neurodivergencia) VALUES (?) RETURNING id_clinica";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNeurodivergencia);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateClinica(int idClinica, int idNeurodivergencia) {
        String sql = "UPDATE clinica SET id_neurodivergencia = ? WHERE id_clinica = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNeurodivergencia);
            preparedStatement.setInt(2, idClinica);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClinica(int idClinica) {
        String sql = "DELETE FROM clinica WHERE id_clinica = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idClinica);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Clinica getClinica(int idClinica) {
        String sql = "SELECT * FROM clinica WHERE id_clinica = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idClinica);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idNeurodivergencia = resultSet.getInt("id_neurodivergencia");
                return new Clinica(idClinica, idNeurodivergencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Clinica> getAllClinicas() {
        List<Clinica> clinicas = new ArrayList<>();
        String sql = "SELECT * FROM clinica";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int idClinica = resultSet.getInt("id_clinica");
                int idNeurodivergencia = resultSet.getInt("id_neurodivergencia");
                clinicas.add(new Clinica(idClinica, idNeurodivergencia));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clinicas;
    }
}
