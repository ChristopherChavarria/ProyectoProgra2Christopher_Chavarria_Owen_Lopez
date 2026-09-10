package modelo;

import java.util.Date;

public class Actividad {
    private String codigo;
    private String nombre;
    private String descripcion;
    private Date horaInicio;
    private Date horaFin;
    private int cupoMaximo;
    private Expositor expositor;

    public Actividad(String codigo, String nombre, String descripcion, Date horaInicio, Date horaFin, int cupoMaximo, Expositor expositor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cupoMaximo = cupoMaximo;
        this.expositor = expositor;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Date horaInicio) { this.horaInicio = horaInicio; }

    public Date getHoraFin() { return horaFin; }
    public void setHoraFin(Date horaFin) { this.horaFin = horaFin; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Expositor getExpositor() { return expositor; }
    public void setExpositor(Expositor expositor) { this.expositor = expositor; }

    @Override
    public String toString() {
        return nombre + " [Cupo: " + cupoMaximo + "]";
    }
}