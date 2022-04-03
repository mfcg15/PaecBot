package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paecbot.Objetos.Informacion;
import com.example.paecbot.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdapterChatbotsQuizzes extends RecyclerView.Adapter<AdapterChatbotsQuizzes.PracticasViewHolder> implements View.OnClickListener {

    private List<Informacion> ListInformacion ;
    private List<Informacion> orgininali;
    private Context c ;
    private View.OnClickListener listener;

    public AdapterChatbotsQuizzes(Context c, List<Informacion> ListInformacion) {
        this.c = c;
        this.ListInformacion = ListInformacion;
        this.orgininali = new ArrayList<>();
        orgininali.addAll(ListInformacion);
    }

    @Override
    public AdapterChatbotsQuizzes.PracticasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==1)
        {
            View view = LayoutInflater.from(c).inflate(R.layout.lista_chatbot_quiz,null);
            view.setOnClickListener(this);
            return  new AdapterChatbotsQuizzes.PracticasViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(c).inflate(R.layout.lista_vacia_chatbot_quiz,null);
            view.setOnClickListener(this);
            return new AdapterChatbotsQuizzes.PracticasViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final AdapterChatbotsQuizzes.PracticasViewHolder holder, int position) {

        if(ListInformacion.get(position).isEstado())
        {

            holder.nivel.setAnimation(AnimationUtils.loadAnimation(c, R.anim.fade_transition_animation));
            holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(c,R.anim.fade_scale_animation));

            final Informacion examenes = ListInformacion.get(position);

            String aux = examenes.getTipo();

            switch(aux)
            {
                case "Cesar":

                    Glide.with(c).load("https://i.ibb.co/HKcGzdR/ccomb.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==18)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(18-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Camila":

                    Glide.with(c).load("https://i.ibb.co/4FJLm0y/ccam.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==18)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(18-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Claudia":

                    Glide.with(c).load("https://i.ibb.co/t8MTqNL/ccom.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==18)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(18-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Ignacio":

                    Glide.with(c).load("https://i.ibb.co/9WG5Lbj/cigu.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==18)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(18-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Quiz N° 1":

                    Glide.with(c).load("https://i.ibb.co/1d9my14/qcomp.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==10)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(10-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Quiz N° 2":

                    Glide.with(c).load("https://i.ibb.co/zbbF0TF/qi3.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==21)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(21-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");

                    }

                    break;

                case "Quiz N° 3":

                    Glide.with(c).load("https://i.ibb.co/BBz9LX5/qi4.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==21)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(21-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }

                    break;

                case "Quiz N° 4":

                    Glide.with(c).load("https://i.ibb.co/k1SzJZv/qigu.jpg").into(holder.nivel);

                    if(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas())==21)
                    {
                        holder.estado.setText("Completo");
                        holder.sin_responder.setVisibility(View.GONE);
                        holder.puntos.setVisibility(View.GONE);
                        holder.label_sin.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.estado.setText("Incompleto");
                        holder.sin_responder.setText(21-(Integer.parseInt(examenes.getCorrectas())+Integer.parseInt(examenes.getIncorrectas()))+"");
                    }
                    break;
            }

            holder.correctas.setText(examenes.getCorrectas());
            holder.incorrectas.setText(examenes.getIncorrectas());
            holder.puntaje.setText(examenes.getPuntaje()+" puntos");
            holder.fecha.setText(examenes.getFecha());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ListInformacion.get(position).isEstado()) {

            return 1;
        }

        else
        {
            return -1;
        }
    }


    @Override
    public int getItemCount() {
        return ListInformacion.size();
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

    public void filter(final String strSearch)
    {
        int longitud = strSearch.length();

        if(longitud == 0)
        {
            ListInformacion.clear();
            ListInformacion.addAll(orgininali);
        }
        else
        {
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                List<Informacion> collect = ListInformacion.stream().filter(i -> i.getFecha().toLowerCase().contains(strSearch.toLowerCase())).collect(Collectors.toList());
                ListInformacion.clear();
                ListInformacion.addAll(collect);
            }
            else
            {
                for (Informacion i : orgininali)
                {
                    if(i.getFecha().toLowerCase().contains(strSearch.toLowerCase()))
                    {
                        ListInformacion.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    static class PracticasViewHolder extends RecyclerView.ViewHolder
    {
        TextView correctas,incorrectas,fecha,puntaje, estado, sin_responder, label_sin, puntos;
        RelativeLayout relativeLayout;
        ImageView nivel ;

        public PracticasViewHolder (View itemView)
        {
            super(itemView);

            nivel = itemView.findViewById(R.id.txt_nive);
            correctas = itemView.findViewById(R.id.txt_correctas);
            incorrectas = itemView.findViewById(R.id.txt_incorrectas);
            fecha = itemView.findViewById(R.id.txt_fecha);
            puntaje = itemView.findViewById(R.id.txt_puntuacion);
            estado = itemView.findViewById(R.id.txt_estado);
            sin_responder = itemView.findViewById(R.id.txt_sinResponder);
            label_sin = itemView.findViewById(R.id.label_sin);
            puntos = itemView.findViewById(R.id.punts_sin);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

        }
    }

}
