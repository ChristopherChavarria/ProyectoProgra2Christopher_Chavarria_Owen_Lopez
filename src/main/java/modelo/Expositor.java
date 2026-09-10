package modelo;

public class Expositor extends Persona {
    private String especialidad;
    private String descripcionProfesional;

    public Expositor(String identificacion, String nombre, String apellidos, String correoElectronico, String telefono, String especialidad, String descripcionProfesional) {
        super(identificacion, nombre, apellidos, correoElectronico, telefono);
        this.especialidad = especialidad;
        this.descripcionProfesional = descripcionProfesional;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getDescripcionProfesional() { return descripcionProfesional; }
    public void setDescripcionProfesional(String descripcionProfesional) { this.descripcionProfesional = descripcionProfesional; }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos() + " - " + especialidad;
    }
}