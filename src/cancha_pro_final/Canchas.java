/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    private String tipoC;
    private double PrecioBase;
    private String estadoC;
    private String horaApertura;
    private String horaCierre;
    
    
    public Canchas (){}
    
   public Canchas(String CodidoC,String NombreC,String CapacidadC,String tipoC,double PrecioBase, String estadoC, String horaApertura, String horaCierre){
       this.CodidoC=CodidoC;
       this.NombreC=NombreC;
       this.CapacidadC=CapacidadC;
       this.tipoC=tipoC;
       this.PrecioBase=PrecioBase;
       this.estadoC =estadoC;
       this.horaApertura = horaApertura;
       this.horaCierre = horaCierre; 
   }
   
    public String getCodigoC(){ 
    return CodidoC;
    }
    public void setCodigoC(String codigoC){ 
    this.CodidoC = codigoC; 
    }
    public String getNombreC(){ 
    return NombreC; 
    }
    public void setNombreC(String nombreC){ 
    this.NombreC = NombreC; 
    }
    public String getTipoC(){ 
    return tipoC; 
    }
    public void setTipoC(String tipoC){ 
    this.tipoC = tipoC; 
    }
    public String getCapacidadC(){
    return CapacidadC; 
    }
    public void setCapacidadC(String capacidadC){ 
    this.CapacidadC = capacidadC; 
    }
    public double getPrecioBase(){ 
    return PrecioBase; 
    }
    public void setPrecioBase(double precioBase){ 
    this.PrecioBase = precioBase; 
    }
    public String getEstadoC(){ 
    return estadoC; 
    }
    public void setEstadoC(String estadoC){ 
    this.estadoC = estadoC; 
    }
    public String getHoraApertura(){ 
    return horaApertura; 
    }
    public void setHoraApertura(String horaApertura){ 
    this.horaApertura = horaApertura; 
    }
    public String getHoraCierre(){ 
    return horaCierre; 
    }
    public void setHoraCierre(String horaCierre){ 
    this.horaCierre = horaCierre; 
    }
    
    public String generasiguicod(Canchas[] arreglo, int contador) {
       int maximo = 0;
       for (int i = 0; i < contador; i++) {
        if (arreglo[i] != null && arreglo[i].getCodigoC() != null) {
        try {
            String codTexto = arreglo[i].getCodigoC().substring(1);
            int num = Integer.parseInt(codTexto);
            if (num > maximo) {
                maximo = num;
            }
        } catch (NumberFormatException e) {
        }
    }
}
    int siguiente = maximo + 1;
    if (siguiente < 10) {
        return "K00" + siguiente;
    } else if (siguiente < 100) {
        return "K0" + siguiente;
    } else {
        return "K" + siguiente;
    }
    }
   
   public static int CargarCanchas(Canchas[] arreglo, int maxCapacidad){
   int contador = 0;
        try {
            FileReader fr = new FileReader("Canchas.txt");
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();

            while (linea != null && contador < maxCapacidad) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 8) {
                        String codigo    = datos[0].trim();
                        String nombre    = datos[1].trim();
                        String tipo      = datos[2].trim();
                        String capacidad = datos[3].trim();
                        double precio    = Double.parseDouble(datos[4].trim());
                        String estado    = datos[5].trim();
                        String apertura  = datos[6].trim();
                        String cierre    = datos[7].trim();
                        arreglo[contador] = new Canchas(codigo, nombre, tipo, capacidad, precio, estado, apertura, cierre);
                        contador++;
                    }
                }
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
        return contador;
   }
   
   public static void GuardarArchivosCanchas(Canchas[] arreglo, int contador){
        try {
            FileWriter fw = new FileWriter("Canchas.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < contador; i++) {
                Canchas c = arreglo[i];
                if (c != null) {
            String linea = c.getCodigoC() + "," +c.getNombreC() + "," +c.getTipoC() + "," +c.getCapacidadC() + "," +c.getPrecioBase() + "," +c.getEstadoC() + "," +c.getHoraApertura() + "," +c.getHoraCierre();
             bw.write(linea);
                   bw.newLine(); 
                }
            }
            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(null, "¡Datos guardados exitosamente en Canchas.txt!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage());
        }
        }
      
  
    
    
    
    
   
   
   
   
   
   
   
   
   
   
}



