package org.example.hospitalcrud;

public class Paciente {

    private int id;
    private String nombre;
    private int edad;
    private String enfermedad;
    private String telefono;

    public Paciente() {
    }

    public Paciente(int id, String nombre, int edad, String enfermedad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.enfermedad = enfermedad;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}