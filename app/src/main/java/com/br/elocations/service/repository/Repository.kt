package com.br.elocations.service.repository


import android.content.Context
import com.br.elocations.service.model.Establishment


//Estou usando o padrão Singleton, onde apenas uma instância do db vai ser criada no companior object

class Repository constructor(context : Context){



    //Acesso ao banco de dados
    private val mDataBase = EstablishmentDataBase.getDataBase(context).establishmentDAO()


    /**
     * Carrega um estabelecimento por id
     */
    suspend fun get(id : Int) : Establishment {
        return mDataBase.get(id)
    }

    /**
     * Carrega todos os estabelecimentos
     */
    suspend fun getAll() : List<Establishment> {
        return mDataBase.getAll()
    }

    /**
     * Salva estabelecimento
     */
    suspend fun save( establishment: Establishment) : Boolean{
        return mDataBase.save(establishment) > 0
    }


    /**
     * Atualiza estabelecimento
     */
    suspend fun update( establishment: Establishment) : Boolean{
        return mDataBase.update(establishment) > 0
    }

    /**
     * Deleta estabelecimento
     */
    suspend fun delete( establishment : Establishment) {
        return mDataBase.delete(establishment)
    }
}