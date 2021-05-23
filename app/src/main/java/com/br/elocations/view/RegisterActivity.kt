package com.br.elocations.view


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.br.elocations.R
import com.br.elocations.databinding.ActivityRegisterBinding
import com.br.elocations.helper.Converters
import com.br.elocations.service.constants.EstablishmentConstants
import com.br.elocations.viewmodel.RegisterViewModel


class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var mViewModel : RegisterViewModel
    private lateinit var categorySpiner : Spinner
    private lateinit var stateSpiner : Spinner
    private var mEstablishmentId : Int = 0
    private var photoInString = ArrayList<String>()
    private var mListCategory : List<String> = listOf()
    private var mListState : List<String> = listOf()
    private val convert = Converters()

    private lateinit var binding : ActivityRegisterBinding

    companion object{
        var currentState = ""
        var currentCategory = ""
        var spinnerStatePosition = 0
        var spinnerCategoryPosition = 0
        val CAMERA_REQUEST_CODE = 200
        val PICK_IMAGE = 100

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initToolbar()
        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        observer()
        loadData()
        initializeUiElements()

    }




    private fun deleteImage(image : ImageView, position : Int){

        image.setOnLongClickListener {
            AlertDialog.Builder(it.context)
                .setTitle(R.string.remocao_imagem)
                .setMessage(R.string.deseja_remover_imagem)
                .setPositiveButton(R.string.remover) { _, _ ->
                    mViewModel.establishment.observe(this, Observer { establishment ->

                        establishment.photos.removeAt(position)
                        image.setImageResource(R.drawable.padrao)

                    })

                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }

    }



    //Recupera os dados do fragment EstablishmentFragment com o bundle através da viewModel
    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null) {
            mEstablishmentId = bundle.getInt(EstablishmentConstants.ESTABLISHMENTID)
            mViewModel.load(mEstablishmentId)

        }
    }



    private fun setListeners(){
        val buttonSave = binding.buttonSave
        buttonSave.setOnClickListener(this)

    }





     override fun onClick(v: View) {
        val id = v.id
        if (id == binding.buttonSave.id) {


            mViewModel.save(mEstablishmentId,
                    binding.editTextTitle.text.toString(), binding.editTextDescription.text.toString(),
                    binding.editTextAddress.text.toString(), binding.editTextCity.text.toString(), currentState,
                    currentCategory, photoInString

                )

        }

        finish()
    }





    private fun observer(){

        mViewModel.saveEstablishment.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucess", Toast.LENGTH_LONG).show()
            } else Toast.makeText(applicationContext, "Falha", Toast.LENGTH_LONG).show()
        })

        mViewModel.establishment.observe(this, Observer {

            if (it.photos.size > 0) binding.imageView0.setImageBitmap(convert.stringToBitmap(it.photos[0]))
            if (it.photos.size > 1) binding.imageView1.setImageBitmap(convert.stringToBitmap(it.photos[1]))
            if (it.photos.size > 2) binding.imageView2.setImageBitmap(convert.stringToBitmap(it.photos[2]))

            photoInString = it.photos

            binding.editTextTitle.setText(it.title)
            binding.editTextDescription.setText(it.description)
            binding.editTextAddress.setText(it.street)
            binding.editTextCity.setText(it.city)

            //pegar o spiner correspodente a posição em qual se encontra.
            binding.spinnerCategory.setSelection(getPositionSpinner(it.category, mListCategory))
            binding.spinnerState.setSelection(getPositionSpinner(it.state, mListState))





        })
    }




    private fun selectImage(image: ImageView){
        image.setOnClickListener {
            AlertDialog.Builder(it.context)
                    .setTitle(R.string.escolha_imagem_estabelecimento)
                    .setItems(R.array.image_array, DialogInterface.OnClickListener { dialog, which ->
                        if (which == 0) {
                            imageFromCamera()
                        } else if (which == 1) {
                            imageFromGallery()
                        } else {
                            dialog.dismiss()
                        }
                    })
                    .show()

            true
        }

    }






    //ADD IMAGE FROM GALLERY
    private fun imageFromGallery(){

            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also { galerryIntent ->
                galerryIntent.resolveActivity(packageManager)?.also {

                    startActivityForResult(galerryIntent, PICK_IMAGE)
                }
            }

    }



    //ADD IMAGE FROM CAMERA
    private fun imageFromCamera(){

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {

                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){

            CAMERA_REQUEST_CODE  ->

                if ( resultCode == RESULT_OK ) {
                val imageBitmap = data?.extras?.get("data") as Bitmap

                if (photoInString.size < 1) {
                    binding.imageView0.setImageBitmap(imageBitmap)
                }else if (photoInString.size < 2){
                    binding.imageView1.setImageBitmap(imageBitmap)
                }else if (photoInString.size < 3){
                    binding.imageView2.setImageBitmap(imageBitmap)
                }

                photoInString.add(convert.bitmapToString(imageBitmap))
            }

            PICK_IMAGE ->

                if (resultCode == RESULT_OK){
                var imageUri = data?.data
                val imageBitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

                if (photoInString.size < 1) {
                    binding.imageView0.setImageBitmap(imageBitmap)
                } else if (photoInString.size < 2){
                    binding.imageView1.setImageBitmap(imageBitmap)
                }else if (photoInString.size < 3){
                    binding.imageView2.setImageBitmap(imageBitmap)
                }
                    photoInString.add(convert.bitmapToString(imageBitmap))
            }
            else -> throw Exception(" No option on how to select an image ")
        }


    }

    private fun initializeUiElements() {
        createSpinnerState()
        createSpinnerCategory()


        setListeners()

        selectImage(binding.imageView0)
        selectImage(binding.imageView1)
        selectImage(binding.imageView2)

        deleteImage(binding.imageView0, 0)
        deleteImage(binding.imageView1, 1)
        deleteImage(binding.imageView2, 2)
    }



    private fun createSpinnerState() {

        stateSpiner = binding.spinnerState
        mListState = listOf( "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará",
                "Distrito Federal", "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso",
                "Mato Grosso do Sul", "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco",
                "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia",
                "Roraíma", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mListState)
        stateSpiner.adapter = adapter
        stateSpiner.onItemSelectedListener = this
        currentState = stateSpiner.selectedItem.toString()

    }

    private fun createSpinnerCategory(){
        categorySpiner = binding.spinnerCategory

        mListCategory = listOf("Lanchonete", "Restaurante", "Ponto de Ônibus", "Escola", "Posto de Gasolina",
                "Supermercado", "Outro")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mListCategory)
        categorySpiner.adapter = adapter
        categorySpiner.onItemSelectedListener = this
        currentCategory = categorySpiner.selectedItem.toString()

    }

    private fun getPositionSpinner(str : String, list : List<String>) : Int{
        for (i in list.indices){
            if (str == list[i])
                return i
        }
        return 0
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        currentState = stateSpiner.selectedItem.toString()
        spinnerStatePosition = stateSpiner.selectedItemPosition
        currentCategory = categorySpiner.selectedItem.toString()
        spinnerCategoryPosition = stateSpiner.selectedItemPosition

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "",Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}