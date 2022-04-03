package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paecbot.Objetos.Quizzes;
import com.example.paecbot.R;

import java.util.List;


public class AdapterListaQuiz extends RecyclerView.Adapter<AdapterListaQuiz.CustomViewHolder>implements View.OnClickListener {

    private List<Quizzes> ListaQuizzes ;
    private Context c ;

    private View.OnClickListener listener;

    public AdapterListaQuiz(List<Quizzes> ListaQuizzes, Context c) {
        this.ListaQuizzes = ListaQuizzes;
        this.c = c;
    }

    @Override
    public AdapterListaQuiz.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.lista_quizzes,null);
        view.setOnClickListener(this);
        return  new AdapterListaQuiz.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterListaQuiz.CustomViewHolder holder, int position) {
        final Quizzes quizzes = ListaQuizzes.get(position);

        holder.nombrequiz.setText(quizzes.getIdquiz());
        holder.cantidadpregunta.setText("Total de preguntas : "+quizzes.getCantidapreguntas());
        holder.progreso.setText(quizzes.getProgreso()+" %");
        holder.barraprogreso.setProgress(quizzes.getProgreso());
        Glide.with(c).load(quizzes.getImagen_quiz()).into(holder.imagen_quiz);

    }

    @Override
    public int getItemCount() {
        return ListaQuizzes.size();
    }


    @Override
    public void onClick(View v) {

        if(listener!=null)
        {
            listener.onClick(v);
        }
    }

    public void Click (View.OnClickListener listener)
    {
        this.listener = listener;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombrequiz,cantidadpregunta, progreso;
        ProgressBar barraprogreso;
        ImageView imagen_quiz;

        public CustomViewHolder (View itemView)
        {
            super(itemView);


            nombrequiz = (TextView) itemView.findViewById(R.id.txt_id_quiz);
            cantidadpregunta = (TextView)itemView.findViewById(R.id.txt_cantidad_preguntas);
            progreso = (TextView) itemView.findViewById(R.id.txt_progreso);
            barraprogreso = (ProgressBar) itemView.findViewById(R.id.barra_progreso);
            imagen_quiz = (ImageView)itemView.findViewById(R.id.imagen_quiz);
        }
    }
}
