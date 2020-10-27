/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.IntegracaoException;
import compras.dao.produtoDAO;
import compras.model.Fornecedor;
import compras.model.Produto;
import compras.uteis.GerenciadorIntegracao;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class ProdutosController {

    private Produto produto = null;
    private produtoDAO db;
    private ArrayList<Produto> listaProdutos;
    private int indice = 0;

    public ProdutosController() throws BancoDeDadosException, ParseException {
        this.db = new produtoDAO();
        this.listaProdutos = db.buscaTodos();
    }

    public Produto getProdutos() {
        return this.produto;
    }

    public ArrayList<Produto> getListaProdutos() {
        return this.listaProdutos;
    }
    
    public void importarDados() throws IntegracaoException, IOException, BancoDeDadosException, ParseException {

        GerenciadorIntegracao.consomeDadosIntegracao(2);
    }

}
