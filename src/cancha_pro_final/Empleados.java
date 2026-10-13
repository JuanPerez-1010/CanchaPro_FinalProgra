/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

/**
 *
 * @author PERSONAL
 */
public class Empleados extends Personas_Cli_Emp {
    private String puesto;
    
    public Empleados(String codigo, String nombre,int telefono, String puesto){
        super(codigo, nombre, telefono);
        this.puesto = puesto;        
    }
    public Empleados(String puesto){
        super (" "," ",0);
        this.puesto = puesto; 
    }
    
    
    
    
    
    
    
}
