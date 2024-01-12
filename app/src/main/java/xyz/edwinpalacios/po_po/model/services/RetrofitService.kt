package xyz.edwinpalacios.po_po.model.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {
    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://edwiinrtz.pythonanywhere.com")
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
    val apiService: APIService = retrofit.create(APIService::class.java)

}