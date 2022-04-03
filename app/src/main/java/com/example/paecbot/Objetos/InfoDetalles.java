package com.example.paecbot.Objetos;

public class InfoDetalles {

    String numero;
    String url;
    String respuestau;
    String tiempo ;

    public InfoDetalles(String numero, String url, String respuestau, String tiempo) {
        this.numero = numero;
        this.url = url;
        this.respuestau = respuestau;
        this.tiempo = tiempo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRespuestau() {
        return respuestau;
    }

    public void setRespuestau(String respuestau) {
        this.respuestau = respuestau;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
