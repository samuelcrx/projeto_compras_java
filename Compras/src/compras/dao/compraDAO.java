/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.dao;

import compras.model.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author G0NN4 CRY
 */
public class compraDAO implements IDAO{ //herança por especificação

    @Override
    public void inserir(Compra compra) {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("INSERT INTO compras(dat_compra, nota_fiscal, valor_total, fornecedor) VALUES(?, ?, ?, ?)");
            
            ps.setDate(1, (java.sql.Date) new Date(Calendar.getInstance().getTimeInMillis()));
            ps.setString(2, compra.getNota_fiscal());
            ps.setDouble(3, compra.getValor_total());
//            ps.set(3, compra.getValor_total());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
    public int getIdInserido() {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
           st = con.createStatement();
           rs = st.executeQuery("SELECT LAST_INSERT_ID() AS ultimo_id");
           
           rs.first();
           
           return rs.getInt("ultimo_id");
            
        } catch (SQLException ex) {
            Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    @Override
    public void atualizar(Compra compra) {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("UPDATE compras SET dat_compra = ? WHERE id = ?");
            
            ps.setDate(1, (java.sql.Date) new Date(Calendar.getInstance().getTimeInMillis()));
            ps.setInt(2, compra.getId());
            
//          ps.setString(2, compra.getNota_fiscal());
//          ps.setDouble(3, compra.getValor_total());
//          ps.set(3, compra.getValor_total());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public Compra buscaPorId(int id) {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        ResultSet rs;
        Compra c = null;
        
        try {
            ps = con.prepareStatement("SELECT FROM compras WHERE id = ?");
            ps.setInt(2, id);
            
            rs = ps.executeQuery();
            
            rs.first();
            
            c = new Compra();
            c.setNota_fiscal(rs.getString("nota_fiscal"));
            c.setId(rs.getInt("id"));
            
            return c;
            
        } catch (SQLException ex) {
            Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return c;
    }

    @Override
    public void excluir(int id) {
        Connection con = Conexao.getConexao();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("DELETE FROM compras WHERE id = ?");
            ps.setInt(1, id);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(compraDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }    
    
}
