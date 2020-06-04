package com.example.covid.servicios;

import com.example.covid.entidades.Clientes;
import com.example.covid.entidades.Departamentos;
import com.example.covid.entidades.Distritos;
import com.example.covid.entidades.TipoDocumento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProyectoService {

    @GET("usuarioscasos/documentos")

    Call<List<TipoDocumento>> getTipoDocumentos();

    @GET("usuarioscasos/departamentos")
    Call<List<Departamentos>> getDepartamentos();

    @GET("usuarioscasos/distrito")
    Call<List<Distritos>> getDistritos();

    @POST("usuarioscasos")
    Call<Clientes> saveCliente(@Body Clientes obj);

}
