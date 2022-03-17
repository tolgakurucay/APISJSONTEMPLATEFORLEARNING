package com.tolgakurucay.apijsontemp.service

import com.tolgakurucay.apijsontemp.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {


    @GET("prices?key=63c8e27cc9454fc031cb0fb37fb8f227a3372ff0")
    //fun getData() : Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>
}