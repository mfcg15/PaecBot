package com.example.paecbot.Fragments.Reporte;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paecbot.Codigo.fragmentmanager;
import com.example.paecbot.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class ReporteChatsQuiz extends Fragment {


    View my;
    TabLayout tabLayout;
    TabItem tab1, tab2;
    ViewPager viewPager;
    fragmentmanager fragmentmanager;

    public ReporteChatsQuiz() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        my = inflater.inflate(R.layout.fragment_lista_chat_quiz, container, false);

        tabLayout = (TabLayout)  my.findViewById(R.id.ctabla);
        tab1 = (TabItem) my.findViewById(R.id.ctab1);
        tab2 = (TabItem) my.findViewById(R.id.ctab2);
        viewPager = (ViewPager) my.findViewById(R.id.page);

        return my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager)
    {
        fragmentmanager = new fragmentmanager(getChildFragmentManager());

        fragmentmanager.addFragment(new ReporteChats(),"Chats");
        fragmentmanager.addFragment(new ReporteQuiz(),"Quizzes");

        viewPager.setAdapter(fragmentmanager);

    }

}
