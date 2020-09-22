/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.dao;

import compras.model.Fornecedor;
import compras.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author G0NN4 CRY
 */

public class produtoDAO implements IDAO {

    @Override
    public void inserir(Object objeto) throws BancoDeDadosException {
    }

    private int getIdInserido() throws BancoDeDadosException {
        return 0;
    }

    @Override
    public void atualizar(Object objeto) throws BancoDeDadosException {
    }

    @Override
    public Produto buscaPorId(int id) throws BancoDeDadosException {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Produto p = null;
        
        try {
            
            ps = con.prepareStatement("SELECT * FROM produtos WHERE id = ?");
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            rs.first();
            
            p = new Produto();
            
            this.preencheObjeto(p, rs);
            
        } catch (SQLException ex) {
        
            throw new BancoDeDadosException(ex.getMessage());
        
        } finally{
            try {
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage()); 
            }
        }
        
        return p;
    }

    @Override
    public int excluir(int id) throws BancoDeDadosException {
        return 0;
    }

    public ArrayList<Produto> buscaTodos() throws BancoDeDadosException {

        Connection con = Conexao.getConexao();
        Statement st = null;
        ResultSet rs = null;
        ArrayList<Produto> lista = new ArrayList<Produto>();

        try {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM produtos");

            while (rs.next()) {

                Produto p = new Produto();
                this.preencheObjeto(p, rs);

                lista.add(p);
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

    private void preencheObjeto(Produto p, ResultSet rs) throws SQLException, BancoDeDadosException {

        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setEstoque(rs.getInt("estoque"));
        p.setValor_custo(rs.getDouble("valor_custo"));
        p.setValor_venda(rs.getDouble("valor_venda"));

        Calendar n = Calendar.getInstance();
        n.setTime(rs.getDate("dat_ultima_compra"));
        p.setDat_ultima_compra(n);

        fornecedorDAO forDAO = new fornecedorDAO();
        Fornecedor f = forDAO.buscaPorId(rs.getInt("fornecedores_id"));
        p.setFornecedor(f);
    }
}
