/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.fornecedorDAO;
import compras.model.Compra;
import compras.model.Fornecedor;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Pedro
 */
public class FornecedorController {
    
     private Fornecedor fornecedor = null;
     private fornecedorDAO db;
     private ArrayList<Fornecedor> listaFornecedores;
     private int indice = 0;
        
    public FornecedorController() throws BancoDeDadosException{
        this.db = new fornecedorDAO();
        
        this.listaFornecedores = db.buscaTodos();
    }
    
    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }
    
    public ArrayList<Fornecedor> getListaTodosFornecedores() {
        return this.listaFornecedores;
    }
    
    public void salvar(String razaosocial, String nomefantasia, String cnpj, String telefone, String email, int indice) throws BancoDeDadosException{
        
        Fornecedor f = new Fornecedor();
        
        f.setRazao_social(razaosocial);
        f.setNome_fantasia(nomefantasia);
        f.setCnpj(cnpj);
        f.setTelefone(telefone);
        f.setEmail(email);
        f.setId(indice);
          
        this.db.atualizar(f);

    }
    
    public void cadastrar(String razaosocial, String nomefantasia, String cnpj, String telefone, String email) throws BancoDeDadosException{
        
        Fornecedor f = new Fornecedor();
        
        f.setRazao_social(razaosocial);
        f.setNome_fantasia(nomefantasia);
        f.setCnpj(cnpj);
        f.setTelefone(telefone);
        f.setEmail(email);
          
        this.db.inserir(f);

    }
    
    public int excluir(int indice) throws BancoDeDadosException {
        
        int reg = this.db.excluir(indice);
        
        return reg;
    
    }
      
    public void setFornecedorSelecionado(int indice) throws BancoDeDadosException {
        this.indice = indice;
        this.fornecedor = this.listaFornecedores.get(indice);
    }

}
