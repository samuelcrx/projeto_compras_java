/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.uteis;

import compras.dao.BancoDeDadosException;
import compras.dao.IntegracaoException;
import compras.dao.fornecedorDAO;
import compras.dao.produtoDAO;
import compras.model.Compra;
import compras.model.Compra_Item;
import compras.model.Fornecedor;
import compras.model.Produto;
import compras.model.Utilitarios;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class GerenciadorIntegracao {

    private static String pathExportacao = "C:\\integracao\\compras\\exportacao\\";

    private static String pathImportacao = "C:\\integracao\\compras\\importacao\\";


    /**
     * Metodo que pega os dados e gera registros no arquivo csv da integração
     *
     * @flagIntegracao indica qual registro deve ser produzido: 0 - exportar
     * Compras Itens; 1 - exportar Compras;
     * @param operacao indica o tipo da op que está sendo realizado: 0 - novo
     * registro;
     * @param lista Lista dos arquivos que vão ser exportados
     */
    public static void produzDadosIntegracao(int flagIntegracao, int operacao, ArrayList lista) throws IOException {

        String arquivo;

        switch (flagIntegracao) {
            case 0: { //Exportação compras itens
                exportaCompraItens(operacao, lista);
                break;
            }
            case 1: { //Exportação compras
                exportaCompras(operacao, lista);
                break;
            }
        }
    }

    public static void consomeDadosIntegracao(int flagIntegracao) throws IntegracaoException, IOException, BancoDeDadosException, ParseException {

        switch (flagIntegracao) {
            case 1: { //Importar fornecedores
                consomeDadosFornecedores();
                break;
            }

            case 2: { //Importar produtos
                consomeDadosProdutos();
                break;
            }
        }
    }

    private static void exportaCompraItens(int operacao, ArrayList lista) throws IOException {

        for (Object linha : lista) {

            Compra_Item ci = (Compra_Item) linha;

            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dataFormatada = formatador.format(data);

            String conteudo = operacao + ";" + ci.getId() + ";" + ci.getQuantidade() + ";" + ci.getValor() + ";" + ci.getCompras_id() + ";" + ci.getProdutos_id() + ";" + dataFormatada;
            ManipuladorArquivo.escritor(pathExportacao + "comprasitens.csv", conteudo);
        }

    }

    private static void exportaCompras(int operacao, ArrayList lista) throws IOException {

        for (Object linha : lista) {

            Compra c = (Compra) linha;

            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dataFormatada = formatador.format(data);

            String conteudo = operacao + ";" + c.getId() + ";" + Utilitarios.formatarData(c.getDat_compra()) + ";" + c.getNota_fiscal() + ";" + c.getValor_total() + ";" + c.getFornecedorId() + ";" + dataFormatada;
            ManipuladorArquivo.escritor(pathExportacao + "compras.csv", conteudo);
        }
    }

    /* Importação dos produtos */
    private static void consomeDadosProdutos() throws IntegracaoException, IOException, BancoDeDadosException, ParseException {

        File dir = new File(pathImportacao);

        if (!dir.exists()) {
            throw new IntegracaoException("Diretório de importação " + pathImportacao + " não existe");
        }

        File arquivo = new File(pathImportacao + "produtos.csv");

        if (!arquivo.exists()) {
            throw new IntegracaoException("Não existe dados a serem integrados");
        }

        ArrayList<String> registros = ManipuladorArquivo.leitor(pathImportacao + "produtos.csv");

        for (String linha : registros) {

            //quebrar a linha com os campos correto
            String dados[] = linha.split(";");

            int op = Integer.parseInt(dados[0]);
            switch (op) {
                case 0: { //Novo registro
                    insereNovoProduto(dados);
                    break;
                }
                case 1: { //Atualização registro
                    break;
                }
                case 2: { //Exclusão registro
                    break;
                }
            }
        }

        File diretorioDestino = new File("C:\\integracao\\compras\\importados\\");

        if (!diretorioDestino.exists()) {
            diretorioDestino.mkdir();
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH;mm;ss");
        Date date = new Date();
        String dataAtual = dateFormat.format(date);

        boolean foiMovido = arquivo.renameTo(new File(diretorioDestino, arquivo.getName().replaceAll(".csv", dataAtual + ".csv")));

        if (foiMovido) {
            System.out.println("Arquivo movido com sucesso para '" + diretorioDestino.getAbsolutePath() + "'");
        } else {
            System.out.println("Erro ao mover arquivo " + arquivo.getAbsolutePath() + " para "
                    + diretorioDestino.getAbsolutePath() + "");
        }

        arquivo = null;
        dir = null;
    }

    /* Importação dos fornecedores  */
    private static void consomeDadosFornecedores() throws IntegracaoException, IOException, BancoDeDadosException {

        File dir = new File(pathImportacao);

        if (!dir.exists()) {
            throw new IntegracaoException("Diretório de importação " + pathImportacao + " não existe");
        }

        File arquivo = new File(pathImportacao + "fornecedores.csv");

        if (!arquivo.exists()) {
            throw new IntegracaoException("Não existe dados a serem integrados");
        }

        ArrayList<String> registros = ManipuladorArquivo.leitor(pathImportacao + "fornecedores.csv");

        for (String linha : registros) {

            //quebrar a linha com os campos correto
            String dados[] = linha.split(";");

            int op = Integer.parseInt(dados[0]);

            switch (op) {
                case 0: { //Novo registro
                    insereNovoFornecedor(dados);
                    break;
                }
                case 1: { //Atualização registro
                    break;
                }
                case 2: { //Exclusão registro
                    break;
                }
            }
        }

        File diretorioDestino = new File("C:\\integracao\\compras\\importados\\");

        if (!diretorioDestino.exists()) {
            diretorioDestino.mkdir();
        }

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy'T'HH;mm;ss");
        String dataAtual = formatador.format(data);
        System.out.println(dataAtual);
        boolean foiMovido = arquivo.renameTo(new File(diretorioDestino, arquivo.getName().replaceAll(".csv", dataAtual + ".csv")));

        if (foiMovido) {
            System.out.println("Arquivo movido com sucesso para '" + diretorioDestino.getAbsolutePath() + "'");
        } else {
            System.out.println("Erro ao mover arquivo " + arquivo.getAbsolutePath() + " para "
                    + diretorioDestino.getAbsolutePath() + "");
        }

        arquivo = null;
        dir = null;
    }

    private static void insereNovoFornecedor(String dados[]) throws BancoDeDadosException {

        fornecedorDAO dao = new fornecedorDAO();

        int id = Integer.parseInt(dados[1]);

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setId(id);
        fornecedor.setRazao_social(dados[2]);
        fornecedor.setNome_fantasia(dados[3]);
        fornecedor.setCnpj(dados[4]);
        fornecedor.setTelefone(dados[5]);
        fornecedor.setEmail(dados[6]);

        dao.inserir(fornecedor);

        dao = null;
        fornecedor = null;
    }

    private static void insereNovoProduto(String dados[]) throws BancoDeDadosException, ParseException {
        produtoDAO dao = new produtoDAO();

        int id = Integer.parseInt(dados[1]);

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        Calendar n = Calendar.getInstance();
        n.setTime(formatador.parse(dados[6]));

        fornecedorDAO forDAO = new fornecedorDAO();
        Fornecedor f = forDAO.buscaPorId(Integer.parseInt(dados[7]));

        Produto produto = new Produto();

        produto.setId(id);
        produto.setNome(dados[2]);
        produto.setEstoque(Integer.parseInt(dados[3]));
        produto.setValor_custo(Double.parseDouble(dados[4]));
        produto.setValor_venda(Double.parseDouble(dados[5]));
        produto.setDat_ultima_compra(n);
        produto.setFornecedor(f);

        dao.inserir(produto);

        dao = null;
        produto = null;
    }
}
