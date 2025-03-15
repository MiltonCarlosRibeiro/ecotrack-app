package br.com.fiap.ecotrack_app.service

import br.com.fiap.ecotrack_app.service.alimento.AlimentoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val URL = "https://caloriasporalimentoapi.herokuapp.com/"

    private val retrofitFactory: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAlimentoService(): AlimentoService {
        return retrofitFactory.create(AlimentoService::class.java)
    }
}