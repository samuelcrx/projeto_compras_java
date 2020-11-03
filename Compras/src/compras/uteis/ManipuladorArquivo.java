/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compras.uteis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Pedro
 */
public class ManipuladorArquivo {

    public static void escritor(String path, String conteudo) throws IOException {

        FileWriter arquivo = new FileWriter(path, true);

        BufferedWriter buffer = new BufferedWriter(arquivo);

        Scanner a = new Scanner(System.in);
        
        buffer.append(conteudo+"\n");
        
        
        buffer.close();
    }
    
    public static ArrayList<String> leitor(String path) throws FileNotFoundException, IOException {
        String linha = "";

            FileReader leitor = new FileReader(path);

            BufferedReader buffer = new BufferedReader(leitor);
            
            ArrayList<String> registros = new ArrayList<String>();

            while (true) {

                if (linha != null) {

                    if(!linha.equals("")) {
                        
                       registros.add(linha);
                    
                    }

                } else {
                    break;
                }

                linha = buffer.readLine();
                
            }
            
            buffer.close();
            
            return registros;
    }
}
