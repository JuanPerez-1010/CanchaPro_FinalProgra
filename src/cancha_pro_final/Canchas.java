/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author PERSONAL
 */
public class Canchas {
    private String CodidoC;
    private String NombreC;
    private String CapacidadC;
    private double PrecioBase;
    private String estadoC;
    private String horaApertura;
    private String horaCierre;
    
    
    public Canchas (){}
    
   public Canchas(String CodidoC,String NombreC,String CapacidadC,double PrecioBase, String estadoC, String horaApertura, String horaCierre){
       this.CodidoC=CodidoC;
       this.NombreC=NombreC;
       this.CapacidadC=CapacidadC;
       this.PrecioBase=PrecioBase;
       this.horaApertura = horaApertura;
       this.horaCierre = horaCierre; 
   }
     
   public String generasiguicod(javax.swing.JTable tabla) {
        int maximo = 0;
        try {
            FileReader leer = new FileReader("Canchas.txt");
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
            return "k00" + siguiente;
        } else if (siguiente < 100) {
            return "K0" + siguiente;
        } else {
            return "K" + siguiente;
        }
    }
   
   public void CargarCanchas(JTable tablecanchas){
    DefaultTableModel modelo = (DefaultTableModel) tablecanchas.getModel();
        modelo.setRowCount(0);
        try {
        FileReader leer = new FileReader("Canchas.txt");
        BufferedReader buff = new BufferedReader(leer);

        String linea = buff.readLine();

        while (linea != null) {
            if (!linea.trim().equals("")) {
                String[] datos = linea.split(",");
                String codigo    = datos[0];
                String nombre    = datos[1];
                String tipo      = datos[2];
                String capacidad = datos[3];
                String precio    = datos[4];
                String horario   = datos[5];
                String estado    = datos[6];
             modelo.addRow(new Object[]{codigo, nombre, tipo, capacidad, precio, horario, estado});
            }
            linea = buff.readLine();
              }
        buff.close();
        leer.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "No a cargado");
    }
    generasiguicod(tablecanchas);    
   }
   
   
  
    
    
    
    
   
   
   
   
   
   
   
   
   
   
}



