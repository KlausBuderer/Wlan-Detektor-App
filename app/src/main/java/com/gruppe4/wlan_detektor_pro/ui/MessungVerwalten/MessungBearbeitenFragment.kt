package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MessungBearbeitenFragmentBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Messungbearbeitungs-View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungBearbeitenFragment : Fragment(), MesspunktBearbeitenAdapter.OnItemClickListener {

    var _binding: MessungBearbeitenFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var addButton: FloatingActionButton
    lateinit var messungsnamen: String
    var messungsId: Long = -1
    val args: MessungBearbeitenFragmentArgs by navArgs()
    var messpunktsListe: List<TblMesspunkt>? = null
    lateinit var namenAendernButton: ImageView

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
        namenAendernButton = binding.imageView
        messungsnamen = args.messungsnamen

        addButton.setOnClickListener {
            val action =
                MessungBearbeitenFragmentDirections.actionMessungBearbeitenFragmentToMesspunktErfassungsFragment(
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

        //Hole Messung aus Datenbank
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getMessung(messungsnamen)
        }

        viewModel.messung.observe(viewLifecycleOwner, Observer {
            messungsId = it.idmessung

            //Hole Messpunkt aus Datenbank
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getMesspunkte(it.idmessung)
            }
        })

        viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
            messpunktsListe = it

            //Erstelle Liste von Messpunkten
            val adapter =
                MesspunktBearbeitenAdapter(messpunktsListe, this, requireActivity().application)
            binding?.rvMesspunktliste?.adapter = adapter

            var count = it.size

            if (count > 3) {
                binding.tvMesspunktHinzu.visibility = TextView.INVISIBLE
            } else {
                binding.tvMesspunktHinzu.visibility = TextView.VISIBLE
            }
        })

        namenAendernButton.setOnClickListener {
            try {
                val action =
                    MessungBearbeitenFragmentDirections.actionMessungBearbeitenFragmentToMessungsnamenAendern(
                        messungsnamen
                    )
                if (action != null && findNavController().currentDestination?.id?.equals(R.id.messungBearbeitenFragment) == true) {
                    Navigation.findNavController(binding.root).navigate(action)
                }
            } catch (namenAendernException: IllegalStateException) {
                Log.e("Messungsnamen aendern", "Navigition Namenaendern nicht gefunden")
            }
        }

        viewModel.messung.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvNamenMessung.text = it.name
                binding.tvSsid.text = it.ssid
            } else {
                binding.tvNamenMessung.text = "Etwas schief gelaufen!"
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

    override fun onResume() {
        super.onResume()
    }
}