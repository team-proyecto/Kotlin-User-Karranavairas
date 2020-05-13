package com.example.covid.servicios;

import com.example.covid.entidades.Departamentos;
import com.example.covid.entidades.TipoDocumento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProyectoService {

    @GET("/documentos")

    Call<List<TipoDocumento>> getTipoDocumentos();

    @GET("/departamentos")
    Call<List<Departamentos>> getDepartamentos();

}
