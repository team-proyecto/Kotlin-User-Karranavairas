package com.example.covid.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

public class SituacionEconomica extends AppCompatActivity{


    ImageView   imgID;
    CheckBox chkBeneficiario;
    Button  btnTomarFoto,btnSeleccionarImagen;
    TextView txtRecibo;
    EditText txtMonto;
    Uri imagenUri;

    int TOMAR_FOTO=100;
    int SELEC_IMAGEN=200;
    String CARPETA_RAIZ = "MisFotosApp";
    String CARPETA_IMAGENES = "imagenes";
    String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_economica);

        imgID = findViewById(R.id.imgId);
        chkBeneficiario = findViewById(R.id.chkBeneficiario);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        txtRecibo = findViewById(R.id.txtRecibo);
        txtMonto = findViewById(R.id.txtMonto);

        /*chkBeneficiario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });*/

        if(ContextCompat.checkSelfPermission(SituacionEconomica.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SituacionEconomica.this,
            new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });
    }

    public void tomarFoto(){
        String nombreImagen="";
        File fileimagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileimagen.exists();
        if(isCreada == false){
            isCreada = fileimagen.mkdirs();
        }
        if(isCreada == true){
            nombreImagen = (System.currentTimeMillis()/1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities = this.getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, TOMAR_FOTO);
    }

    public void seleccionarImagen(){
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, SELEC_IMAGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELEC_IMAGEN) {
            imagenUri = data.getData();
            imgID.setImageURI(imagenUri);
        } else if (resultCode == RESULT_OK && requestCode == TOMAR_FOTO) {
            MediaScannerConnection.scanFile(SituacionEconomica.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            });

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imgID.setImageBitmap(bitmap);
        }
      }
}
