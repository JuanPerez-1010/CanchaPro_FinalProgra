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
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author PERSONAL
 */
public class Reserva {
   private String codigoR;
    private String codigoCliente;
    private String codigoCancha;
    private String fecha;
    private String horaInicio;
    private int horas;
    private String horaFin;
    private String servicios;
    private double total;
    private double anticipo;
    private double saldo;
    private String estado;
    private String motivoCancelacion;
    
    public Reserva(){}

    public Reserva(String codigoR, String codigoCliente, String codigoCancha, String fecha,String horaInicio, int horas, String horaFin, String servicios,double total, double anticipo, double saldo, String estado, String motivoCancelacion) {
        this.codigoR = codigoR;
        this.codigoCliente = codigoCliente;
        this.codigoCancha = codigoCancha;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horas = horas;
        this.horaFin = horaFin;
        this.servicios = servicios;
        this.total = total;
        this.anticipo = anticipo;
        this.saldo = saldo;
        this.estado = estado;
        this.motivoCancelacion = motivoCancelacion;
    }
    public String getCodigoR() { 
    return codigoR; 
    }
    public void setCodigoR(String codigoR){ 
    this.codigoR = codigoR; 
    }
    public String getCodigoCliente(){ 
    return codigoCliente; 
    }
    public void setCodigoCliente(String codigoCliente){ 
    this.codigoCliente = codigoCliente; 
    }
    public String getCodigoCancha(){ 
    return codigoCancha; 
    }
    public void setCodigoCancha(String codigoCancha){ 
    this.codigoCancha = codigoCancha; 
    }
    public String getFecha(){ 
    return fecha; 
    }
    public void setFecha(String fecha){ 
    this.fecha = fecha; 
    }
    public String getHoraInicio(){
    return horaInicio; 
    }
    public void setHoraInicio(String horaInicio){ 
    this.horaInicio = horaInicio; 
    }
    public int getHoras(){ 
    return horas; 
    }
    public void setHoras(int horas){ 
    this.horas = horas; 
    }
    public String getHoraFin(){ 
    return horaFin; 
    }
    public void setHoraFin(String horaFin){ 
    this.horaFin = horaFin; 
    }
    public String getServicios(){ 
    return servicios; 
    }
    public void setServicios(String servicios){ 
    this.servicios = servicios; 
    }
    public double getTotal(){ 
    return total; 
    }
    public void setTotal(double total){ 
    this.total = total; 
    }
    public double getAnticipo(){ 
    return anticipo; 
    }
    public void setAnticipo(double anticipo){ 
    this.anticipo = anticipo; 
    }
    public double getSaldo() { 
    return saldo; 
    }
    public void setSaldo(double saldo) { 
    this.saldo = saldo; 
    }
    public String getEstado(){ 
    return estado; 
    }
    public void setEstado(String estado){ 
    this.estado = estado; 
    }
    public String getMotivoCancelacion(){ 
    return motivoCancelacion; 
    }
    public void setMotivoCancelacion(String motivoCancelacion){ 
    this.motivoCancelacion = motivoCancelacion; 
    }
    
