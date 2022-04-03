package com.example.paecbot.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paecbot.R;

public class SobreMi extends Fragment {

    TextView click;

    public SobreMi() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sobre_mi, container, false);
        click = (TextView) view.findViewById(R.id.clikea);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + click.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Consulta");
                startActivity(intent);
            }
        });
    }
}
