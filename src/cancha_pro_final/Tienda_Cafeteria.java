/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

/**
 *
 * @author PERSONAL
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;


public class Tienda_Cafeteria {
    
    private String codigo;
    private String nombre;
    private double precio;
    private int existencia;
    private int stock;
    private String estado;
    
    private String codigoVenta;
    private String fecha;
    private String detalle;
    private double total;
    private String metodoPago;
    
    public Tienda_Cafeteria(String codigo, String nombre, double precio, int existencia, int stock, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
        this.stock = stock;
        this.estado = estado;
    }
    public Tienda_Cafeteria() {
    }
    public Tienda_Cafeteria(String codigoVenta, String fecha, String detalle, double total, String metodoPago) {
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.detalle = detalle;
        this.total = total;
        this.metodoPago = metodoPago;
    }
    public String getCodigo(){ 
    return codigo; 
    }
    public void setCodigo(String codigo){ 
    this.codigo = codigo; 
    }
    public String getNombre(){ 
    return nombre; 
    }
    public void setNombre(String nombre){ 
    this.nombre = nombre; 
    }
    public double getPrecio(){ 
    return precio; 
    }
    public void setPrecio(double precio){ 
    this.precio = precio; 
    }
    public int getExistencia(){ 
    return existencia; 
    }
    public void setExistencia(int existencia){ 
    this.existencia = existencia; 
    }
    public int getStock(){ 
    return stock; 
    }
    public void setStock(int stock){ 
    this.stock = stock; 
    }
    public String getEstado(){ 
    return estado; 
    }
    public void setEstado(String estado){ 
    this.estado = estado; 
    }

    
    
    public String getCodigoVenta(){ 
    return codigoVenta; 
    }
    public void setCodigoVenta(String codigoVenta){ 
    this.codigoVenta = codigoVenta; 
    }
    public String getFecha(){ 
    return fecha; 
    }
    public void setFecha(String fecha){ 
    this.fecha = fecha; 
    }
    public String getDetalle(){ 
    return detalle; 
    }
    public void setDetalle(String detalle){ 
    this.detalle = detalle; 
    }
    public double getTotal(){ 
    return total; 
    }
    public void setTotal(double total){ 
    this.total = total; 
    }
    public String getMetodoPago(){ 
    return metodoPago; 
    }
    public void setMetodoPago(String metodoPago){ 
    this.metodoPago = metodoPago; 
    }
    public static String generaCodProducto(Tienda_Cafeteria[] arreglo, int contador) {
        int maximo = 0;
        for (int i = 0; i < contador; i++) {
            if (arreglo[i] != null && arreglo[i].getCodigo() != null) {
            try {
            String codTexto = arreglo[i].getCodigo().substring(2);
             int num = Integer.parseInt(codTexto);
            if (num > maximo) {
            maximo = num;
              }
             } catch (Exception e) {
                }} }
        int siguiente = maximo + 1;
        if (siguiente < 10) {
            return "PR00" + siguiente;
        } else if (siguiente < 100) {
            return "PR0" + siguiente;
        } else {
            return "PR" + siguiente;
        }
    }

    public static int CargarProductos(Tienda_Cafeteria[] arreglo) {
        int contador = 0;
        try {
            FileReader fr = new FileReader("Productos.txt");
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();

            while (linea != null && contador < arreglo.length) {
            if (!linea.trim().isEmpty()) {
            String[] datos = linea.split(",");
            if (datos.length >= 6) {
            String cod = datos[0].trim();
            String nom = datos[1].trim();
            double pre = Double.parseDouble(datos[2].trim());
            int exi = Integer.parseInt(datos[3].trim());
            int stk = Integer.parseInt(datos[4].trim());
            String est = datos[5].trim();
            arreglo[contador] = new Tienda_Cafeteria(cod, nom, pre, exi, stk, est);
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

    public static void GuardarProductos(Tienda_Cafeteria[] arreglo, int contador) {
          try {
            FileWriter fw = new FileWriter("Productos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < contador; i++) {
                Tienda_Cafeteria p = arreglo[i];
                if (p != null) {
                    String linea = p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio() + ","+ p.getExistencia() + "," + p.getStock() + "," + p.getEstado();              
                    bw.write(linea);
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar Productos.txt: " + e.getMessage());
        }
    }
    public static String generaCodVenta(Tienda_Cafeteria[] arreglo, int contador) {
            int maximo = 0;
             for (int i = 0; i < contador; i++) {
              if (arreglo[i] != null && arreglo[i].getCodigoVenta() != null) {
                try {
                    String codTexto = arreglo[i].getCodigoVenta().substring(1);
                    int num = Integer.parseInt(codTexto);
                    if (num > maximo) {
                        maximo = num;
                    }
                } catch (Exception e) {
                }
            }
        }
        int siguiente = maximo + 1;
        if (siguiente < 10) {
            return "V00" + siguiente;
        } else if (siguiente < 100) {
            return "V0" + siguiente;
        } else {
            return "V" + siguiente;
        }
    }

    public static int CargarVentas(Tienda_Cafeteria[] arreglo) {
        int contador = 0;
        try {
            FileReader fr = new FileReader("VentasTienda.txt");
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();
            while (linea != null && contador < arreglo.length) {
            if (!linea.trim().isEmpty()) {
            String[] datos = linea.split(",");
            if (datos.length >= 5) {
            String codV = datos[0].trim();
            String fec = datos[1].trim();
            String det = datos[2].trim();
            double tot = Double.parseDouble(datos[3].trim());
            String met = datos[4].trim();
                    arreglo[contador] = new Tienda_Cafeteria(codV, fec, det, tot, met);
                     contador++;
                  }}
                linea = br.readLine();  }
            br.close();
            fr.close();
        } catch (Exception e) {  }
        return contador;
    }

    public static void GuardarVentas(Tienda_Cafeteria[] arreglo, int contador) {
        try {
            FileWriter fw = new FileWriter("VentasTienda.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < contador; i++) {
                Tienda_Cafeteria v = arreglo[i];
                if (v != null) {
                String detalleLimpio = v.getDetalle().replace(",", " -");
                String linea = v.getCodigoVenta() + "," + v.getFecha() + "," + detalleLimpio + ","+ v.getTotal() + "," + v.getMetodoPago();
                bw.write(linea);
                bw.newLine();
                } }
            bw.close();
            fw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar VentasTienda.txt: " + e.getMessage());
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    

