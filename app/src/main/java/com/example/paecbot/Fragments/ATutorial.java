package com.example.paecbot.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paecbot.Adapters.AdapterSlider;
import com.example.paecbot.Objetos.ScrenItem;
import com.example.paecbot.R;

import java.util.ArrayList;
import java.util.List;

public class ATutorial extends Fragment {

    ViewPager2 viewPager2;

    public ATutorial() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_atutorial, container, false);

        viewPager2 = (ViewPager2) view.findViewById(R.id.viewPagerImagenSlider);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        List<ScrenItem> mList = new ArrayList<>();
        mList.add(new ScrenItem(R.drawable.tutorial1));
        mList.add(new ScrenItem(R.drawable.tutorial2));
        mList.add(new ScrenItem(R.drawable.tutorial3));
        mList.add(new ScrenItem(R.drawable.tutorial4));
        mList.add(new ScrenItem(R.drawable.tutorial5));

        viewPager2.setAdapter(new AdapterSlider(mList,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

    }

}
