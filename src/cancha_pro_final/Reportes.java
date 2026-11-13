/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Locale;

public class Reportes {

    private String codigoCriterio;
    private String detalles;
    private String fechaHora;
    private int cantidad;
    private double saldoMonto;

    public Reportes() {
    }

    public Reportes(String codigoCriterio, String detalles, String fechaHora, int cantidad, double saldoMonto) {
        this.codigoCriterio = codigoCriterio;
        this.detalles = detalles;
        this.fechaHora = fechaHora;
        this.cantidad = cantidad;
        this.saldoMonto = saldoMonto;
    }

    public String getCodigoCriterio() {
        return codigoCriterio;
    }

    public void setCodigoCriterio(String codigoCriterio) {
        this.codigoCriterio = codigoCriterio;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSaldoMonto() {
        return saldoMonto;
    }

    public void setSaldoMonto(double saldoMonto) {
        this.saldoMonto = saldoMonto;
    }

    public static void GuardarArchivoReportes(Reportes[] canasta, int contador, String tipoReporte, String fechaGeneracion) {
        try {
            FileWriter fw = new FileWriter("Reportes.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("---REPORTE: " + tipoReporte + " | GENERADO: " + fechaGeneracion + " ----");
            bw.newLine();

            for (int i = 0; i < contador; i++) {
                Reportes rep = canasta[i];
                if (rep != null) {
                  String linea = rep.getCodigoCriterio() + ","+ rep.detalles + ","+ rep.getFechaHora() + ","+ rep.getCantidad() + ","+ String.format(Locale.US, "%.2f", rep.getSaldoMonto());
                    bw.write(linea);
                    bw.newLine();
                }
            }
            bw.newLine();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar reporstes al txt" + e.getMessage());
        }
    }
    
    public static String buscarNombreCliente(Clientes[] clientes, int contCli, String codCli) {
        for (int i = 0; i < contCli; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codCli.trim())) {
                return clientes[i].getNombre();
            }
        }
        return codCli;
    }

    public static String buscarNombreCancha(Canchas[] canchas, int contCan, String codCan) {
        for (int i = 0; i < contCan; i++) {
            if (canchas[i] != null && canchas[i].getCodigoC().equalsIgnoreCase(codCan.trim())) {
                return canchas[i].getNombreC();
            }
        }
        return codCan;
    }
    
    public static boolean fechaEnRango(String fecha, String inicio, String fin) {
        String f = fecha.trim();
        if (f.length() > 10) {
            f = f.substring(0, 10); 
        }
        if (!inicio.trim().equals("") && f.compareTo(inicio.trim()) < 0) {
            return false;
        }
        if (!fin.trim().equals("") && f.compareTo(fin.trim()) > 0) {
            return false;
        }
        return true;
    }

