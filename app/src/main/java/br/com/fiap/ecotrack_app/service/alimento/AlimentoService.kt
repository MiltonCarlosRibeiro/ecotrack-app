package br.com.fiap.ecotrack_app.service.alimento

import br.com.fiap.ecotrack_app.model.alimento.Alimento
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlimentoService {
    @GET("api/calorias/")
    fun getAlimentos(@Query("descricao") descricao: String): Call<List<Alimento>>
}