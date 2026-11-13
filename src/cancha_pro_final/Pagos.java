/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author PERSONAL
 */
public class Pagos {
   

    private String codigoPago;
    private String codigoReserva;
    private String fechaHora;
    private double monto;
    private String metodoPago;
    private String observacion;

    public Pagos() {
    }
    public Pagos(String codigoPago, String codigoReserva, String fechaHora, double monto, String metodoPago, String observacion) {
        this.codigoPago = codigoPago;
        this.codigoReserva = codigoReserva;
        this.fechaHora = fechaHora;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observacion = observacion;
    }

    public String getCodigoPago() {
        return codigoPago;
    }
    public void setCodigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
    }
    public String getCodigoReserva() {
        return codigoReserva;
    }
    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }
    public String getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public String getObservacion() {
        return observacion;
    }
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public static int CargarArchivosPagos(Pagos[] arreglo) {
        File archivo = new File("Pagos.txt");
        if (!archivo.exists()) {
        return 0;
        }
        int contador = 0;
        try {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea = br.readLine();
        while (linea != null) {
        if (linea.trim().length() > 0) {
         String[] d = linea.split(",", -1);
        if (d.length >= 6) {
             String codP = d[0].trim();
             String codR = d[1].trim();
             String fec  = d[2].trim();
             double mon  = Double.parseDouble(d[3].trim());
             String met  = d[4].trim();
             String obs  = d[5].trim();

             arreglo[contador] = new Pagos(codP, codR, fec, mon, met, obs);
            contador++;
                    }
                }
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error en el TXT " + e.getMessage());
        }
        return contador;
    }
    public static void GuardarArchivoPagos(Pagos[] arreglo, int contador) {
        try {
            FileWriter fw = new FileWriter("Pagos.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < contador; i++) {
                Pagos p = arreglo[i];
                if (p != null) {
                 String obs = p.getObservacion();
                 if (obs == null || obs.trim().equals("")) {
                 obs = "Ninguna";
                  }
                 obs = obs.replace(",", " ");
                 String linea = p.getCodigoPago() + ","+ p.getCodigoReserva() + ","+ p.getFechaHora() + ","+ p.getMonto() + ","+ p.getMetodoPago() + ","+ obs;
                 bw.write(linea);
                 bw.newLine();
                }
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar  " + e.getMessage());
        }
    }
    public static String generarCodigo(Pagos[] arreglo, int contador) {
        int maximo = 0;
        for (int i = 0; i < contador; i++) {
         if (arreglo[i] != null) {
             try {
            String numTexto = arreglo[i].getCodigoPago().trim().replace("P", "");
             int num = Integer.parseInt(numTexto);
                if (num > maximo) {
                 maximo = num;
                 }
                } catch (Exception e) {
                }
            }
        }
        int siguiente = maximo + 1;
        if (siguiente < 10) {
            return "P00" + siguiente;
        } else if (siguiente < 100) {
            return "P0" + siguiente;
        } else {
            return "P" + siguiente;
        }
    }
    public static String validarPago(double monto, double saldoPendiente) {
        if (monto <= 0) {
            return "El monto del pago debe ser mayor a Q0.00.";
        }
        if (monto > saldoPendiente) {
            return "El monto no puede ser mayor al saldo pendiente (Q" + saldoPendiente + ").";
        }
        return "";
    }
    public static double calcularTotalRecaudado(Pagos[] arreglo, int contador) {
        double suma = 0;
        for (int i = 0; i < contador; i++) {
        if (arreglo[i] != null) {
        suma = suma + arreglo[i].getMonto();
         }
        }
        return suma;
    }
}
    
    
    
    

