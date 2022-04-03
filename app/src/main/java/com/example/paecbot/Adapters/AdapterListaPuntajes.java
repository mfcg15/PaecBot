package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.paecbot.Objetos.Ranking;
import com.example.paecbot.R;

import java.util.List;


public class AdapterListaPuntajes extends RecyclerView.Adapter<AdapterListaPuntajes.CustomViewHolder>{

    private List<Ranking> ListaRanking ;
    private Context c ;

    public AdapterListaPuntajes(List<Ranking> ListaRanking, Context c) {
        this.ListaRanking = ListaRanking;
        this.c = c;
    }

    @Override
    public AdapterListaPuntajes.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.lista_puntajes,null);
        return  new AdapterListaPuntajes.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterListaPuntajes.CustomViewHolder holder, int position) {
        final Ranking ranking = ListaRanking.get(position);
        holder.puesto.setText(ranking.getPuesto());
        holder.nombreuser.setText(ranking.getUser());
        holder.score.setText("Puntaje : "+ranking.getPuntaje()+" puntos");


        if(position==0)
        {
            holder.bpuesto.setBackground(ContextCompat.getDrawable(c, R.drawable.puesto1));
            holder.puesto.setText("");
        }
        if(position==1)
        {
            holder.bpuesto.setBackground(ContextCompat.getDrawable(c,R.drawable.puesto2));
            holder.puesto.setText("");
        }
        if(position==2)
        {
            holder.bpuesto.setBackground(ContextCompat.getDrawable(c,R.drawable.puesto3));
            holder.puesto.setText("");
        }


        if(position == ListaRanking.size()-1)
        {
            holder.separadorp.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ListaRanking.size();
    }



    class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView puesto,nombreuser, score ;
        LinearLayout separadorp, bpuesto;

        public CustomViewHolder (View itemView)
        {
            super(itemView);

            puesto = (TextView) itemView.findViewById(R.id.txt_puesto);
            nombreuser = (TextView)itemView.findViewById(R.id.txt_nombreuse);
            score = (TextView)itemView.findViewById(R.id.txt_puntajetotal);
            separadorp = (LinearLayout) itemView.findViewById(R.id.separador_puntajes);
            bpuesto = (LinearLayout) itemView.findViewById(R.id.BPuesto);
        }
    }

}
