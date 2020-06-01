package com.example.covid.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.covid.R;

public class Triaje extends AppCompatActivity  {

    RadioButton rb1,rb2,rb3,rb4,rb5;
    CheckBox chkSintomas;
    Button btnEvaluacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triaje);


        chkSintomas=findViewById(R.id.chkSintomas);
        btnEvaluacion = findViewById(R.id.btnEvaluacion);


        btnEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FichaPersonal.class);
                startActivity(intent);
            }
        });


    }

    public void mensaje(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mensaje);
        builder.setMessage(R.string.mensaje2);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Menu.class );
                startActivity(intent);
            }
        });

        builder.setNegativeButton(android.R.string.cancel,null);
        Dialog dialog = builder.create();
        dialog.show();
    }
/*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                if(rb1.isSelected()){
                    rb1.setSelected(false);
                    rb1.setChecked(false);
            } else {
                    rb1.setSelected(true);
                    rb1.setChecked(true);

            }
            break;

            case R.id.rb2:
                if(rb2.isSelected()){
                    rb2.setSelected(false);
                    rb2.setChecked(false);
                } else {
                    rb2.setSelected(true);
                    rb2.setChecked(true);

                }
                break;

            case R.id.rb3:
                if(rb3.isSelected()){
                    rb3.setSelected(false);
                    rb3.setChecked(false);
                } else {
                    rb3.setSelected(true);
                    rb3.setChecked(true);

                }
                break;
            case R.id.rb4:
                if(rb4.isSelected()){
                    rb4.setSelected(false);
                    rb4.setChecked(false);
                } else {
                    rb4.setSelected(true);
                    rb4.setChecked(true);

                }
                break;

            case R.id.rb5:
                if(rb5.isSelected()){
                    rb5.setSelected(false);
                    rb5.setChecked(false);
                } else {
                    rb5.setSelected(true);
                    rb5.setChecked(true);

                }
                break;

        }
    }*/
}
