package com.br.elocations.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class Converters {



    fun bitmapToString (bitmap: Bitmap) : String{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()

        return Base64.encodeToString(b, Base64.DEFAULT)

    }

    fun stringToBitmap (encoded : String?) : Bitmap{
        val encodeByte = Base64.decode(encoded, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

    }


    @TypeConverter
    fun jsonToArray (value : String) : ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun arrayToJson (list : ArrayList<String>) : String{
        return Gson().toJson(list)
    }

}