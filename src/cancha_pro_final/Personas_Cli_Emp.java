/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

/**
 *
 * @author PERSONAL
 */public class Personas_Cli_Emp {
    
    protected String codigo;
    protected String nombre;
    protected int telefono;
    
    
    public Personas_Cli_Emp (String codigo, String nombre,int telefono){
        this.codigo=codigo;
        this.nombre=nombre;
        this.telefono=telefono;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    
   
    
    
    
}
