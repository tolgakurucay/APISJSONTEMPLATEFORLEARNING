package com.tolgakurucay.apijsontemp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.tolgakurucay.apijsontemp.R
import com.tolgakurucay.apijsontemp.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*


private var colors:Array<String> = arrayOf("#ee82ee","#96ddff","#afc065","#106976","#ff2812")
class CryptoAdapter(val cryptoList:ArrayList<CryptoModel>,private val listener:Listener) : RecyclerView.Adapter<CryptoAdapter.RowHolder>() {
    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }


    class RowHolder(view:View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel: CryptoModel,colors:Array<String>,position: Int,listener:Listener){
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position%5]))
            itemView.TextViewCurrency.text=cryptoModel.currency
            itemView.TextViewPrice.text=cryptoModel.price

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        //hangi item ne verisi gösterecek
        holder.bind(cryptoList[position], colors,position,listener)

    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}