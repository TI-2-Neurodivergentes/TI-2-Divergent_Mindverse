package com.divergent_mindverse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GruposApoioDAO {
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

    public int insertGrupoApoio(int idNeurodivergencia) {
        String sql = "INSERT INTO grupos_apoio (id_neurodivergencia) VALUES (?) RETURNING id_grupo";
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

    public void updateGrupoApoio(int idGrupoApoio, int idNeurodivergencia) {
        String sql = "UPDATE grupos_apoio SET id_neurodivergencia = ? WHERE id_grupo = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idNeurodivergencia);
            preparedStatement.setInt(2, idGrupoApoio);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrupoApoio(int idGrupoApoio) {
        String sql = "DELETE FROM grupos_apoio WHERE id_grupo = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGrupoApoio);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GrupoApoio getGrupoApoio(int idGrupoApoio) {
        String sql = "SELECT * FROM grupos_apoio WHERE id_grupo = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGrupoApoio);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idNeurodivergencia = resultSet.getInt("id_neurodivergencia");
                return new GrupoApoio(idGrupoApoio, idNeurodivergencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GrupoApoio> getAllGruposApoio() {
        List<GrupoApoio> gruposApoio = new ArrayList<>();
        String sql = "SELECT * FROM grupos_apoio";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int idGrupoApoio = resultSet.getInt("id_grupo");
                int idNeurodivergencia = resultSet.getInt("id_neurodivergencia");
                gruposApoio.add(new GrupoApoio(idGrupoApoio, idNeurodivergencia));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gruposApoio;
    }
}
