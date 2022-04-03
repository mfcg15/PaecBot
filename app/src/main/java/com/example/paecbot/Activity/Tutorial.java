package com.example.paecbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.paecbot.Adapters.AdapterTutorial;
import com.example.paecbot.Objetos.ScrenItem;
import com.example.paecbot.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Tutorial extends AppCompatActivity {

    private ViewPager screenPager;
    TabLayout tableLayout;
    Button boton, botonc;
    AdapterTutorial introviewpagerAdapter;
    Animation btnAnim;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        if(restorePrefData())
        {
            Intent intent = new Intent(Tutorial.this, Menu.class);
            startActivity(intent);
            finish();
        }

        screenPager = (ViewPager) findViewById(R.id.rpe);
        tableLayout = (TabLayout) findViewById(R.id.tabLayou);
        boton = (Button) findViewById(R.id.button);
        botonc = (Button) findViewById(R.id.button2) ;
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        List<ScrenItem> mList = new ArrayList<>();
        mList.add(new ScrenItem(R.drawable.tutorial1));
        mList.add(new ScrenItem(R.drawable.tutorial2));
        mList.add(new ScrenItem(R.drawable.tutorial3));
        mList.add(new ScrenItem(R.drawable.tutorial4));
        mList.add(new ScrenItem(R.drawable.tutorial5));

        introviewpagerAdapter = new AdapterTutorial(this,mList);
        screenPager.setAdapter(introviewpagerAdapter);

        tableLayout.setupWithViewPager(screenPager);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();

                if(position < mList.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position== mList.size()-1)
                {
                    loadlastscreen();
                }

            }
        });

        tableLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == mList.size()-1)
                {
                    loadlastscreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        botonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial.this, Menu.class);
                savePrefsData();
                startActivity(intent);
                finish();
            }
        });

    }


    private boolean restorePrefData()
    {
        SharedPreferences pref = getApplication().getSharedPreferences("myprefs",MODE_PRIVATE);
        Boolean asd = pref.getBoolean("isIntroOpnend",false);
        return asd;
    }

    private  void loadlastscreen()
    {
        boton.setVisibility(View.INVISIBLE);
        botonc.setVisibility(View.VISIBLE);
        tableLayout.setVisibility(View.VISIBLE);
        botonc.setAnimation(btnAnim);
    }

    private void savePrefsData()
    {
        SharedPreferences pref = getApplication().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }


}
