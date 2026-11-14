

package cancha_pro_final;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;

public class Inicio {

    public int totalHoy = 0;
    public int enCurso = 0;
    public int pendientes = 0;
    public double ingresosHoy = 0;

    public Object[][] filasHoy = new Object[200][6];

    private String buscarNombreCliente(Clientes[] clientes, int totalClientes, String codCliente) {
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codCliente.trim())) {
                return clientes[i].getNombre();
            }
        }
        return codCliente;
    }

    private String[] cargarHorariosTxt() {
        String[] horarios = new String[200];
        File archivo = new File("Reservas.txt");
        if (!archivo.exists()) {
            return horarios;
        }
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null && idx < horarios.length) {
                if (!linea.trim().isEmpty()) {
                    String[] d = linea.split(",", -1);
                    if (d.length >= 6) {
                        horarios[idx] = d[3].trim() + " (" + d[4].trim() + "-" + d[5].trim() + ")";
                    } else {
                        horarios[idx] = "---";
                    }
                    idx++;
                }
            }
        } catch (Exception e) {
        }
        return horarios;
    }

    public void cargarDatosHoy() {
        totalHoy = 0;
        enCurso = 0;
        pendientes = 0;
        ingresosHoy = 0;

        Clientes[] arregloClientes = new Clientes[200];
        int contadorClientes = Clientes.CargarArchivosClientes(arregloClientes);

        Reserva[] arregloReservas = new Reserva[200];
        int contadorReservas = Reserva.CargarArchivosReservas(arregloReservas);

        Pagos[] arregloPagos = new Pagos[200];
        int contadorPagos = Pagos.CargarArchivosPagos(arregloPagos);

        String[] horarios = cargarHorariosTxt();
        double sumaAnticipos = 0;

        for (int i = 0; i < contadorReservas; i++) {
            Reserva r = arregloReservas[i];
            if (r != null && totalHoy < filasHoy.length) {
                String estado = r.getEstado().trim();

                if (!estado.equalsIgnoreCase("CANCELADA")) {
                    String nombreCliente = buscarNombreCliente(arregloClientes, contadorClientes, r.getCodigoCliente());
                    String horario = (horarios[i] != null) ? horarios[i] : "---";

                    filasHoy[totalHoy][0] = r.getCodigoR();
                    filasHoy[totalHoy][1] = nombreCliente;
                    filasHoy[totalHoy][2] = r.getCodigoCancha();
                    filasHoy[totalHoy][3] = horario;
                    filasHoy[totalHoy][4] = "Q" + String.format(Locale.US, "%.2f", r.getTotal());
                    filasHoy[totalHoy][5] = estado;

                    totalHoy++;

                    if (estado.equalsIgnoreCase("CONFIRMADA") || estado.equalsIgnoreCase("EN_CURSO")) {
                        enCurso++;
                    } else if (estado.equalsIgnoreCase("PENDIENTE")) {
                        pendientes++;
                    }

                    sumaAnticipos += r.getAnticipo();
                }
            }
        }

        double totalPagosTxt = Pagos.calcularTotalRecaudado(arregloPagos, contadorPagos);
        ingresosHoy = Math.max(sumaAnticipos, totalPagosTxt);
    }
}