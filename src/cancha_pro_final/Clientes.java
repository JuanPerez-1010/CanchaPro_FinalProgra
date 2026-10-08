/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cancha_pro_final;

import java.time.LocalDate;

public class Clientes {

  private String Codigo_Cliente;
  private String Nombre_Cliente;
  private int telefono;
  private String CorreoElectronico;
  private LocalDate fechaRegistro;
  
  public Clientes(String Codigo_Cliente,String Nombre_Cliente, int telefono, String CorreoElectronico){
      this.Codigo_Cliente = Codigo_Cliente;
      this.Nombre_Cliente = Nombre_Cliente;
      this.telefono = telefono;
      this.CorreoElectronico = CorreoElectronico;
      this.fechaRegistro = LocalDate.now(); 
  }
  public Clientes(String Codigo_Cliente,String Nombre_Cliente, int telefono, String CorreoElectronico,LocalDate fechadeRegistro){
      this.Codigo_Cliente = Codigo_Cliente;
      this.Nombre_Cliente = Nombre_Cliente;
      this.telefono = telefono;
      this.CorreoElectronico = CorreoElectronico;
      this.fechaRegistro = fechaRegistro; 
  }

    public String getCodigo_Cliente() {
        return Codigo_Cliente;
    }

    public String getNombre_Cliente() {
        return Nombre_Cliente;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setNombre_Cliente(String Nombre_Cliente){
        this.Nombre_Cliente=Nombre_Cliente;
    }
    public void setTelefono_Cliente(int telefono){
        this.telefono=telefono;
    }
    public void setCorreoElectronico(String CorreoElectronico){
        this.CorreoElectronico = CorreoElectronico;
    }
    
    public String toCSV() {
        return Codigo_Cliente + ";" + Nombre_Cliente + ";" + telefono + ";" + CorreoElectronico + ";" + fechaRegistro;
    }
  
  
  
  
  
  
  
  
    
}
