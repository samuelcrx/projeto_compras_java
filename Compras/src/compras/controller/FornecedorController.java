/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.fornecedorDAO;
import compras.model.Fornecedor;
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

}
