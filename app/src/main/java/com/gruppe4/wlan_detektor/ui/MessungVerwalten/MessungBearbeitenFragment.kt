package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MesspunktItemBinding
import com.gruppe4.wlan_detektor.databinding.MessungBearbeitenFragmentBinding
import com.gruppe4.wlan_detektor.databinding.NetzwerklisteFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.ui.MesspunktListe
import com.gruppe4.wlan_detektor.ui.Visualisierung.VisuDetailFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessungBearbeitenFragment : Fragment(), MesspunktBearbeitenAdapter.OnItemClickListener {

    var _binding: MessungBearbeitenFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var addButton: FloatingActionButton
    lateinit var messungsnamen: String
    var messungsId: Long = -1
    val args: MessungBearbeitenFragmentArgs by navArgs()
    var messpunktsListe: List<TblMesspunkt>? = null

    companion object {
        fun newInstance() = MessungBearbeitenFragment()
    }

    private lateinit var viewModel: MessungBearbeitenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MessungBearbeitenFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root

        addButton = binding.btnMesspunktHinzufuegen

        messungsnamen = args.messungsnamen

        var messpunkte = MesspunktListe.messpunktListe



        addButton.setOnClickListener{

            val action = MessungBearbeitenFragmentDirections.actionMessungBearbeitenFragmentToMesspunktErfassungsFragment(
                messungsnamen,
                messungsId,
                -1
            )

            Navigation.findNavController(binding.root).navigate(action)

        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungBearbeitenViewModel::class.java)

        lifecycleScope.launch(Dispatchers.Main){
            viewModel.getMessung(messungsnamen)
        }

        viewModel.messung.observe(viewLifecycleOwner, Observer {
            messungsId = it.idmessung

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getMesspunkte(it.idmessung)
            }
        })

        viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
            messpunktsListe = it
            val adapter = MesspunktBearbeitenAdapter(messpunktsListe, this, requireActivity().application)
            binding?.rvMesspunktliste?.adapter = adapter

        })




        viewModel.messung.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                binding.tvNamenMessung.text = it.name
                binding.tvSsid.text = it.ssid
            }else{
                binding.tvNamenMessung.text = "Kein Treffer"
            }
        })
    }

    override fun onItemClick(position: Int) {
        val action = messpunktsListe?.get(position)?.let {
            MessungBearbeitenFragmentDirections.actionMessungBearbeitenFragmentToMesspunktErfassungsFragment(
                messungsnamen,
                messungsId,
                it.idmesspunkt
            )
        }

        if (action != null) {
            Navigation.findNavController(binding.root).navigate(action)
        }

    }

}