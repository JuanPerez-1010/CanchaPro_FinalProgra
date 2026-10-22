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
    
    public boolean codigorepetido (String Clientes, String codigobuscar){
        boolean encontrado = false;
        try{
        FileReader leerclientes = new FileReader("Clientes.txt");
        BufferedReader buffcli = new BufferedReader(leerclientes);
        
        String temporal;
        temporal = buffcli.readLine();
        
        while (temporal!=null){
            
        if (temporal.trim().length() > 0){    
        String[] datosDeLaLinea = temporal.split(",");
        String CodGuardado = datosDeLaLinea[0].trim();
        
        if(CodGuardado.equalsIgnoreCase(codigobuscar.trim()) == true){
            encontrado = true;
           break;
        }
        
        }
        temporal = buffcli.readLine();
        }
        buffcli.close();
        
        }
        catch(IOException e){
           encontrado = false; 
        }
        return encontrado;
    }
    
    public String buscarcliente (String Clientes, String busqueda, String nombre){
    String respuesta = " no enccontrado";
    
    try {
      FileReader leerclientes = new FileReader("Clientes.txt");
      BufferedReader buffcli = new BufferedReader(leerclientes);
   
      String temporal=buffcli.readLine();
      
       while (temporal!=null){
        if (temporal.trim().length() > 0){ 
         String[] datoslinea = temporal.split(",");
         
         String CodGuardado = datoslinea[0].trim();
         String NomGuardado = datoslinea[1].trim();
         
         if(busqueda.equals("Buscar por Codigo") && CodGuardado.equalsIgnoreCase(nombre.trim())){
             respuesta = "Cliente "+nombre+
             "Código: " + datoslinea[0] + "\n" +
             "Nombre: " + datoslinea[1] + "\n" +
             "Correo: " + datoslinea[2] + "\n" +
             "Teléfono: " + datoslinea[3] + "\n" +
             "Frecuente: " + datoslinea[4];
             break;
         }
          if(busqueda.equals("Buscar por Nombre") && nombre.toLowerCase().contains(busqueda.trim().toLowerCase())){ /// ESTUDIAR
             respuesta = "Cliente "+nombre+
             "Código: " + datoslinea[0] + "\n" +
             "Nombre: " + datoslinea[1] + "\n" +
             "Correo: " + datoslinea[2] + "\n" +
             "Teléfono: " + datoslinea[3] + "\n" +
             "Frecuente: " + datoslinea[4];
             break;
         }
        }
        temporal =buffcli.readLine();
       }
       
      buffcli.close();;
      
    }
    catch(IOException e){
    respuesta ="-- ERROR EL CODIGO ---"; 
    JOptionPane.showMessageDialog(null, respuesta);
    }
    return respuesta;
    }
    
    
}
