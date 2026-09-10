package modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String codigo;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private String lugar;
    private List<Actividad> actividades;

    public Evento(String codigo, String nombre, String descripcion, Date fechaInicio, Date fechaFin, String lugar) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.lugar = lugar;
        this.actividades = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public List<Actividad> getActividades() { return actividades; }
    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    @Override
    public String toString() {
        return nombre + " (" + lugar + ")";
    }
}