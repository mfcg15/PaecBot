package com.example.paecbot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Codigo.iComunicaFragments;
import com.example.paecbot.Fragments.ATutorial;
import com.example.paecbot.Fragments.CambiarContrasena;
import com.example.paecbot.Fragments.EditarDatos;
import com.example.paecbot.Fragments.Juego.ListaCantidadPreguntas;
import com.example.paecbot.Fragments.Juego.ListasChatsQuiz;
import com.example.paecbot.Fragments.Puntajes;
import com.example.paecbot.Fragments.Reporte.DetalleReporte.DetalleChatbot;
import com.example.paecbot.Fragments.Reporte.DetalleReporte.DetalleQuiz;
import com.example.paecbot.Fragments.Reporte.ReporteChatsQuiz;
import com.example.paecbot.Fragments.SobreMi;
import com.example.paecbot.Objetos.Informacion;
import com.example.paecbot.Objetos.Quizzes;
import com.example.paecbot.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageButton btn_cerrar_sesion;
    View view;
    TextView nombrecompleto , correouser;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Fragment temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        btn_cerrar_sesion = (ImageButton) findViewById(R.id.btn_salir);

        view = navigationView.getHeaderView(0);

        nombrecompleto = (TextView)view.findViewById(R.id.txt_nombrecompleto);
        correouser = (TextView) view.findViewById(R.id.txt_correousuario);

        Usuario();

        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ListasChatsQuiz()).commit();
        toolbar.setTitle("Juego");


        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch(item.getItemId())
        {
            case R.id.Juego:
                temp = new ListasChatsQuiz();
                toolbar.setTitle("Juego");
                break;

            case R.id.Reporte:
                temp = new ReporteChatsQuiz();
                toolbar.setTitle("Reporte");
                break;

            case R.id.Editar_perfil:
                temp = new EditarDatos();
                toolbar.setTitle("Editar perfil");
                break;

            case R.id.Cambiar_contraseña:
                temp = new CambiarContrasena();
                toolbar.setTitle("Cambiar contraseña");
                break;

            case R.id.Ranking_de_puntajes:
                temp = new Puntajes();
                toolbar.setTitle("Ranking de puntajes");
                break;

            case R.id.tutorial:
                temp = new ATutorial();
                toolbar.setTitle("Ayuda");
                break;

            case R.id.acerca:
                temp = new SobreMi();
                toolbar.setTitle("Sobre mi");
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
        return true;
    }


    void Usuario()
    {
        mDatabase.child("Usuarios").child(UsuarioDAO.getInstancia().getKeyUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    nombrecompleto.setText(snapshot.child("nombres").getValue().toString()+" "+snapshot.child("apellidos").getValue().toString());
                    correouser.setText(snapshot.child("correo").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void eichat(Informacion informacion) {
        DetalleChatbot detalleChatbot = new DetalleChatbot();
        Bundle bundle = new Bundle();
        bundle.putSerializable("objeto",informacion);
        detalleChatbot.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,detalleChatbot).addToBackStack(null).commit();
    }

    @Override
    public void eiquiz(Informacion informacion) {
        DetalleQuiz detalleQuiz = new DetalleQuiz();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cosa",informacion);
        detalleQuiz.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,detalleQuiz).addToBackStack(null).commit();
    }

    @Override
    public void einfoq(Quizzes quizzes) {
        ListaCantidadPreguntas listaCantidadPreguntas = new ListaCantidadPreguntas();
        Bundle bundle = new Bundle();
        bundle.putSerializable("envia",quizzes);
        listaCantidadPreguntas.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,listaCantidadPreguntas).addToBackStack(null).commit();
    }
}
