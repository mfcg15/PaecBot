package com.example.paecbot.Objetos;

public class Ranking {

    String puesto ;
    String user;
    String puntaje ;

    public Ranking(String puesto, String user, String puntaje) {
        this.puesto = puesto;
        this.user = user;
        this.puntaje = puntaje;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }
}
