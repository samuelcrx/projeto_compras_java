/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.fornecedorDAO;
import compras.dao.produtoDAO;
import compras.model.Fornecedor;
import compras.model.Produto;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Pedro
 */
public class ProdutosController {

    private Produto produto = null;
    private produtoDAO db;
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Fornecedor> listaFornecedores;
    private int indice = 0;

    public ProdutosController() throws BancoDeDadosException, ParseException {
        this.db = new produtoDAO();
        this.listaProdutos = db.buscaTodos();
    }

    public Produto getProdutos() {
        return this.produto;
    }

    public void setProdutoSelecionado(int indice) throws BancoDeDadosException {
        this.indice = indice;
        this.produto = this.listaProdutos.get(indice);
    }

    public void cadastrar(String nome, int estoque, Double valor_custo, Double valor_venda, int indiceFornecedor, String dat_ultima_compra) throws BancoDeDadosException {

        Produto p = new Produto();

        p.setNome(nome);
        p.setEstoque(estoque);
        p.setValor_custo(valor_custo);
        p.setValor_venda(valor_venda);

        Fornecedor f = this.listaFornecedores.get(indiceFornecedor);
        p.setFornecedor(f);

        Calendar ultimaCompra = Calendar.getInstance();
        Date d = new Date(dat_ultima_compra);
        ultimaCompra.setTime(d);

        p.setDat_ultima_compra(ultimaCompra);

        this.db.inserir(p);

        this.listaProdutos.add(p);

    }

    public void salvar(String nome, int estoque, Double valor_custo, Double valor_venda, int indiceFornecedor, String dat_ultima_compra) throws BancoDeDadosException {
        Produto produto;
        
        if (this.produto != null) {

            produto = this.produto;

            produto.setNome(nome);
            produto.setEstoque(estoque);
            produto.setValor_custo(valor_custo);
            produto.setValor_venda(valor_venda);
            
            buscarTodoOsFornecedores();
            Fornecedor f = this.listaFornecedores.get(indiceFornecedor);
            produto.setFornecedor(f);

            Calendar ultimaCompra = Calendar.getInstance();
            Date d = new Date(dat_ultima_compra);
            ultimaCompra.setTime(d);

            produto.setDat_ultima_compra(ultimaCompra);

            this.listaProdutos.set(indice, produto);

            this.db.atualizar(produto);

            this.indice = 0;
            this.produto = null;

        }

    }

    public void buscarTodoOsFornecedores() throws BancoDeDadosException {
        fornecedorDAO fdao = new fornecedorDAO();
        this.listaFornecedores = fdao.buscaTodos();
        fdao = null;
    }

    public int excluir(int indice) throws BancoDeDadosException {
        
        int reg = this.db.excluir(indice);
        
        return reg;
    
    }
    
    public ArrayList<Fornecedor> getListaFornecedoresCadastrados() {
        return this.listaFornecedores;
    }

    public ArrayList<Produto> getListaProdutos() {
        return this.listaProdutos;
    }

}
