package com.br.elocations.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.br.elocations.service.model.Establishment
import com.br.elocations.service.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterViewModel(application : Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mRepository : Repository = Repository(mContext)

    private var mSaveEstablishment = MutableLiveData<Boolean>()
    val saveEstablishment : LiveData<Boolean> = mSaveEstablishment

    private var mEstablishment = MutableLiveData<Establishment>()
    val establishment : LiveData<Establishment> = mEstablishment



     fun save(id : Int ,title : String, desc : String, street : String, city : String, state : String, cat : String, photos : ArrayList<String>){
        CoroutineScope(Dispatchers.Main).launch {

            val establishment = Establishment()

            //viewModelScope.launch {
                establishment.id = id
                establishment.title = title
                establishment.description = desc
                establishment.street = street
                establishment.city = city
                establishment.state = state
                establishment.category = cat
                establishment.photos = photos

          //  }
            if (id == 0 ){
                mSaveEstablishment.value = mRepository.save(establishment)

            }else mSaveEstablishment.value = mRepository.update(establishment)
        }

    /*fun save(id : Int ,title : String, desc : String, street : String, city : String, state : String, cat : String, photos : ArrayList<String>) {
        val establishment = Establishment().apply {
            this.id = id
            this.title = title
            this.description = desc
            this.street = street
            this.city = city
            this.state = state
            this.category = cat
            this.photos = photos


        }


        if (id == 0 ){
            mSaveEstablishment.value = mRepository.save(establishment)

        }else mSaveEstablishment.value = mRepository.update(establishment)

    }*/







        /*
        val establishment = Establishment().apply {
            this.id = id
            this.title = title
            this.description = desc
            this.street = street
            this.city = city
            this.state = state
            this.category = cat
            this.photos = photos


        }


        if (id == 0 ){
            mSaveEstablishment.value = mRepository.save(establishment)

        }else mSaveEstablishment.value = mRepository.update(establishment)
         */
    }

    fun load(id : Int) {

        CoroutineScope(Dispatchers.Main).launch {
            mEstablishment.value = mRepository.get(id)

        }
    }

}