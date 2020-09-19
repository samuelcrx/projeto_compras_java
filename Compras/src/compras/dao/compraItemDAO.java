/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.dao;

import compras.model.Compra;
import compras.model.Compra_Item;
import compras.model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author G0NN4 CRY
 */
public class compraItemDAO implements IDAO{

    @Override
    public void inserir(Object objeto) throws BancoDeDadosException {
        Compra_Item ci = (Compra_Item)objeto; //cast
        
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        
        try {
            
            ps = con.prepareStatement("INSERT INTO compras_itens(quantidade, valor, compras_id, produtos_id) VALUE(?, ?, ?, ?)");
            ps.setInt(1, ci.getQuantidade());
            ps.setDouble(2, ci.getValor());
            ps.setInt(3, ci.getCompras_id());
            ps.setInt(4, ci.getProdutos_id());
            
            if ( ps.executeUpdate() > 0 )
                ci.setId( this.getIdInserido() );
            
            
        } catch (SQLException ex) {
        
            throw new BancoDeDadosException(ex.getMessage());           
        
        } finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new BancoDeDadosException(ex.getMessage()); 
            }
        }
    }
    
    private int getIdInserido() throws BancoDeDadosException{
        
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
        
        } finally{
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
        
    }

    @Override
    public Object buscaPorId(int id) throws BancoDeDadosException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int excluir(int id) throws BancoDeDadosException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Fornecedor> buscaTodos() throws BancoDeDadosException {

        Connection con = Conexao.getConexao();
        Statement st = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> lista = new ArrayList<Fornecedor>();

        try {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM fornecedores ORDER BY nome");

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

        // razao_social, nome_fantasia, cnpj, telefone, email
        f.setRazao_social(rs.getString("razao_social"));
        f.setId(rs.getInt("id"));
        f.setNome_fantasia(rs.getString("nome_fantasia"));
        f.setCnpj(rs.getString("cnpj"));
        f.setTelefone(rs.getString("telefone"));
        f.setEmail(rs.getString("email"));
    }

}
