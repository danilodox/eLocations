package com.br.elocations.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.br.elocations.helper.Converters
import com.br.elocations.service.model.Establishment

@Database(entities = [Establishment::class], version = 1)
@TypeConverters(Converters::class)
abstract class EstablishmentDataBase : RoomDatabase() {

    abstract fun establishmentDAO() : EstablishmentDAO


    companion object{
        private lateinit var INSTANCE: EstablishmentDataBase
        fun getDataBase(context: Context): EstablishmentDataBase {

            if  (!::INSTANCE.isInitialized) {
                synchronized(EstablishmentDataBase::class) {

                    INSTANCE = Room.databaseBuilder(context, EstablishmentDataBase::class.java, "establishmentDB")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}