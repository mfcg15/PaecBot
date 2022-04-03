package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.paecbot.Objetos.InfoDetalles;
import com.example.paecbot.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.List;

public class AdapterDetalleChatbotsQuizzes extends RecyclerView.Adapter<AdapterDetalleChatbotsQuizzes.CustomViewHolder> {

    private List<InfoDetalles> listinfodetalles ;
    private Context c ;

    public AdapterDetalleChatbotsQuizzes(List<InfoDetalles> listinfodetalles, Context c) {
        this.listinfodetalles = listinfodetalles;
        this.c = c;
    }

    @Override
    public AdapterDetalleChatbotsQuizzes.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.lista_detalles_chatbot_quiz,null);
        return  new AdapterDetalleChatbotsQuizzes.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDetalleChatbotsQuizzes.CustomViewHolder holder, int position) {
        final InfoDetalles practicaDetalles = listinfodetalles.get(position);
        holder.numero.setText(practicaDetalles.getNumero());
        Glide.with(c).load(practicaDetalles.getUrl()).into(holder.url);
        holder.respuestau.setText(practicaDetalles.getRespuestau());
        holder.tiempo.setText(practicaDetalles.getTiempo());

    }

    @Override
    public int getItemCount() {
        return listinfodetalles.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView numero, respuestau,tiempo;
        ZoomInImageView url;
        ConstraintLayout constrain ;

        public CustomViewHolder (View itemView)
        {
            super(itemView);
            numero = itemView.findViewById(R.id.txt_numero);
            url = itemView.findViewById(R.id.txt_url);
            respuestau = itemView.findViewById(R.id.txt_respuestau);
            tiempo = itemView.findViewById(R.id.txt_tiempo);
            constrain = itemView.findViewById(R.id.relativeLayout);
        }
    }
}