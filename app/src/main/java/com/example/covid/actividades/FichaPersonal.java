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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.entidades.TipoDocumento;
import com.example.covid.servicios.ProyectoService;
import com.example.covid.util.ConnectionRest;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaPersonal extends AppCompatActivity implements View.OnClickListener {
    Spinner combo_tipoDocumento, combo_ciudades, combo_distritos,combo_departamentos;

    Button btnRegistrarse;
    ArrayAdapter arrayAdapter;
    ArrayList<String> opcionesSpnView = new ArrayList<String>();
    List<TipoDocumento> documentos = new ArrayList<TipoDocumento>();
    private LocationManager ubicacion;
    TextView latitud, longitud;
    EditText txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_personal);

        combo_tipoDocumento = (Spinner)findViewById(R.id.spnTipoDocumentos);
        combo_ciudades = (Spinner)findViewById(R.id.spnCiudades);
        combo_distritos = (Spinner)findViewById(R.id.spnDistritos);
        combo_departamentos = (Spinner)findViewById(R.id.spnDepartamentos);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,opcionesSpnView);
        combo_tipoDocumento.setAdapter(arrayAdapter);

        localizacion();
        //listaProviders();
        //mejorCriterio();
        //estadoGPS();
        registrarLocalizacion();
        cargaDocumentos();

        btnRegistrarse = findViewById(R.id.btnRegistarse);

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
                Log.i("cargaDocumentos" , "-----> ");
                Log.i("cargaDocumentos" , "-----> " + response.body());

                documentos = response.body();
                //Se agrega de usuarios al listview
                for(TipoDocumento x : documentos) {
                    opcionesSpnView.add(x.getId() + "->" + x.getNombreDocumento());
                }
                //Se actualiza el listview
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<TipoDocumento>> call, Throwable t) {
                Log.i("MuestraPost" , t.getMessage());
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
