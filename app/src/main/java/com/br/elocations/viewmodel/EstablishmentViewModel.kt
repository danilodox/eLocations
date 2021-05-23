package com.br.elocations.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.br.elocations.R
import com.br.elocations.service.model.Establishment
import com.br.elocations.service.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentViewModel(application: Application) : AndroidViewModel(application) {



    private val mRepository = Repository(application.applicationContext)

    private val mEstablishmentList = MutableLiveData<List<Establishment>>()
    val establishmentList : LiveData<List<Establishment>> = mEstablishmentList

     fun load(){
        CoroutineScope(Dispatchers.Main).launch {

                mEstablishmentList.value = mRepository.getAll()
        }
    }

     fun delete( id : Int){
         CoroutineScope(Dispatchers.Main).launch {
            val establishment = mRepository.get( id )
            mRepository.delete(establishment)
        }
    }


}