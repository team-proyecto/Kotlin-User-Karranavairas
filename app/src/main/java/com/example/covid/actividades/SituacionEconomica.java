package com.example.covid.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid.R;
import com.google.android.material.textfield.TextInputLayout;

public class SituacionEconomica extends AppCompatActivity{


    ImageView   imgID;
    CheckBox chkBeneficiario;
    Button  btnImagen;
    TextView txtRecibo;
    TextInputLayout tilMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_economica);

        imgID = findViewById(R.id.imgId);
        chkBeneficiario = findViewById(R.id.chkBeneficiario);
        btnImagen = findViewById(R.id.btnImagen);
        txtRecibo = findViewById(R.id.txtRecibo);
        tilMonto = findViewById(R.id.tilMonto);

        chkBeneficiario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                        tilMonto.setVisibility(View.VISIBLE);
                        txtRecibo.setVisibility(View.VISIBLE);
                        imgID.setVisibility(View.VISIBLE);
                        btnImagen.setVisibility(View.VISIBLE);
                }else{
                        tilMonto.setVisibility(View.INVISIBLE);
                        txtRecibo.setVisibility(View.INVISIBLE);
                        imgID.setVisibility(View.INVISIBLE);
                        btnImagen.setVisibility(View.INVISIBLE);
                }
            }
        });



    }
}
