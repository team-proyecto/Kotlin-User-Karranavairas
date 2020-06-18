package com.example.covid.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.EstadoMedico;
import com.example.covid.entidades.Gps;
import com.example.covid.entidades.Nacionalidad;
import com.example.covid.entidades.ReporteEconomico;
import com.example.covid.entidades.ReporteMedico;
import com.example.covid.entidades.TipoDocumento;
import com.example.covid.entidades.TipoUsuario;
import com.example.covid.entidades.UsuarioCasos;
import com.example.covid.servicios.ProyectoService;
import com.example.covid.util.ConnectionRest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Triaje extends AppCompatActivity  {

    private ProyectoService postService;
    private static final String TAG = "LogsAndroid";
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
                Long id = Long.valueOf(2);
                try {
                    ReporteMedico estado = new ReporteMedico();
                    Boolean sintomas = chkSintomas.isChecked();
                    estado.setResultadoTriaje(sintomas);
                    updateUsuariosCaso(id);
                }catch (Exception e){

                }

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

    private void actualizarUsuarioCasos(Long id, UsuarioCasos obj){

        //se actualiza los campos necesarios

        Log.i(TAG, "valor  de codigo confirmación actualizado: " +  obj.getReporteMedico());
        Log.i(TAG, "valor  de codigo confirmación actualizado: " +  obj.getId());

        Log.i(TAG, "PASO 0_actualizarusuarioscasos: " + obj);
        postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<UsuarioCasos> call =postService.updateUsuariosCasos(id,obj);
        Log.i(TAG, "PASO 0_actualizarusuarioscasos: " + call);
        call.enqueue(new Callback<UsuarioCasos>() {
            @Override
            public void onResponse(Call<UsuarioCasos> call, Response<UsuarioCasos> response) {
                Log.i(TAG, "PASO 0_actualizarusuarioscasos: " + response);
                Log.i(TAG, "PASO 1_actualizarusuarioscasos: " + response.body());
                if (!response.isSuccessful()){
                    Log.i(TAG, "Algo salio mal" + response.body().toString());
                }else{
                    Log.i(TAG, "Put Subido al API" + response.body().toString());
                    Log.i(TAG, "PASO 3.0_actualizarusuarioscasos: " + response);
                    Log.i(TAG, "PASO 3.0_actualizarusuarioscasos: " + response.errorBody());
                    Log.i(TAG, "PASO 3.0_actualizarusuarioscasos: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<UsuarioCasos> call, Throwable t) {
                Log.i(TAG, "Improbable subir PUT al API");
                Toast.makeText(Triaje.this, "UsuarioCasos Actualizado (ver retorno)", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateUsuariosCaso(Long id) {

        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);

        Call<UsuarioCasos> call = postService.obtenerUsuariosCaso(id);

        call.enqueue(new Callback<UsuarioCasos>() {
            @Override
            public void onResponse(Call<UsuarioCasos> call, Response<UsuarioCasos> response) {
                //UsuarioCasos usuarioOriginal;
                if (response.isSuccessful()) {
                    /*SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");
                    String sFecha = fecha.format(fecha);
                    Date dat=new Date();*/
                    try {

                        final UsuarioCasos com = response.body(); // Esto es json completo


                        UsuarioCasos reg = new UsuarioCasos();

                        /*UsuarioCasos global = (UsuarioCasos)getApplicationContext();
                        global.setCodigoConfirmacion(1212);

                        UsuarioCasos lectura = (UsuarioCasos)getApplicationContext();
                        txtCodigo.setText(lectura.getCodigoConfirmacion());*/

                        reg.setId(com.getId());
                        reg.setNombre(com.getNombre());
                        reg.setApellidos(com.getApellidos());
                        Nacionalidad nacional = new Nacionalidad();
                        nacional.setId(com.getNacionalidad().getId());
                        reg.setNacionalidad(nacional);
                        TipoDocumento documento = new TipoDocumento();
                        documento.setId(com.getTipoDocumento().getId());
                        reg.setTipoDocumento(documento);
                        reg.setNumeroDocumento(com.getNumeroDocumento());
                        //reg.setFechaNacimiento(com.getFechaNacimiento());
                        //Log.i(TAG, "fechanacimiento: " + dat);
                        Distritos dist = new Distritos();
                        dist.setId(com.getDistritos().getId());
                        reg.setDistritos(dist);
                        reg.setTelefono(com.getTelefono());
                        reg.setDireccionDomicilio(com.getDireccionDomicilio());
                        reg.setCodigoConfirmacion(com.getCodigoConfirmacion());
                        reg.setCondicionUso(com.getCondicionUso());
                        //reg.setFechaRegistro(com.getFechaRegistro());
                        //Log.i(TAG, "fecharegistro: " + dat;
                        Gps gps = new Gps();
                        gps.setId(com.getGps().getId());
                        reg.setGps(gps);
                        TipoUsuario tipoUsuario = new TipoUsuario();
                        tipoUsuario.setId(com.getTipoUsuario().getId());
                        reg.setTipoUsuario(tipoUsuario);
                        ReporteEconomico reporteEconomico = new ReporteEconomico();
                        reporteEconomico.setId(com.getReporteEconomico().getId());
                        reg.setReporteEconomico(reporteEconomico);
                        ReporteMedico reporteMedico = new ReporteMedico();
                        reporteMedico.setId(com.getReporteMedico().getId());
                        reg.setReporteMedico(reporteMedico);
                        reg.setEstado(com.getEstado());

                        //logs
                        Log.i(TAG, "getId: " + reg.getId());
                        Log.i(TAG, "getNombre: " + reg.getNombre());
                        Log.i(TAG, "getApellidos: " + reg.getApellidos());
                        Log.i(TAG, "getNacionalidad: " + reg.getNacionalidad().getId());
                        Log.i(TAG, "getTipoDocumento: " + reg.getTipoDocumento().getId());
                        Log.i(TAG, "getNumeroDocumento: " + reg.getNumeroDocumento());
                        Log.i(TAG, "getFechaNacimiento: " + reg.getFechaNacimiento());
                        Log.i(TAG, "getDistritos: " + reg.getDistritos().getId());
                        Log.i(TAG, "getTelefono: " + reg.getTelefono());
                        Log.i(TAG, "getDireccionDomicilio: " + reg.getDireccionDomicilio());
                        Log.i(TAG, "getCodigoConfirmacion: " + reg.getCodigoConfirmacion());
                        Log.i(TAG, "getCondicionUso: " + reg.getCondicionUso());
                        Log.i(TAG, "getFechaRegistro: " + reg.getFechaRegistro());
                        Log.i(TAG, "getGps: " + reg.getGps().getId());
                        Log.i(TAG, "getTipoUsuario: " + reg.getTipoUsuario().getId());
                        Log.i(TAG, "getReporteEconomico: " + reg.getReporteEconomico().getId());
                        Log.i(TAG, "getReporteMedico: " + reg.getReporteMedico().getId());
                        Log.i(TAG, "getEstado: " + reg.getEstado());

                        Log.i(TAG, "idpara actualizar: " + Long.valueOf(reg.getId()));
                        actualizarUsuarioCasos(Long.valueOf(reg.getId()),reg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("Base", "El metodo ha fallado" + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<UsuarioCasos> call, Throwable t) {

            }

        });


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
