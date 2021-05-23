package com.br.elocations.service.repository

import androidx.room.*
import com.br.elocations.service.model.Establishment

@Dao
interface EstablishmentDAO {

    @Insert
    suspend fun save (establishment: Establishment) : Long

    @Update
    suspend fun update (establishment: Establishment) : Int

    @Delete
    suspend fun delete (establishment: Establishment)

    @Query("SELECT * FROM Establishment WHERE id = :id")
    suspend fun get ( id : Int ) : Establishment

    @Query("SELECT * FROM Establishment")
    suspend fun getAll () : List<Establishment>
}