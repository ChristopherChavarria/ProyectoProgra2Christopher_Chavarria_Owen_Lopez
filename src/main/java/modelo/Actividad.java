package modelo;

import java.util.Date;

public class Actividad {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private Date horaInicio;
    private Date horaFin;
    private String ubicacion;
    private int capacidadMax;
    private String estado;
    private int eventoId;
    private int expositorId;
    private int tipoActividadId;

    public Actividad() {
    }

    public Actividad(int id, String nombre, String descripcion, Date fecha, Date horaInicio, Date horaFin, String ubicacion, int capacidadMax, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ubicacion = ubicacion;
        this.capacidadMax = capacidadMax;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Date getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Date horaInicio) { this.horaInicio = horaInicio; }

    public Date getHoraFin() { return horaFin; }
    public void setHoraFin(Date horaFin) { this.horaFin = horaFin; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getCapacidadMax() { return capacidadMax; }
    public void setCapacidadMax(int capacidadMax) { this.capacidadMax = capacidadMax; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getEventoId() { return eventoId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }

    public int getExpositorId() { return expositorId; }
    public void setExpositorId(int expositorId) { this.expositorId = expositorId; }

    public int getTipoActividadId() { return tipoActividadId; }
    public void setTipoActividadId(int tipoActividadId) { this.tipoActividadId = tipoActividadId; }

    @Override
    public String toString() {
        return nombre + " (Ubicación: " + ubicacion + ")";
    }
}