    public static int reporteReservasPorFecha(Reportes[] canasta, Reserva[] reservas, int contRes,Clientes[] clientes, int contCli, Canchas[] canchas, int contCan,String fechaIni, String fechaFin, String filtroCancha, String filtroEstado) {
        int totalFilas = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null) {
                boolean pasaFecha = fechaEnRango(r.getFecha(), fechaIni, fechaFin);
                boolean pasaCancha = filtroCancha.equalsIgnoreCase("Todas") || r.getCodigoCancha().equalsIgnoreCase(filtroCancha);
                boolean pasaEstado = filtroEstado.equalsIgnoreCase("Todos") || r.getEstado().equalsIgnoreCase(filtroEstado);

                if (pasaFecha && pasaCancha && pasaEstado) {
                    String nomCli = buscarNombreCliente(clientes, contCli, r.getCodigoCliente());
                    String nomCan = buscarNombreCancha(canchas, contCan, r.getCodigoCancha());
                    String detalle = nomCli + " - " + nomCan + " (" + r.getEstado() + ")";
                    String fechaHorario = r.getFecha() + " " + r.getHoraInicio() + "-" + r.getHoraFin();

                    canasta[totalFilas] = new Reportes(r.getCodigoR(), detalle, fechaHorario, 1, r.getTotal());
                    totalFilas++;
                }
            }
        }
        return totalFilas;
    }
    
    public static int reporteReservasPorCancha(Reportes[] canasta, Reserva[] reservas, int contRes,Clientes[] clientes, int contCli, Canchas[] canchas, int contCan,String fechaIni, String fechaFin, String filtroCancha) {
        int totalFilas = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null) {
                boolean pasaFecha = fechaEnRango(r.getFecha(), fechaIni, fechaFin);
                boolean pasaCancha = filtroCancha.equalsIgnoreCase("Todas") || r.getCodigoCancha().equalsIgnoreCase(filtroCancha);

                if (pasaFecha && pasaCancha) {
                    String nomCli = buscarNombreCliente(clientes, contCli, r.getCodigoCliente());
                    String nomCan = buscarNombreCancha(canchas, contCan, r.getCodigoCancha());
                    String detalle = r.getCodigoCancha() + " (" + nomCan + ") - " + nomCli;
                    String fechaHorario = r.getFecha() + " " + r.getHoraInicio() + "-" + r.getHoraFin();

                    canasta[totalFilas] = new Reportes(r.getCodigoR(), detalle, fechaHorario, 1, r.getTotal());
                    totalFilas++;
                }
            }  } 
        return totalFilas;
    }
    
    public static int reporteIngresosPorPeriodo(Reportes[] canasta, Pagos[] pagos, int contPagos,String fechaIni, String fechaFin) {
        int totalFilas = 0;
        for (int i = 0; i < contPagos; i++) {
            Pagos p = pagos[i];
            if (p != null) {
                if (fechaEnRango(p.getFechaHora(), fechaIni, fechaFin)) {
                    String detalle = "Reserva: " + p.getCodigoReserva() + " - " + p.getMetodoPago();
                    canasta[totalFilas] = new Reportes(p.getCodigoPago(), detalle, p.getFechaHora(), 1, p.getMonto());
                    totalFilas++;
                }   }}
        return totalFilas;
         }

    public static int reportePagosPendientes(Reportes[] canasta, Reserva[] reservas, int contRes,Clientes[] clientes, int contCli, Canchas[] canchas, int contCan, String filtroCancha) {     
        int totalFilas = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null) {
                boolean pasaCancha = filtroCancha.equalsIgnoreCase("Todas") || r.getCodigoCancha().equalsIgnoreCase(filtroCancha);
                if (!r.getEstado().equalsIgnoreCase("CANCELADA") && r.getSaldo() > 0 && pasaCancha) {
                    String nomCli = buscarNombreCliente(clientes, contCli, r.getCodigoCliente());
                    String nomCan = buscarNombreCancha(canchas, contCan, r.getCodigoCancha());
                    String detalle = nomCli + " - " + nomCan + " (Total Q" + r.getTotal() + ")";
                    String fechaHorario = r.getFecha() + " " + r.getHoraInicio();

                    canasta[totalFilas] = new Reportes(r.getCodigoR(), detalle, fechaHorario, 1, r.getSaldo());
                    totalFilas++;
                }  }}
        return totalFilas;
       }

    public static int reporteReservasCanceladas(Reportes[] canasta, Reserva[] reservas, int contRes,Clientes[] clientes, int contCli, String fechaIni, String fechaFin) {
        int totalFilas = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null && r.getEstado().equalsIgnoreCase("CANCELADA")) {
                if (fechaEnRango(r.getFecha(), fechaIni, fechaFin)) {
                String nomCli = buscarNombreCliente(clientes, contCli, r.getCodigoCliente());
                String detalle = "Cliente: " + nomCli + " | Cancha: " + r.getCodigoCancha() + " | CANCELADA";
                String fechaHorario = r.getFecha() + " " + r.getHoraInicio() + "-" + r.getHoraFin();
                canasta[totalFilas] = new Reportes(r.getCodigoR(), detalle, fechaHorario, 1, r.getTotal());
                totalFilas++;
                }
                }
             }
        return totalFilas;
        }
    public static int reporteCanchaMasUtilizada(Reportes[] canasta, Reserva[] reservas, int contRes,Canchas[] canchas, int contCan) {
        String[] codigos = new String[100];
        int[] conteos = new int[100];
        double[] dinero = new double[100];
        int grupos = 0;

        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null && !r.getEstado().equalsIgnoreCase("CANCELADA")) {
                int pos = -1;
                for (int j = 0; j < grupos; j++) {
                    if (codigos[j].equalsIgnoreCase(r.getCodigoCancha())) {
                        pos = j;
                        break;
                    }
                }
                if (pos == -1) {
                    codigos[grupos] = r.getCodigoCancha();
                    conteos[grupos] = 1;
                    dinero[grupos] = r.getTotal();
                    grupos++;
                } else {
                    conteos[pos]++;
                    dinero[pos] = dinero[pos] + r.getTotal();
                }  } }

        for (int i = 0; i < grupos - 1; i++) {
            for (int j = i + 1; j < grupos; j++) {
                if (conteos[j] > conteos[i]) {
               int tempC = conteos[i]; conteos[i] = conteos[j]; conteos[j] = tempC;
                double tempD = dinero[i]; dinero[i] = dinero[j]; dinero[j] = tempD;
                 String tempCod = codigos[i]; codigos[i] = codigos[j]; codigos[j] = tempCod;
                }  }}
 
        for (int i = 0; i < grupos; i++) {
            String nombreCan = buscarNombreCancha(canchas, contCan, codigos[i]);
            canasta[i] = new Reportes(codigos[i], nombreCan, "Acumulado general", conteos[i], dinero[i]);
        }
        return grupos;
    }
    
    public static int reporteHorariosMayorDemanda(Reportes[] canasta, Reserva[] reservas, int contRes) {
        String[] horas = new String[100];
        int[] conteos = new int[100];
        double[] dinero = new double[100];
        int grupos = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null && !r.getEstado().equalsIgnoreCase("CANCELADA")) {
                String hora = r.getHoraInicio().trim();
                int pos = -1;
                for (int j = 0; j < grupos; j++) {
                    if (horas[j].equalsIgnoreCase(hora)) {
                        pos = j;
                        break;
                    }                }

                if (pos == -1) {
                    horas[grupos] = hora;
                    conteos[grupos] = 1;
                    dinero[grupos] = r.getTotal();
                    grupos++;
                } else {
                    conteos[pos]++;
                    dinero[pos] = dinero[pos] + r.getTotal();
                }} }

        for (int i = 0; i < grupos - 1; i++) {
            for (int j = i + 1; j < grupos; j++) {
            if (conteos[j] > conteos[i]) {
             int tempC = conteos[i]; conteos[i] = conteos[j]; conteos[j] = tempC;
              double tempD = dinero[i]; dinero[i] = dinero[j]; dinero[j] = tempD;
               String tempH = horas[i]; horas[i] = horas[j]; horas[j] = tempH;
                }
            }
        }
        for (int i = 0; i < grupos; i++) {
            canasta[i] = new Reportes("Turno " + (i + 1), "Franja horaria de inicio", horas[i], conteos[i], dinero[i]);
        }
        return grupos;
    }
    
    public static int reporteClientesFrecuentes(Reportes[] canasta, Reserva[] reservas, int contRes,Clientes[] clientes, int contCli) { 
        String[] codigosCli = new String[200];
        int[] visitas = new int[200];
        double[] dinero = new double[200];
        int grupos = 0;
        for (int i = 0; i < contRes; i++) {
            Reserva r = reservas[i];
            if (r != null && !r.getEstado().equalsIgnoreCase("CANCELADA")) {
                String cod = r.getCodigoCliente().trim();
                int pos = -1;
                for (int j = 0; j < grupos; j++) {
                if (codigosCli[j].equalsIgnoreCase(cod)) {
                 pos = j;
                break;
                    }
                }
                if (pos == -1) {
                    codigosCli[grupos] = cod;
                    visitas[grupos] = 1;
                    dinero[grupos] = r.getTotal();
                    grupos++;
                } else {
                    visitas[pos]++;
                    dinero[pos] = dinero[pos] + r.getTotal();
                }} }
            for (int i = 0; i < grupos - 1; i++) {
            for (int j = i + 1; j < grupos; j++) {
                if (visitas[j] > visitas[i]) {
                    int tempV = visitas[i]; visitas[i] = visitas[j]; visitas[j] = tempV;
                    double tempD = dinero[i]; dinero[i] = dinero[j]; dinero[j] = tempD;
                    String tempC = codigosCli[i]; codigosCli[i] = codigosCli[j]; codigosCli[j] = tempC;
                }  }  }
        for (int i = 0; i < grupos; i++) {
            String nombreCli = buscarNombreCliente(clientes, contCli, codigosCli[i]);
            canasta[i] = new Reportes(codigosCli[i], nombreCli, "Historial de visitas", visitas[i], dinero[i]);
        }
        return grupos;
    }             
    
    public static double sumarDineroCanasta(Reportes[] canasta, int totalFilas) {
        double suma = 0;
        for (int i = 0; i < totalFilas; i++) {
            if (canasta[i] != null) {
                suma = suma + canasta[i].getSaldoMonto();
            }
        }
        return suma;
    }
    public static String obtenerCanchaTop(Reserva[] reservas, int contRes, Canchas[] canchas, int contCan) {
        Reportes[] temp = new Reportes[100];
        int n = reporteCanchaMasUtilizada(temp, reservas, contRes, canchas, contCan);
        if (n > 0) {
            return temp[0].getCodigoCriterio() + " - " + temp[0].getDetalles() + " (" + temp[0].getCantidad() + ")";
        }
        return "Sin datos";
    }
    public static String obtenerHorarioTop(Reserva[] reservas, int contRes) {
        Reportes[] temp = new Reportes[100];
        int n = reporteHorariosMayorDemanda(temp, reservas, contRes);
        if (n > 0) {
            return temp[0].getFechaHora() + " (" + temp[0].getCantidad() + " res.)";
        }
        return "Sin datos";
    }
    public static String obtenerClienteTop(Reserva[] reservas, int contRes, Clientes[] clientes, int contCli) {
        Reportes[] temp = new Reportes[200];
        int n = reporteClientesFrecuentes(temp, reservas, contRes, clientes, contCli);
        if (n > 0) {
            return temp[0].getDetalles() + " (" + temp[0].getCantidad() + " visitas)";
        }
        return "Sin datos";
    }
}