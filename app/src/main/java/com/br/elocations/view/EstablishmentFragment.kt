package com.br.elocations.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.elocations.R
import com.br.elocations.databinding.FragmentEstablishmentBinding
import com.br.elocations.service.constants.EstablishmentConstants
import com.br.elocations.view.adapter.EstablishmentAdapter
import com.br.elocations.view.listener.EstablishmentListener
import com.br.elocations.viewmodel.EstablishmentViewModel


class EstablishmentFragment : Fragment() {

    private lateinit var mViewModel: EstablishmentViewModel
    private lateinit var  mAdapter : EstablishmentAdapter
    private lateinit var mListener : EstablishmentListener
    private lateinit var binding : FragmentEstablishmentBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEstablishmentBinding.inflate(inflater,  container, false)

        mViewModel = ViewModelProvider(this).get(EstablishmentViewModel::class.java)

        mAdapter = EstablishmentAdapter()

        initRecyclerView()


        mListener = object : EstablishmentListener{
            override fun onClick(id: Int) {
                val intent = Intent(context, RegisterActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(EstablishmentConstants.ESTABLISHMENTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load()
            }
        }

        mAdapter.attachListener(mListener)

        initObserver()
        initSwipe()

        return binding.root

    }

    private fun initRecyclerView() {
        binding.recyclerAllEstablishment.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load()
    }


    private fun initSwipe() {
        binding.swiperEstablishment.setColorSchemeResources( R.color.verde2 )
        binding.swiperEstablishment.setOnRefreshListener {

            mViewModel.load()
            binding.swiperEstablishment.isRefreshing = false

        }
    }



    private fun initObserver(){
        mViewModel.establishmentList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateEstablishment(it)
        })
    }


}