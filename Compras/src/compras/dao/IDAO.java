/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.dao;

import compras.model.Compra;

/**
 *
 * @author G0NN4 CRY
 */
public interface IDAO {
    
    //Insert
    public abstract void inserir(Compra compra);
    
    //update
    public abstract void atualizar(Compra compra);
    
    //select
    public abstract Compra buscaPorId(int Id);
    
    //delete
    public abstract void excluir(int id);
    
}
