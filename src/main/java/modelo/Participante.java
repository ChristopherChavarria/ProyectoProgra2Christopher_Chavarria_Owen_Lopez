package modelo;

import java.util.Date;

public class Participante extends Persona {
    private Date fechaRegistro;
    private String estado;

    public Participante(String identificacion, String nombre, String apellidos, String correoElectronico, String telefono, Date fechaRegistro, String estado) {
        super(identificacion, nombre, apellidos, correoElectronico, telefono);
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos() + " (" + getIdentificacion() + ")";
    }
}