package com.br.elocations.view.viewholder

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.elocations.R
import com.br.elocations.helper.Converters
import com.br.elocations.service.model.Establishment
import com.br.elocations.view.listener.EstablishmentListener

class EstablishmentViewHolder(itemView : View, private val listener : EstablishmentListener) : RecyclerView.ViewHolder(itemView) {


    private val convert = Converters()

    fun bind (establishment: Establishment) {

        val imageEstablishment = itemView.findViewById<ImageView>(R.id.image_establishmentPerfil)
        if(establishment.photos.size > 0) imageEstablishment.setImageBitmap( convert.stringToBitmap(establishment.photos[0]) )

        val textName = itemView.findViewById<TextView>(R.id.text_name)
        textName.text = establishment.title

        val textStreet = itemView.findViewById<TextView>(R.id.text_street)
        textStreet.text = establishment.state

        val textCategory = itemView.findViewById<TextView>(R.id.text_category)
        textCategory.text = establishment.category

        val buttonDelete = itemView.findViewById<ImageButton>(R.id.button_delete)

        imageEstablishment.setOnClickListener {
            listener.onClick(establishment.id)
        }

        buttonDelete.setOnClickListener {
            listener.onClick(establishment.id)
        }


        buttonDelete.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.remocao_estabelecimento)
                    .setMessage(R.string.deseja_remover)
                    .setPositiveButton(R.string.remover) { dialog, which ->

                        listener.onDelete(establishment.id)
                    }
                    .setNeutralButton(R.string.cancelar, null)
                    .show()
            true
        }

    }
}