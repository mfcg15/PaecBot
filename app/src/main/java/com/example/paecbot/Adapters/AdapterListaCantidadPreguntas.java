package com.example.paecbot.Adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paecbot.Activity.Menu;
import com.example.paecbot.Activity.Quiz;
import com.example.paecbot.R;


public class AdapterListaCantidadPreguntas extends BaseAdapter {

    private int sets = 0;
    private String tipo = "", idquiz = "", cantidad = "";

    public AdapterListaCantidadPreguntas(int sets, String tipo, String idquiz, String cantidad)
    {
        this.sets = sets;
        this.tipo = tipo;
        this.idquiz = idquiz;
        this.cantidad = cantidad;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final View view;

        if(convertView==null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        }
        else
        {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(parent.getContext(), Quiz.class);
              intent.putExtra(Quiz.totalpreguntas,position+1+"");
              intent.putExtra(Quiz.tip,tipo+"");
              intent.putExtra(Quiz.niv,idquiz+"");
              intent.putExtra(Quiz.canti,cantidad+"");
              parent.getContext().startActivity(intent);
              ((Menu)parent.getContext()).finish();
            }
        });


        ((TextView)view.findViewById(R.id.txt_cantidad)).setText(String.valueOf(position+1));

        return view;
    }
}
