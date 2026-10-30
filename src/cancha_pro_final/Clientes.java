/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Clientes  {

        String nombre;

   
        
        
        
        
        
        
        
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
    
    public void buscarcliente (javax.swing.JTable tabla, String busqueda, String nombre){
    
        boolean encontrado = false;
        String textoBuscar = nombre.toLowerCase().trim();
       
       for (int i = 0; i < tabla.getRowCount(); i++) {
        String datoCelda = ""; 
        
        if (busqueda.equals("Buscar por Codigo")) {
            datoCelda = tabla.getValueAt(i, 0).toString().toLowerCase(); // Columna 0: Código
        } else if (busqueda.equals("Buscar por Nombre")) {
            datoCelda = tabla.getValueAt(i, 1).toString().toLowerCase(); // Columna 1: Nombre
        }
        if (datoCelda.contains(textoBuscar)) {
        tabla.setRowSelectionInterval(i, i); 
            encontrado = true;
            break;
        }
    }
       if (!encontrado) {
        JOptionPane.showMessageDialog(null, "Cliente no encontrado en la tabla.");
    }
    }
    
   public void eliminarclientes( String CodiEliminar ){
       try{
        FileReader leerclientes = new FileReader("Clientes.txt");
        BufferedReader buffcli = new BufferedReader(leerclientes);

        String temporal=buffcli.readLine();
        String codinuevo = "";
        
         while (temporal!=null){
          if (temporal.trim().length() > 0){ 
           String[] datoslinea = temporal.split(",");
                if (!datoslinea[0].trim().equalsIgnoreCase(CodiEliminar.trim())){    // estudiar 
                    codinuevo = codinuevo + temporal+"\n";
                }
               }
            temporal =buffcli.readLine();
            }
            buffcli.close();
            leerclientes.close();
           FileWriter remplazo = new FileWriter ("Clientes.txt");
           remplazo.write(codinuevo); 
           remplazo.close();
          }
          catch(IOException e){ 
              JOptionPane.showMessageDialog(null, "Error al eliminar ");
            }
       
         }
 
   public void actulizarcliente(String codigo, String nnombre, String ncorreo, String ntelefono,String nfrecuencia){
 
    try{
        FileReader leerclientes = new FileReader("Clientes.txt");
        BufferedReader buffcli = new BufferedReader(leerclientes);

        String temporal=buffcli.readLine();
        String codinuevo = "";
        
         while (temporal!=null){
          if (temporal.trim().length() > 0){ 
           String[] datoslinea = temporal.split(",");
   
            if (datoslinea[0].trim().equalsIgnoreCase(codigo.trim())){
             codinuevo = codinuevo + codigo + ","+nnombre+","+ncorreo+","+ntelefono+","+nfrecuencia+"\n";
          }else{
            codinuevo=codinuevo +temporal+"\n";
                }
                }
          temporal =buffcli.readLine();
             }
         leerclientes.close();
         buffcli.close();
         
         FileWriter reescribir = new FileWriter("Clientes.txt");
         reescribir.write(codinuevo);
         reescribir.close();
         }
          catch(IOException e){ 
        JOptionPane.showMessageDialog(null, "Error al Actulizar ");
        }
         }
   
   
   
   
   
   
   
   
}
         
   


