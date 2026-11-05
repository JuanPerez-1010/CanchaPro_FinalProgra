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

    private String codigo;
    private String nombre;
    private String correo;
    private String telefono;
    private String frecuente;
    private String fechaRegistro;
    
    public Clientes (){}

   public Clientes(String codigo, String nombre, String correo, String telefono, String frecuente, String fechaRegistro){
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.frecuente = frecuente;
        this.fechaRegistro = fechaRegistro;
    }
    public Object[] cliente() {
     return new Object[]{codigo, nombre, correo, telefono, frecuente, fechaRegistro};
    }  
    public String getCodigo(){ 
    return codigo; 
    }
    public String getNombre(){ 
    return nombre; 
    }
    public String getCorreo(){ 
    return correo; 
    }
    public String getTelefono(){
    return telefono; 
    }
    public String getFrecuente(){ 
    return frecuente; 
    }
    public String getFechaRegistro(){
    return fechaRegistro; }
    
    
    public void eliminarcliente (javax.swing.JTable tabla, int fila){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.removeRow(fila);
        }
        
    public String generarSiguienteCodigo(javax.swing.JTable tabla) {
        int maximo = 0;
        try {
            FileReader leer = new FileReader("Clientes.txt");
            BufferedReader buff = new BufferedReader(leer);
            String linea = buff.readLine();
             while (linea != null) {
              if (linea.trim().length() > 0) {
                String[] datos = linea.split(",");
               String numeroTexto = datos[0].trim().replace("C", "");
                 int numero = Integer.parseInt(numeroTexto);
                 if (numero > maximo) {
                    maximo = numero;
                    }
                }
                linea = buff.readLine();
            }
            buff.close();
            leer.close();
             } catch (Exception e) {
             }
            for (int i = 0; i < tabla.getRowCount(); i++) {
            try {
                String codTabla = tabla.getValueAt(i, 0).toString().trim().replace("C", "");
                int numTabla = Integer.parseInt(codTabla);

                if (numTabla > maximo) {
                    maximo = numTabla;
                }
            } catch (Exception e) {
            }
            }
        int siguiente = maximo + 1;
        if (siguiente < 10) {
            return "C00" + siguiente;
        } else if (siguiente < 100) {
            return "C0" + siguiente;
        } else {
            return "C" + siguiente;
        }
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
  
   
   
   
   

         
   


