package com.br.elocations.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.elocations.R
import com.br.elocations.helper.Converters
import com.br.elocations.service.model.Establishment
import com.br.elocations.view.listener.EstablishmentListener
import com.br.elocations.view.viewholder.EstablishmentViewHolder

class EstablishmentAdapter : RecyclerView.Adapter<EstablishmentViewHolder>() {

    private var mEstablishmentList : List<Establishment> = arrayListOf()
    private lateinit var mListener : EstablishmentListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.establishment_item, parent, false)
        return EstablishmentViewHolder( item, mListener )
    }

    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        holder.bind(mEstablishmentList[position])
    }

    override fun getItemCount(): Int {
        return mEstablishmentList.count()
    }

    fun updateEstablishment(list : List<Establishment>){
        mEstablishmentList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener : EstablishmentListener){
        mListener = listener
    }

}