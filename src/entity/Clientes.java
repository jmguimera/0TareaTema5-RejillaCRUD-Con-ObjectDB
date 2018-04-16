package entity;
// Generated 31-mar-2018 12:17:00 by Hibernate Tools 4.3.1

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Clientes  implements java.io.Serializable {

    @Id    
    private String nif;
    private String nombre;
    private String direccion;
    private String telefono;

    public Clientes() {
    }

	
    public Clientes(String nif, String nombre) {
        this.nif = nif;
        this.nombre = nombre;
    }
    public Clientes(String nif, String nombre, String direccion, String telefono) {
       this.nif = nif;
       this.nombre = nombre;
       this.direccion = direccion;
       this.telefono = telefono;
    }
   
    public String getNif() {
        return this.nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}