/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.time.LocalDate;

public class Clientes  {

   private String tipocliente; // aqui me tiene que ver si el cliente es fecuente o no
   private String CorreoElectronico;
   private LocalDate fechaRegistro;
   private String codigo_C;
   private String Nombre_C;
   private int telefono_C;
  
   
  
        public Clientes( String tipocliente, String CorreoElectronico,LocalDate fechaRegistro, String codigo_C, String Nombre_C, int telefono_C){
           this.tipocliente=tipocliente;
           this.CorreoElectronico=CorreoElectronico;
           this.fechaRegistro=fechaRegistro;
           this.codigo_C=codigo_C; 
           this.Nombre_C=Nombre_C;
           this.telefono_C=telefono_C;
                   
            
        }
       
    public void AgregarCliente(String CorreoElectronico, String codigo_C, String Nombre_C, int telefono_C ){
        
        
        
        
    }  
        
        
        
        



}
