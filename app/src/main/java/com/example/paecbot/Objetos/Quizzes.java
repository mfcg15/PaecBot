package com.example.paecbot.Objetos;

import java.io.Serializable;

public class Quizzes implements Serializable {

    String imagen_quiz;
    String idquiz;
    String tipotest;
    String cantidapreguntas;
    int progreso;

    public Quizzes() {
    }

    public Quizzes(String imagen_quiz, String idquiz, String tipotest, String cantidapreguntas, int progreso) {
        this.imagen_quiz = imagen_quiz;
        this.idquiz = idquiz;
        this.tipotest = tipotest;
        this.cantidapreguntas = cantidapreguntas;
        this.progreso = progreso;
    }

    public String getImagen_quiz() {
        return imagen_quiz;
    }

    public void setImagen_quiz(String imagen_quiz) {
        this.imagen_quiz = imagen_quiz;
    }

    public String getIdquiz() {
        return idquiz;
    }

    public void setIdquiz(String idquiz) {
        this.idquiz = idquiz;
    }

    public String getTipotest() {
        return tipotest;
    }

    public void setTipotest(String tipotest) {
        this.tipotest = tipotest;
    }

    public String getCantidapreguntas() {
        return cantidapreguntas;
    }

    public void setCantidapreguntas(String cantidapreguntas) {
        this.cantidapreguntas = cantidapreguntas;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
}
