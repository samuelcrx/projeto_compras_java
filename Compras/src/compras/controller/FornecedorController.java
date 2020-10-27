/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.IntegracaoException;
import compras.dao.fornecedorDAO;
import compras.model.Fornecedor;
import compras.uteis.GerenciadorIntegracao;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class FornecedorController {

    private Fornecedor fornecedor = null;
    private fornecedorDAO db;
    private ArrayList<Fornecedor> listaFornecedores;

    public FornecedorController() throws BancoDeDadosException {
        this.db = new fornecedorDAO();

        this.listaFornecedores = db.buscaTodos();
    }

    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    public ArrayList<Fornecedor> getListaTodosFornecedores() {
        return this.listaFornecedores;
    }
    
     public void importarDados() throws IntegracaoException, IOException, BancoDeDadosException, ParseException {

        GerenciadorIntegracao.consomeDadosIntegracao(1);
    }

}
