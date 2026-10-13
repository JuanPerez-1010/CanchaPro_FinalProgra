/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.time.LocalDate;

public class Clientes extends Personas_Cli_Emp  {

   private String tipocliente; // aqui me tiene que ver si el cliente es fecuente o no
   private String CorreoElectronico;
   private LocalDate fechaRegistro;
   
  
        public Clientes(String codigo, String nombre,int telefono, String tipocliente, String CorreoElectronico){
            super (codigo, nombre, telefono);
            this.tipocliente=tipocliente;
            this.CorreoElectronico =CorreoElectronico;
            this.fechaRegistro =LocalDate.now();
        }
        public Clientes(String correElectronico){
            super (" "," ",0);
            this.CorreoElectronico=CorreoElectronico;
            this.fechaRegistro =LocalDate.now();
        }





}
