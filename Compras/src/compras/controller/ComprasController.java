package compras.controller;

import compras.dao.BancoDeDadosException;
import compras.dao.compraDAO;
import compras.model.Compra;
import compras.model.Fornecedor;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Pedro
 */
public class ComprasController {
    
     private compraDAO db;
     private Fornecedor fornecedor = null;
        
    public ComprasController() throws BancoDeDadosException{
        this.db = new compraDAO();
    }
    
    public void salvar(Calendar dat_compra, String nota_fiscal, Double valor_total, String fornecedor) throws BancoDeDadosException{
        
 
        
        Compra c = new Compra();
        
        c.setDat_compra(dat_compra);
        c.setNota_fiscal(nota_fiscal);
        c.setValor_total(valor_total);
        /*
        c.setFornecedor(fornecedor);
        
        
        Calendar calendar = Calendar.getInstance();
        Date dat = new Date(data);
        calendar.setTime(dat);
        
        c.setDatContato(calendar);
        
        this.db.inserir(c);
        
        this.listaDeContatosCadastrados.add(c);
*/
    }

}
