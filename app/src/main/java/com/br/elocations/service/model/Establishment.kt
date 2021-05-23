package com.br.elocations.service.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Establishment")
class Establishment {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var description : String = ""
    var street : String = ""
    var city : String = ""
    var state : String = ""
    var title : String = ""
    var category : String = ""
    var photos  = ArrayList<String>()


}
