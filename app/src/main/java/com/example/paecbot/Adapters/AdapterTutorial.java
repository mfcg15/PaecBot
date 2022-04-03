package com.example.paecbot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.example.paecbot.Objetos.ScrenItem;
import com.example.paecbot.R;

import java.util.List;

public class AdapterTutorial extends PagerAdapter {

    Context contexto;
    List<ScrenItem> mlistscreen;
    int [] anInt;

    public AdapterTutorial(Context context, List<ScrenItem> mlistscreen)
    {
        this.contexto = context;
        this.mlistscreen = mlistscreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.imagenes_tutoriales,null);

        View imageView = layoutScreen.findViewById(R.id.imagen_tutorial);

        imageView.setBackground(ContextCompat.getDrawable(contexto,mlistscreen.get(position).getFototutorial()));

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mlistscreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
