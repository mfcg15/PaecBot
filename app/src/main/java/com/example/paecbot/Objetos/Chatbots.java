package com.example.paecbot.Objetos;

public class Chatbots {

    String fotoperfil;
    String nombre;
    String lastmensaje;
    String hora;
    String inicio;
    String termino;
    String tipo;
    String separador;


    public Chatbots(String fotoperfil, String nombre, String inicio, String termino, String tipo) {
        this.fotoperfil = fotoperfil;
        this.nombre = nombre;
        this.inicio = inicio;
        this.termino = termino;
        this.tipo = tipo;
    }

    public Chatbots(String lastmensaje, String hora) {
        this.lastmensaje = lastmensaje;
        this.hora = hora;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLastmensaje() {
        return lastmensaje;
    }

    public void setLastmensaje(String lastmensaje) {
        this.lastmensaje = lastmensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }
}