    public static int CargarArchivosReservas(Reserva[] arreglo) {
        File archivo = new File("Reservas.txt");
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
                    if (d.length >= 12) {
                        String codR    = d[0].trim();
                        String codCli  = d[1].trim();
                        String codCan  = d[2].trim();
                        String fec     = d[3].trim();
                        String hIni    = d[4].trim();
                        int hrs        = Integer.parseInt(d[5].trim());
                        String hFin    = d[6].trim();
                        String serv    = d[7].trim();
                        double tot     = Double.parseDouble(d[8].trim());
                        double ant     = Double.parseDouble(d[9].trim());
                        double sal     = Double.parseDouble(d[10].trim());
                        String est     = d[11].trim();
                        String motivo  = (d.length >= 13 && !d[12].trim().isEmpty()) ? d[12].trim() : "Ninguno";

                        arreglo[contador] = new Reserva(codR, codCli, codCan, fec, hIni, hrs, hFin, serv, tot, ant, sal, est, motivo);
                        contador++;
                    }
                }
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error en el txt " + e.getMessage());
        }
        return contador;
    }
    public static void GuardarArchivoReservas(Reserva[] arreglo, int contador) {
        try {
            FileWriter fw = new FileWriter("Reservas.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < contador; i++) {
            Reserva r = arreglo[i];
            if (r != null) {
              String serv = r.getServicios();
                    if (serv == null || serv.trim().equals("")) {
                        serv = "Ninguno";
                    }
                    String motivo = r.getMotivoCancelacion();
                    if (motivo == null || motivo.trim().equals("")) {
                        motivo = "Ninguno";
                    }
                    String linea = r.getCodigoR() + "," + r.getCodigoCliente() + ","+ r.getCodigoCancha() + ","+ r.getFecha() + ","+ r.getHoraInicio() + ","+ r.getHoras() + ","+ r.getHoraFin() + ","+ serv + ","+ r.getTotal() + ","+ r.getAnticipo() + ","+ r.getSaldo() + ","+ r.getEstado() + ","+ motivo;
                    bw.write(linea);
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();
            } catch (Exception e) {
            System.out.println("Eror en el archivo " + e.getMessage());
        }
    }
    public static String generarCodigo(Reserva[] arreglo, int contador) {
        int maximo = 0;
        for (int i = 0; i < contador; i++) {
            if (arreglo[i] != null) {
                try {
                String numTexto = arreglo[i].getCodigoR().trim().replace("R", "");
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
            return "R00" + siguiente;
        } else if (siguiente < 100) {
            return "R0" + siguiente;
        } else {
            return "R" + siguiente;
        }
    }

    public static int buscarPosicionPorCodigo(Reserva[] arreglo, int contador, String codigoBuscar) {
        for (int i = 0; i < contador; i++) {
         if (arreglo[i] != null) {
         if (arreglo[i].getCodigoR().equalsIgnoreCase(codigoBuscar.trim())) {
         return i;
                }
            }
        }
        return -1;
    }
    
    
    private static LocalTime convertirHora(String horaTxt) {
        String h = horaTxt.trim();
        if (h.length() == 4) {
         h = "0" + h;
        }
        return LocalTime.parse(h);
    }
    
    public static String calcularHoraFin(String horaInicioTxt, int horas) {
        LocalTime inicio = convertirHora(horaInicioTxt);
        LocalTime fin = inicio.plusHours(horas);
        return fin.toString();
    }
    public static boolean cabeEnHorarioCancha(String inicioTxt, String finTxt, String aperturaTxt, String cierreTxt) {
        LocalTime inicio = convertirHora(inicioTxt);
        LocalTime fin = convertirHora(finTxt);
        LocalTime apertura = convertirHora(aperturaTxt);
        LocalTime cierre = convertirHora(cierreTxt);

        if (inicio.isBefore(apertura) || fin.isAfter(cierre)) {
            return false;
        }
        return true;
    }
    public static boolean hayChoqueDeHorario(Reserva[] arreglo, int contador,String codCancha, String fecha,String inicioNuevoTxt, String finNuevoTxt,String codigoReservaIgnorar) {                                   
        LocalTime inicioNuevo = convertirHora(inicioNuevoTxt);
        LocalTime finNuevo = convertirHora(finNuevoTxt);
        for (int i = 0; i < contador; i++) {
            Reserva r = arreglo[i];
            if (r != null) {
                if (r.getEstado().equalsIgnoreCase("CANCELADA")) {
                continue;
                }
                if (!codigoReservaIgnorar.isEmpty() && r.getCodigoR().equalsIgnoreCase(codigoReservaIgnorar)) {
                continue;
                }
                if (r.getCodigoCancha().equalsIgnoreCase(codCancha.trim())&& r.getFecha().equalsIgnoreCase(fecha.trim())) {
                 LocalTime inicioExistente = convertirHora(r.getHoraInicio());
                 LocalTime finExistente = convertirHora(r.getHoraFin());
                  if (inicioNuevo.isBefore(finExistente) && finNuevo.isAfter(inicioExistente)) {
                  return true; 
                 }
                }
                 }
                 }
                 return false; 
                 }
    
    public static double calcularTotal(double tarifaCancha, int horas, double totalExtras) {
        return (tarifaCancha * horas) + totalExtras;
    }

  
}
    
    
    
    

