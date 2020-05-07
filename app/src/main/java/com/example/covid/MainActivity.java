package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.covid.actividades.Verificacion;

public class MainActivity extends AppCompatActivity{
    AwesomeValidation awesomeValidation;
    EditText txtNumero;
    Button btnEnviar;
    CheckBox chkAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumero = findViewById(R.id.txtNumero);
        btnEnviar = findViewById(R.id.btnEnviar);
        chkAceptar = findViewById(R.id.chkAceptar);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(txtNumero,"[1-9]{4}","Solo 4 dígitos");

        chkAceptar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    btnEnviar.setVisibility(View.VISIBLE);
                }else{
                    btnEnviar.setVisibility(View.INVISIBLE);
                }

            }
        });


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest
                .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
    }else{
            };

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    enviarMensaje("923001670", "Tú código de verificación es: 1234");
                    Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                    startActivity(intent);
            }
        });

    }

    public void enviarMensaje(String numero, String mensaje){
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numero,null,mensaje,null,null);
                Toast.makeText(getApplicationContext(),"Tu código de verificación se ha enviado",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Mensaje no enviado ",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
    }
}
