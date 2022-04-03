package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paecbot.Objetos.Mensaje;
import com.example.paecbot.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterMensajes extends RecyclerView.Adapter<AdapterMensajes.CustomViewHolder> {

    private List<Mensaje> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }


    @NonNull
    @Override
    public AdapterMensajes.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==1)
        {
            View view = LayoutInflater.from(c).inflate(R.layout.mensaje_emisor,parent,false);
            return new CustomViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(c).inflate(R.layout.mensaje_receptor,parent,false);

            return new CustomViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final AdapterMensajes.CustomViewHolder holder, final int position) {

        holder.mensaje.setText(listMensaje.get(position).getMensaje());
        holder.horamensaje.setText(listMensaje.get(position).getHoramensaje());

        if (!(listMensaje.get(position).isEstado()))
        {
            Glide.with(c).load(listMensaje.get(position).getFoto_perfil()).into(holder.fotoperfilmensaje);
        }

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }



    @Override
    public int getItemViewType(int position) {
        if (listMensaje.get(position).isEstado()) {

            return 1;
        }

        else
        {
            return -1;
        }
    }


    class CustomViewHolder extends RecyclerView.ViewHolder
    {

        private TextView mensaje, horamensaje;
        private CircleImageView fotoperfilmensaje;

        public CustomViewHolder (View itemView)
        {
            super(itemView);
            mensaje = (TextView) itemView.findViewById(R.id.mensajeMensaje);
            horamensaje = (TextView) itemView.findViewById(R.id.horamensaje);
            fotoperfilmensaje = (CircleImageView) itemView.findViewById(R.id.fotoPerfilMensaje);
        }
    }

}