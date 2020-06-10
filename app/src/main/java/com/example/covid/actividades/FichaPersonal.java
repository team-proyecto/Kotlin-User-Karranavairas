package com.example.covid.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.covid.entidades.Departamentos;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.Nacionalidad;
import com.example.covid.entidades.Provincias;
import com.example.covid.entidades.UsuarioCasos;
import com.example.covid.servicios.ProyectoService;
import com.example.covid.util.ConnectionRest;
import com.loopj.android.http.*;

import com.example.covid.R;
import com.example.covid.entidades.TipoDocumento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FichaPersonal extends AppCompatActivity implements View.OnClickListener {
    Spinner combo_tipoDocumento, combo_provincias, combo_distritos,combo_departamentos, combo_nacionalidad;
    private AsyncHttpClient cliente;

    private ProyectoService postService;
    private static final String TAG = "LogsAndroid";
    Button btnRegistrarse;
    ArrayAdapter arrayAdapter;
    ArrayList<String> opcionesSpnView = new ArrayList<String>();
    List<TipoDocumento> documentos = new ArrayList<TipoDocumento>();
    private LocationManager ubicacion;
    TextView latitud, longitud;
    EditText txtID,txtNombres, txtApellidos,txtDNI,txtNacimiento,txtTelefono ,txtDireccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_personal);
        final Date date = new Date();
        cliente = new AsyncHttpClient();
        combo_tipoDocumento = (Spinner)findViewById(R.id.spnTipoDocumentos);
        combo_provincias = (Spinner)findViewById(R.id.spnProvincias);
        combo_distritos = (Spinner)findViewById(R.id.spnProvincias);
        combo_departamentos = (Spinner)findViewById(R.id.spnDepartamentos);
        txtNombres = (EditText)findViewById(R.id.txtNombres);
        txtApellidos = (EditText)findViewById(R.id.txtApellidos);
        combo_nacionalidad = (Spinner)findViewById(R.id.spnNacionalidad);
        txtDNI = (EditText)findViewById(R.id.txtDNI);
        txtNacimiento = (EditText)findViewById(R.id.txtNacimiento);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);



        localizacion();
        //listaProviders();
        //mejorCriterio();
        //estadoGPS();
        registrarLocalizacion();
        cargaDocumentos();
        //cargaDistritos();
        cargaNacionalidad();
        //cargaProvincia();
        cargaDepartamentos();


        combo_departamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Cargar Provincia a Seleccionar un Departamento
                Long idDepartamento = combo_departamentos.getSelectedItemId();
                if (idDepartamento != null){
                    cargaProvincia(idDepartamento);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        combo_provincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cargar Provincia a Seleccionar un Departamento
                Long idProvincia = combo_provincias.getSelectedItemId();
                if (idProvincia != null){
                    cargaDistritos(idProvincia);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegistrarse = findViewById(R.id.btnRegistarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);*/

                String nombre = txtNombres.getText().toString().trim();
                String apellidos = txtApellidos.getText().toString().trim();
                String dni = txtDNI.getText().toString().trim();
                int nacionalidad = combo_nacionalidad.getSelectedItemPosition();
                int distritos = combo_distritos.getSelectedItemPosition();
                int documentos = combo_tipoDocumento.getSelectedItemPosition();
                String nacimiento = txtNacimiento.getText().toString().trim();
                String telefono = txtTelefono.getText().toString().trim();
                String direccion = txtDireccion.getText().toString().trim();
                //Nacionalidad es combo lo estas poniendo como text
                //pensaba que se ingresaba, ya es´ta

                SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");
                String sFecha = fecha.format(nacimiento);
                Date dat=new Date();
                try {
                    dat = fecha.parse(sFecha);
                UsuarioCasos obj =new UsuarioCasos();
                obj.setNombre(nombre);
                obj.setApellidos(apellidos);
                Nacionalidad nacional=new Nacionalidad();
                nacional.setId(nacionalidad);
                obj.setNacionalidad(nacional);
                TipoDocumento documento = new TipoDocumento();
                documento.setId(documentos);
                obj.setTipoDocumento(documento);
                obj.setNumeroDocumento(dni);
                obj.setFechaNacimiento(dat);
                Distritos dist = new Distritos();
                dist.setId(distritos);
                obj.setDistritos(dist);
                obj.setTelefono(telefono);
                obj.setDireccionDomicilio(direccion);
                registrarUsuarioCasos(obj);
                } catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está segur(a) que está todo correcto?");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        Dialog dialog = builder.create();
        dialog.show();
    }



    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }

        latitud = (TextView) findViewById(R.id.idLatitud);
        longitud = (TextView) findViewById(R.id.idLongitud);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (ubicacion != null) {
            Log.d("Latitud", String.valueOf(loc.getLatitude()));
            Log.d("Longitud", String.valueOf(loc.getLongitude()));
        }
    }

    private void listaProviders() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> listProvider = ubicacion.getAllProviders();//listaProvider: size = 3

        String mejorProvider = ubicacion.getBestProvider(mejorCriterio(), false);
        System.out.println(mejorProvider);

        LocationProvider provider = ubicacion.getProvider(listProvider.get(0));
        System.out.println(provider.getAccuracy()); //provider: LocationProvider@4568
        System.out.println(provider.getPowerRequirement());
        System.out.println(provider.supportsAltitude());
    }

    private Criteria mejorCriterio() {
        Criteria req = new Criteria();
        req.setAccuracy(Criteria.ACCURACY_FINE);
        req.setAltitudeRequired(true);
        return req;
    }

    private boolean estadoGPS() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!ubicacion.isProviderEnabled(LocationManager.GPS_PROVIDER))
            Log.d("GPS", "NO ACTIVADO");
        else {
            Log.d("GPS", "ACTIVADO");
        }
        return true;
    }




   public void cargaDocumentos(){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<List<TipoDocumento>> call = postService.getTipoDocumentos();
        call.enqueue(new Callback<List<TipoDocumento>>() {
            @Override
            public void onResponse(Call<List<TipoDocumento>> call, Response<List<TipoDocumento>> response) {
                ArrayList<TipoDocumento> lista=new ArrayList<TipoDocumento>();
                if(response.isSuccessful()){
                    try {
                        final List<TipoDocumento> com = response.body();
                        for (int i = 0; i < com.size(); i++) {
                            TipoDocumento reg=new TipoDocumento();
                            reg.setNombreDocumento(com.get(i).getNombreDocumento());
                            lista.add(reg);
                        }
                        String[] result = TextUtils.join(",",lista).split(",");
                        Spinner combo_documentos=(Spinner) findViewById(R.id.spnTipoDocumentos);
                        combo_documentos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,result));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else
                {
                    Log.i("Base","El metodo ha fallado" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<TipoDocumento>> call, Throwable t) {

            }
        });

    }

    public void cargaDistritos(Long id){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        id = combo_provincias.getSelectedItemId();
        Call<Provincias> call = postService.getDistritos(id);
        call.enqueue(new Callback<Provincias>() {
            @Override
            public void onResponse(Call<Provincias> call, Response<Provincias> response) {
                ArrayList<Distritos> lista=new ArrayList<Distritos>();
                if(response.isSuccessful()){
                    try {
                        final Provincias com = response.body(); // Esto es json completo
                        for( Distritos item: com.getDistritos() ) {
                            Distritos reg = new Distritos();
                            reg.setId(item.getId());
                            reg.setNombreDistrito(item.getNombreDistrito());
                            lista.add(reg);
                        }

                        /*for (int i = 0; i < com.getProvincias().size(); i++) {
                            Provincias reg=new Provincias();
                            reg.setId(com.get);
                            reg.setNombreProvincia(com.getNombreProvincia());
                            lista.add(reg);
                        }*/
                        String[] result = TextUtils.join(",",lista).split(",");
                        Spinner combo_distritos=(Spinner) findViewById(R.id.spnDistritos);
                        combo_distritos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,result));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else
                {
                    Log.i("Base","El metodo ha fallado" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Provincias> call, Throwable t) {

            }
        });

    }

    public void cargaDepartamentos(){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<List<Departamentos>> call = postService.getDepartamentos();
        call.enqueue(new Callback<List<Departamentos>>() {
            @Override
            public void onResponse(Call<List<Departamentos>> call, Response<List<Departamentos>> response) {
                ArrayList<Departamentos> lista=new ArrayList<Departamentos>();
                if(response.isSuccessful()){
                    try {
                        final List<Departamentos> com = response.body();
                        for (int i = 0; i < com.size(); i++) {
                            Departamentos reg=new Departamentos();
                            //cargaProvincia(com.get(i).getId());
                            reg.setId(com.get(i).getId());
                            reg.setNombreDepartamento(com.get(i).getNombreDepartamento());
                            lista.add(reg);
                        }
                        String[] result = TextUtils.join(",",lista).split(",");
                        Spinner combo_departamentos=(Spinner) findViewById(R.id.spnDepartamentos);
                        combo_departamentos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,result));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else
                {
                    Log.i("Base","El metodo ha fallado" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<Departamentos>> call, Throwable t) {

            }
        });

    }

    public void cargaProvincia(Long id){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        id = combo_departamentos.getSelectedItemId();
        Call<Departamentos> call = postService.getProvincias(id);
        call.enqueue(new Callback<Departamentos>() {
            @Override
            public void onResponse(Call<Departamentos> call, Response<Departamentos> response) {
                ArrayList<Provincias> lista=new ArrayList<Provincias>();
                if(response.isSuccessful()){
                    try {
                        final Departamentos com = response.body(); // Esto es json completo
                        for( Provincias item: com.getProvincias() ) {
                            Provincias reg = new Provincias();
                            reg.setId(item.getId());
                            reg.setNombreProvincia(item.getNombreProvincia());
                            lista.add(reg);
                        }

                        /*for (int i = 0; i < com.getProvincias().size(); i++) {
                            Provincias reg=new Provincias();
                            reg.setId(com.get);
                            reg.setNombreProvincia(com.getNombreProvincia());
                            lista.add(reg);
                        }*/
                        String[] result = TextUtils.join(",",lista).split(",");
                        Spinner combo_provincias=(Spinner) findViewById(R.id.spnProvincias);
                        combo_provincias.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,result));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else
                {
                    Log.i("Base","El metodo ha fallado" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Departamentos> call, Throwable t) {

            }
        });

    }
    public void cargaNacionalidad(){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<List<Nacionalidad>> call = postService.getNacionalidades();
        call.enqueue(new Callback<List<Nacionalidad>>() {
            @Override
            public void onResponse(Call<List<Nacionalidad>> call, Response<List<Nacionalidad>> response) {
                ArrayList<Nacionalidad> lista=new ArrayList<Nacionalidad>();
                if(response.isSuccessful()){
                    try {
                        final List<Nacionalidad> com = response.body();
                        for (int i = 0; i < com.size(); i++) {
                            Nacionalidad reg=new Nacionalidad();
                            reg.setNombreNacionalidad(com.get(i).getNombreNacionalidad());
                            lista.add(reg);
                        }
                        String[] result = TextUtils.join(",",lista).split(",");
                        Spinner combo_nacionalidades=(Spinner) findViewById(R.id.spnNacionalidad);
                        combo_nacionalidades.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,result));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else
                {
                    Log.i("Base","El metodo ha fallado" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<Nacionalidad>> call, Throwable t) {

            }
        });

    }

    private void registrarUsuarioCasos(UsuarioCasos obj){
        postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<UsuarioCasos> call =postService.saveUsuariosCasos(obj);

        call.enqueue(new Callback<UsuarioCasos>() {
            @Override
            public void onResponse(Call<UsuarioCasos> call, Response<UsuarioCasos> response) {
                if (!response.isSuccessful()){
                    Log.i(TAG, "Post Subido al API" + response.body().toString());
                }else{
                    Log.i(TAG, "Algo salio mal" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UsuarioCasos> call, Throwable t) {
                    Log.i(TAG, "Improbable subir POST al API");
                    Toast.makeText(FichaPersonal.this, "UsuarioCasos Registrado (ver retorno)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarLocalizacion() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, new miLocalizacionListener());
    }

    private class miLocalizacionListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            String lat = "" + location.getLatitude();
            String lon = "" + location.getLongitude();

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> direccion1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                txtDireccion.setText(direccion1.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }

            latitud.setText(lat);
            longitud.setText(lon);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }


}
