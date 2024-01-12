package xyz.edwinpalacios.po_po.model.services

import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.POST
import xyz.edwinpalacios.po_po.model.MessageNotification


interface APIService {
    @POST("/send_message")
    fun sendNotification(@Body requestBody: MessageNotification): Call<String>?
}