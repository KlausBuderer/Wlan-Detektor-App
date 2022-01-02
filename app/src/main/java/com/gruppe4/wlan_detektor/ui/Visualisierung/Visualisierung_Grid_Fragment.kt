package com.gruppe4.wlan_detektor.ui.Visualisierung

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gruppe4.wlan_detektor.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor.ui.MesspunktListe.messpunktListe
import com.gruppe4.wlan_detektor.ui.MessungListe

class Visualisierung_Grid_Fragment : Fragment(), MesspunktVisuAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = Visualisierung_Grid_Fragment()
    }

    var _binding: MessungListeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VisualisierungGridViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessungListeFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root

        var messungen = MessungListe.messungListe
        val adapter = MesspunktVisuAdapter(messpunktListe, this)
        binding.rvmessungsliste.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        binding?.rvmessungsliste?.adapter = adapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisualisierungGridViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onItemClick(position: Int) {

    }

}