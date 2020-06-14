package com.example.covid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.covid.actividades.FichaPersonal;
import com.example.covid.actividades.Verificacion;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.Nacionalidad;
import com.example.covid.entidades.TipoDocumento;
import com.example.covid.entidades.UsuarioCasos;
import com.example.covid.servicios.ProyectoService;
import com.example.covid.util.ConnectionRest;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private ProyectoService postService;
    private static final String TAG = "LogsAndroid";
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

                    String telefono = txtNumero.getText().toString().trim();
                    Boolean condicion = chkAceptar.isChecked();

                /*SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");
                String sFecha = fecha.format(nacimiento);
                Date dat=new Date();*/
                try {
                   // dat = fecha.parse(sFecha);
                    UsuarioCasos obj =new UsuarioCasos();
                    obj.setTelefono(telefono);
                    obj.setCondicionUso(condicion);
                    registrarUsuarioCasos(obj);

                    Log.i(TAG, "onClick: " + obj.getId());
                } catch (Exception e ){
                    e.printStackTrace();
                }



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

    private void registrarUsuarioCasos(UsuarioCasos obj){
        Log.i(TAG, "PASO 0_crearusuarioscasos: " + obj);
        postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<UsuarioCasos> call =postService.saveUsuariosCasos(obj);
        Log.i(TAG, "PASO 1_crearusuarioscasos: " + call);
        call.enqueue(new Callback<UsuarioCasos>() {
            @Override
            public void onResponse(Call<UsuarioCasos> call, Response<UsuarioCasos> response) {
                Log.i(TAG, "PASO 2_crearusuarioscasos: " + response);
                if (!response.isSuccessful()){
                    Log.i(TAG, "Algo salio mal" + response.body().toString());
                }else{
                    Log.i(TAG, "Post Subido al API" + response.body().toString());
                    Log.i(TAG, "PASO 3.1_crearusuarioscasos: " + response);
                    Log.i(TAG, "PASO 3.2_crearusuarioscasos: " + response.errorBody());
                    Log.i(TAG, "PASO 3.3_crearusuarioscasos: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<UsuarioCasos> call, Throwable t) {
                Log.i(TAG, "Improbable subir POST al API");
                Toast.makeText(MainActivity.this, "UsuarioCasos Registrado (ver retorno)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
