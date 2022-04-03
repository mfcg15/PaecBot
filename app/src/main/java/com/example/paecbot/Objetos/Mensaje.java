package com.example.paecbot.Objetos;

public class Mensaje {
    private String mensaje;
    private String foto_perfil;
    private String enviado_por;
    private String horamensaje;
    private String nproblema;
    private  boolean estado;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String foto_perfil, String enviado_por, String horamensaje, String nproblema, boolean estado) {
        this.mensaje = mensaje;
        this.foto_perfil = foto_perfil;
        this.enviado_por = enviado_por;
        this.horamensaje = horamensaje;
        this.nproblema = nproblema;
        this.estado = estado;
    }


    public Mensaje(String mensaje, String foto_perfil, String enviado_por, String horamensaje, boolean estado) {
        this.mensaje = mensaje;
        this.foto_perfil = foto_perfil;
        this.enviado_por = enviado_por;
        this.horamensaje = horamensaje;
        this.estado = estado;
    }

    public Mensaje(String mensaje, String enviado_por, String horamensaje, boolean estado) {
        this.mensaje = mensaje;
        this.enviado_por = enviado_por;
        this.horamensaje = horamensaje;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getEnviado_por() {
        return enviado_por;
    }

    public void setEnviado_por(String enviado_por) {
        this.enviado_por = enviado_por;
    }

    public String getHoramensaje() {
        return horamensaje;
    }

    public void setHoramensaje(String horamensaje) {
        this.horamensaje = horamensaje;
    }

    public String getNproblema() {
        return nproblema;
    }

    public void setNproblema(String nproblema) {
        this.nproblema = nproblema;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}