package br.com.trabalho.dao;

import br.com.trabalho.model.Carta;
import br.com.trabalho.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a tabela 'cartas'.
 * Responsável por buscar as cartas do banco MySQL.
 */
public class CartaDAO {

    /**
     * Retorna todas as cartas cadastradas no banco.
     */
    public List<Carta> listarTodas() {
        List<Carta> cartas = new ArrayList<>();
        String sql = "SELECT id, nome, codigo, ano_criacao, peso_kg, preco, popularidade, volume_db, super_trunfo FROM cartas";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Carta carta = new Carta(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("codigo"),
                    rs.getInt("ano_criacao"),
                    rs.getDouble("peso_kg"),
                    rs.getDouble("preco"),
                    rs.getInt("popularidade"),
                    rs.getInt("volume_db"),
                    rs.getBoolean("super_trunfo")
                );
                cartas.add(carta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cartas: " + e.getMessage(), e);
        }

        return cartas;
    }
}
