package com.example.paecbot.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paecbot.Activity.Chatbot;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Objetos.Chatbots;
import com.example.paecbot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterListaChatbot extends RecyclerView.Adapter<AdapterListaChatbot.CustomViewHolder> {

    private List<Chatbots> ListaChatbots;
    private Context contexto ;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public AdapterListaChatbot(List<Chatbots> ListaChatbots, Context contexto) {
        this.ListaChatbots = ListaChatbots;
        this.contexto = contexto;
    }

    @Override
    public AdapterListaChatbot.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View view = inflater.inflate(R.layout.lista_chatbots,null);
        return  new AdapterListaChatbot.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterListaChatbot.CustomViewHolder holder, int position) {

        final Chatbots chatbot = ListaChatbots.get(position);
        Glide.with(contexto).load(chatbot.getFotoperfil()).into(holder.fotoperfil);
        holder.nombrechatbot.setText(chatbot.getNombre());



        mDatabase.child("Chatbot").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(chatbot.getTipo()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String a = snapshot.child("hora").getValue().toString();
                    String b = snapshot.child("lastmensaje").getValue().toString();
                    holder.horaenvio.setText(a);
                    holder.ultimomensaje.setText(b);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if(position==0)
        {
            holder.separador.setText("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        if (position==1)
        {
            holder.separador.setText("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }

        if (position==2)
        {
            holder.separador.setText("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }

        if (position==3)
        {
            holder.separador.setText(chatbot.getSeparador());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(holder.itemView.getContext(), Chatbot.class);
                intent.putExtra(Chatbot.nombrechatbot,chatbot.getNombre());
                intent.putExtra(Chatbot.fotoperfil,chatbot.getFotoperfil());
                intent.putExtra(Chatbot.inic,chatbot.getInicio());
                intent.putExtra(Chatbot.tern,chatbot.getTermino());
                intent.putExtra(Chatbot.tipo,chatbot.getTipo());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListaChatbots.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombrechatbot,ultimomensaje, horaenvio , separador;
        CircleImageView fotoperfil;

        public CustomViewHolder (View itemView)
        {
            super(itemView);

            nombrechatbot = (TextView) itemView.findViewById(R.id.nombrechatot);
            ultimomensaje = (TextView)itemView.findViewById(R.id.ultimomensaje);
            horaenvio = (TextView)itemView.findViewById(R.id.horaenvio);
            separador = (TextView)itemView.findViewById(R.id.separador);
            fotoperfil = (CircleImageView) itemView.findViewById(R.id.imagen_perfil);
        }
    }

}
