package DAO;

import model.Carro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarroDAO {
    private Connection conn;
    private String query;
    private PreparedStatement stmt;

    public CarroDAO(){
        this.conn = ConexaoMySQL.getInstancia().getConexao();
    }

    public ArrayList<Carro> listarTodosCarros(){
        this.query = "SELECT * FROM carros";

        ArrayList<Carro> carros = new ArrayList<>();
        try{
            this.stmt = this.conn.prepareStatement(this.query);
            ResultSet rs = this.stmt.executeQuery();
            while(rs.next()){
                Carro carro = new Carro(rs.getInt("id"),
                    rs.getString("modelo"),
                    rs.getString("marca"),
                    rs.getInt("ano"),
                    rs.getString("placa"));
                carros.add(carro);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return carros;
    }

    public Carro buscarPorId(int id) {
        this.query = "SELECT * FROM carros WHERE id = ?";
        Carro carro = null;

        try {
            this.stmt = this.conn.prepareStatement(this.query);
            this.stmt.setInt(1, id);
            ResultSet rs = this.stmt.executeQuery();

            if (rs.next()) {
                carro = new Carro(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("ano"),
                        rs.getString("placa")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return carro;
    }

    public boolean inserirCarro(Carro carro) {
        this.query = "INSERT INTO carros (modelo, marca, ano, placa) VALUES (?, ?, ?, ?)";

        try {
            this.stmt = this.conn.prepareStatement(this.query);
            this.stmt.setString(1, carro.getModelo());
            this.stmt.setString(2, carro.getMarca());
            this.stmt.setInt(3, carro.getAno());
            this.stmt.setString(4, carro.getPlaca());

            int rows = this.stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean inserirCarro(Carro carro) {
        this.query = "INSERT INTO carros (modelo, marca, ano, placa) VALUES (?, ?, ?, ?)";

        try {
            this.stmt = this.conn.prepareStatement(this.query);
            this.stmt.setString(1, carro.getModelo());
            this.stmt.setString(2, carro.getMarca());
            this.stmt.setInt(3, carro.getAno());
            this.stmt.setString(4, carro.getPlaca());

            int rows = this.stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean atualizarCarro(Carro carro) {
        this.query = "UPDATE carros SET modelo = ?, marca = ?, ano = ?, placa = ? WHERE id = ?";

        try {
            this.stmt = this.conn.prepareStatement(this.query);
            this.stmt.setString(1, carro.getModelo());
            this.stmt.setString(2, carro.getMarca());
            this.stmt.setInt(3, carro.getAno());
            this.stmt.setString(4, carro.getPlaca());
            this.stmt.setInt(5, carro.getId());

            int rows = this.stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deletarCarro(int id) {
        this.query = "DELETE FROM carros WHERE id = ?";

        try {
            this.stmt = this.conn.prepareStatement(this.query);
            this.stmt.setInt(1, id);

            int rows = this.stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
}
