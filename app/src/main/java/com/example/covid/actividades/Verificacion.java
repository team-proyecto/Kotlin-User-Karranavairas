package com.example.covid.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.covid.R;

public class Verificacion extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    EditText txtCodigo;
    Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        btnContinuar = findViewById(R.id.btnContinuar);
        txtCodigo = findViewById(R.id.txtCodigo);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(txtCodigo,"[1-9]{4}","Solo 4 dígitos");

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                    Intent intent = new Intent(getApplicationContext(), Triaje.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Codígo Inválido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
