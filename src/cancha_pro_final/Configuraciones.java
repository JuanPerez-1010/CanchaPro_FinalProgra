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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Configuraciones {
    public static boolean leerEstadoTienda() {
        try {
        FileReader fr = new FileReader("Config.txt");
        BufferedReader br = new BufferedReader(fr);
        String linea = br.readLine();
        br.close();
        fr.close();
        if (linea != null && linea.trim().equalsIgnoreCase("ACTIVO")) {
          return true;
        }
        } catch (Exception e) { }
        return false;
        }

    public static void guardarEstadoTienda(boolean activo) {
        try {
            FileWriter fw = new FileWriter("Config.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            if (activo) {
            bw.write("ACTIVO");
            } else {
            bw.write("INACTIVO");
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
      }
    public static String[] verificarArchivos(String[] listaArchivos) {
        String[] estados = new String[listaArchivos.length];

        for (int i = 0; i < listaArchivos.length; i++) {
            File archivo = new File(listaArchivos[i]);

            try {
            if (!archivo.exists()) {
             FileWriter fw = new FileWriter(archivo);
              fw.close();
               estados[i] = "Creado (0 registros)";
                } else {
                    FileReader fr = new FileReader(archivo);
                    BufferedReader br = new BufferedReader(fr);
                    String linea = br.readLine();
                    int contador = 0;

                    while (linea != null) {
                        if (!linea.trim().isEmpty()) {
                            contador++;
                        }
                        linea = br.readLine();
                    }
                    br.close();
                    fr.close();

                    if (contador == 0) {
                        estados[i] = "Vacío (0 registros)";
                    } else {
                        estados[i] = "Cargado (" + contador + " registros)";
                    }
                }
            } catch (Exception e) {
                estados[i] = "Error al leer";
            }
        }
        return estados;
    }
}

