package com.example.paecbot.Objetos;

import java.io.Serializable;

public class Informacion implements Serializable {

    String id ;
    String tipo ;
    String correctas;
    String incorrectas;
    String puntaje;
    String fecha;
    boolean estado;


   public Informacion()
    {

    }

    public Informacion(String id, String tipo, String correctas, String incorrectas, String puntaje, String fecha, boolean estado) {
        this.id = id;
        this.tipo = tipo;
        this.correctas = correctas;
        this.incorrectas = incorrectas;
        this.puntaje = puntaje;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Informacion(boolean estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorrectas() {
        return correctas;
    }

    public void setCorrectas(String correctas) {
        this.correctas = correctas;
    }

    public String getIncorrectas() {
        return incorrectas;
    }

    public void setIncorrectas(String incorrectas) {
        this.incorrectas = incorrectas;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
