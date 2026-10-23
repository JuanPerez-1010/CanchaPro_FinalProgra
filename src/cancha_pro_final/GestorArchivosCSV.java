/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author PERSONAL
 */
public class GestorArchivosCSV {
    
    public void GuardarCliente(String datos){
        try{
        FileWriter abrirclientes = new FileWriter ("Clientes.txt",true);
        BufferedWriter buffercliente = new BufferedWriter (abrirclientes);
         
        buffercliente.write(datos);
        buffercliente.newLine();
        buffercliente.close();
        }
        catch(IOException e){
        JOptionPane.showMessageDialog (null,"-- Error en el Archivo Clientes ----");
        }   
    }
    
   
    
}
