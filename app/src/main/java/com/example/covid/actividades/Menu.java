package com.example.covid.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covid.R;

public class Menu extends AppCompatActivity {
    Button btnVerInfectados, btnTriaje, btnSituacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnVerInfectados = findViewById(R.id.btnVerInfectados);
        btnTriaje = findViewById(R.id.btnTriaje);
        btnSituacion = findViewById(R.id.btnSituacion);

        btnTriaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Triaje.class);
                startActivity(intent);
            }
        });

        btnSituacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SituacionEconomica.class);
                startActivity(intent);
            }
        });
    }
}
