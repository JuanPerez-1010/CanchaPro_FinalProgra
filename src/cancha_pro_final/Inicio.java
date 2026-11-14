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
import java.io.FileReader;
import java.time.LocalDate;

public class Inicio {
    
    public int totalHoy = 0;
    public int enCurso = 0;
    public int pendientes = 0;
    public double ingresosHoy = 0;

    // Arreglo normal de 2 dimensiones (hasta 200 reservas por día y 6 columnas)
    public Object[][] filasHoy = new Object[200][6];

    public void cargarDatosHoy() {
        totalHoy = 0;
        enCurso = 0;
        pendientes = 0;
        ingresosHoy = 0;

        String fechaHoy = LocalDate.now().toString();

        try {
            FileReader fr = new FileReader("reservas.csv");
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();

            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    // Separa por coma (cámbialo a ";" si tu reservas.csv usa punto y coma)
                    String[] d = linea.split(",");

                    // Orden: 0=Codigo, 1=Cliente, 2=Cancha, 3=Fecha, 4=HoraIni, 5=HoraFin, 6=Total, 7=Estado
                    if (d.length >= 8) {
                        String fechaReserva = d[3].trim();

                        if (fechaReserva.equals(fechaHoy) && totalHoy < filasHoy.length) {
                            String codigo = d[0].trim();
                            String cliente = d[1].trim();
                            String cancha = d[2].trim();
                            String horario = d[4].trim() + " - " + d[5].trim();
                            double total = Double.parseDouble(d[6].trim());
                            String estado = d[7].trim();
                            
                            filasHoy[totalHoy][0] = codigo;
                            filasHoy[totalHoy][1] = cliente;
                            filasHoy[totalHoy][2] = cancha;
                            filasHoy[totalHoy][3] = horario;
                            filasHoy[totalHoy][4] = "Q" + String.format("%.2f", total);
                            filasHoy[totalHoy][5] = estado;

                            totalHoy++;

                            if (estado.equalsIgnoreCase("EN_CURSO")) {
                                enCurso++;
                            } else if (estado.equalsIgnoreCase("PENDIENTE")) {
                                pendientes++;
                            }

                            if (!estado.equalsIgnoreCase("CANCELADA")) {
                                ingresosHoy += total;
                            }
                        }
                    }
                }
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
    }   
    
    
    
    
    
}
