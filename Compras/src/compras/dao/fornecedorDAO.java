/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.dao;

import compras.model.Compra;
import compras.model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author G0NN4 CRY
 */
public class fornecedorDAO implements IDAO {

    @Override
    public void inserir(Object objeto) throws BancoDeDadosException {
        Fornecedor f = (Fornecedor) objeto; //cast

        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("INSERT INTO fornecedores(razao_social, nome_fantasia, cnpj, telefone, email) VALUE(?, ?, ?, ?, ?)");
            ps.setString(1, f.getRazao_social());
            ps.setString(2, f.getNome_fantasia());
            ps.setString(3, f.getCnpj());
            ps.setString(4, f.getTelefone());
            ps.setString(5, f.getEmail());

            if (ps.executeUpdate() > 0) {
                f.setId(this.getIdInserido());
            }

        } catch (SQLException ex) {

            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage());
            }
        }
    }

    private int getIdInserido() throws BancoDeDadosException {

        Connection con = Conexao.getConexao();
        Statement st = null;
        ResultSet rs = null;

        try {

            st = con.createStatement();
            rs = st.executeQuery("SELECT LAST_INSERT_ID() AS ultimo_id");

            rs.first();

            return rs.getInt("ultimo_id");

        } catch (SQLException ex) {

            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage());
            }
        }
    }

    @Override
    public void atualizar(Object objeto) throws BancoDeDadosException {
        Fornecedor f = (Fornecedor) objeto; //cast

        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("UPDATE fornecedores SET razao_social = ?, nome_fantasia = ?, cnpj = ?, telefone = ?, email = ? WHERE id = ?");
            ps.setString(1, f.getRazao_social());
            ps.setString(2, f.getNome_fantasia());
            ps.setString(3, f.getCnpj());
            ps.setString(4, f.getTelefone());
            ps.setString(5, f.getEmail());
            ps.setInt(6, f.getId());

            ps.executeUpdate();

        } catch (SQLException ex) {

            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage());
            }
        }
    }

    @Override
    public Fornecedor buscaPorId(int id) throws BancoDeDadosException {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Fornecedor f = null;

        try {

            ps = con.prepareStatement("SELECT * FROM fornecedores WHERE id = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            rs.first();

            f = new Fornecedor();

            this.preencheObjeto(f, rs);

        } catch (SQLException ex) {

            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage());
            }
        }
        return f;
    }

    @Override
    public int excluir(int id) throws BancoDeDadosException {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        
        int totRegistroAfetado = 0;
        
        try {
            
            ps = con.prepareStatement("DELETE FROM fornecedores WHERE id = ?");
            ps.setInt(1, id);
            
            totRegistroAfetado = ps.executeUpdate();
            
            
        } catch (SQLException ex) {
        
            throw new BancoDeDadosException(ex.getMessage());
        
        } finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage()); 
            }
        }
        
        return totRegistroAfetado;        
    }

    public ArrayList<Fornecedor> buscaTodos() throws BancoDeDadosException {

        Connection con = Conexao.getConexao();
        Statement st = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> lista = new ArrayList<Fornecedor>();

        try {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM fornecedores");

            while (rs.next()) {

                Fornecedor f = new Fornecedor();
                this.preencheObjeto(f, rs);

                lista.add(f);
            }

        } catch (SQLException ex) {

            throw new BancoDeDadosException(ex.getMessage());

        } finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage());
            }
        }
        return lista;
    }

    private void preencheObjeto(Fornecedor f, ResultSet rs) throws SQLException, BancoDeDadosException {

        f.setId(rs.getInt("id"));
        f.setRazao_social(rs.getString("razao_social"));
        f.setNome_fantasia(rs.getString("nome_fantasia"));
        f.setCnpj(rs.getString("cnpj"));
        f.setTelefone(rs.getString("telefone"));
        f.setEmail(rs.getString("email"));
    }
}
