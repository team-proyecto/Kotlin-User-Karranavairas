package com.example.covid.servicios;

import com.example.covid.entidades.Nacionalidad;
import com.example.covid.entidades.Provincias;
import com.example.covid.entidades.UsuarioCasos;
import com.example.covid.entidades.Departamentos;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.TipoDocumento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProyectoService {

    @GET("usuarioscasos/documentos")

    Call<List<TipoDocumento>> getTipoDocumentos();

    @GET("usuarioscasos/departamentos")
    Call<List<Departamentos>> getDepartamentos();

    @GET("usuarioscasos/departamento/{id}")
    Call<Departamentos> getProvincias(@Path(value="id") Long id);

    @GET("usuarioscasos/provincias/{id}")
    Call<Provincias> getDistritos(@Path(value="id") Long id);

    @GET("usuarioscasos/nacionalidad")
    Call<List<Nacionalidad>> getNacionalidades();

    @POST("usuarioscasos")
    Call<UsuarioCasos> saveUsuariosCasos(@Body UsuarioCasos obj);

}
