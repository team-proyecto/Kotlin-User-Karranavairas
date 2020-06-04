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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.entidades.Departamentos;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.Provincias;
import com.example.covid.servicios.ProyectoService;
import com.example.covid.util.ConnectionRest;
import com.loopj.android.http.*;

import com.example.covid.R;
import com.example.covid.entidades.TipoDocumento;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;





public class FichaPersonal extends AppCompatActivity implements View.OnClickListener {
    Spinner combo_tipoDocumento, combo_ciudades, combo_distritos,combo_departamentos;
    private AsyncHttpClient cliente;

    Button btnRegistrarse;
    ArrayAdapter arrayAdapter;
    ArrayList<String> opcionesSpnView = new ArrayList<String>();
    List<TipoDocumento> documentos = new ArrayList<TipoDocumento>();
    private LocationManager ubicacion;
    TextView latitud, longitud;
    EditText txtID,txtNombres, txtApellidos,txtNacionalidad,txtDNI,txtNacimiento,txtTelefono ,txtDireccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_personal);

        cliente = new AsyncHttpClient();
        combo_tipoDocumento = (Spinner)findViewById(R.id.spnTipoDocumentos);
        combo_ciudades = (Spinner)findViewById(R.id.spnCiudades);
        combo_distritos = (Spinner)findViewById(R.id.spnDistritos);
        combo_departamentos = (Spinner)findViewById(R.id.spnDepartamentos);
        txtNombres = (EditText)findViewById(R.id.txtNombres);
        txtApellidos = (EditText)findViewById(R.id.txtApellidos);
        txtNacionalidad = (EditText)findViewById(R.id.txtNacionalidad);
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
        cargaDistritos();
        cargaDepartamentos();

        btnRegistrarse = findViewById(R.id.btnRegistarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://localhost:8080/api/usuarioscasos");
            }
        });

    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros = new HashMap<String, String>();
                TipoDocumento documento = new TipoDocumento();
                Distritos distritos = new Distritos();
                Provincias provincias = new Provincias();
                Departamentos departamentos = new Departamentos();
                parametros.put("idCliente", txtID.getText().toString());
                parametros.put("nombres", txtNombres.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString());
                parametros.put("nacionalidad", txtNacionalidad.getText().toString());
                parametros.put("tipoDocumento", documento.getNombreDocumento());
                parametros.put("dni", txtDNI.getText().toString());
                parametros.put("nombreDistrito", distritos.getNombreDistrito());
                parametros.put("nombreProvincia", provincias.getNombreProvincia());
                parametros.put("nombreDepartamento", departamentos.getNombreDepartamento());
                parametros.put("txtTelefono", txtTelefono.getText().toString());
                parametros.put("txtDireccion", txtDireccion.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
            //latitud.setText(String.valueOf(loc.getLatitude()));
            //longitud.setText(String.valueOf(loc.getLongitude()));
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

    public void cargaDistritos(){
        //Se obtiene la solicitud REST
        ProyectoService postService = ConnectionRest.getConnection().create(ProyectoService.class);
        Call<List<Distritos>> call = postService.getDistritos();
        call.enqueue(new Callback<List<Distritos>>() {
            @Override
            public void onResponse(Call<List<Distritos>> call, Response<List<Distritos>> response) {
                ArrayList<Distritos> lista=new ArrayList<Distritos>();
                if(response.isSuccessful()){
                    try {
                        final List<Distritos> com = response.body();
                        for (int i = 0; i < com.size(); i++) {
                            Distritos reg=new Distritos();
                            reg.setNombreDistrito(com.get(i).getNombreDistrito());
                            lista.add(reg);
                        }
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
            public void onFailure(Call<List<Distritos>> call, Throwable t) {

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
