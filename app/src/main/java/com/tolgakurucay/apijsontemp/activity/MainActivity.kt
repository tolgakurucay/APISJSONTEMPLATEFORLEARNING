package com.tolgakurucay.apijsontemp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tolgakurucay.apijsontemp.adapter.CryptoAdapter


import com.tolgakurucay.apijsontemp.databinding.ActivityMainBinding
import com.tolgakurucay.apijsontemp.model.CryptoModel
import com.tolgakurucay.apijsontemp.service.CryptoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL="https://api.nomics.com/v1/"
private var cryptoModels:ArrayList<CryptoModel>?=null
private lateinit var binding:ActivityMainBinding

private  var adapter:CryptoAdapter?=null

//Disposable kullan at
private var compositeDisposable : CompositeDisposable?=null

class MainActivity : AppCompatActivity() ,CryptoAdapter.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        compositeDisposable= CompositeDisposable()
        //recyclerView
        val layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        loadData()



        //63c8e27cc9454fc031cb0fb37fb8f227a3372ff0
        //https://api.nomics.com/v1/
        //"https://api.nomics.com/v1/prices?key=63c8e27cc9454fc031cb0fb37fb8f227a3372ff0"
    }

    private fun loadData()
    {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoApi::class.java)


        compositeDisposable?.add(retrofit.getData()
                                .subscribeOn(Schedulers.io())//veriye kayıt olma işini arka planda yapıyoruz
                                .observeOn(AndroidSchedulers.mainThread())//veriyi mainde işlememizi sağlıyor
                                .subscribe(this::handleResponse) )


        /*
        val service=retrofit.create(CryptoApi::class.java)

        val call=service.getData()
        call.enqueue(object :Callback<List<CryptoModel>>{


            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {

                if(response.isSuccessful)
                {

                    response.body()?.let{
                        cryptoModels= ArrayList(it)

                        cryptoModels?.let {
                            adapter= CryptoAdapter(it,this@MainActivity)
                            recyclerView.adapter= adapter
                            /*for (i:CryptoModel in cryptoModels!!){
                                println(i.currency)
                                println(i.price)
                            }*/
                        }

                    }
                }
                else
                {
                    println("Cevap dönmedi")
                }


            }




            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }


        })*/






        //Call yerine rxJava ile çalışalım

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }
    private fun handleResponse(cryptoList:List<CryptoModel>){
        cryptoModels= ArrayList(cryptoList)

        cryptoModels?.let {
            //null değilse
            adapter= CryptoAdapter(it,this@MainActivity)
            recyclerView.adapter= adapter

            }

    }

    override fun onDestroy() {

        super.onDestroy()
        compositeDisposable?.clear()
    }
}