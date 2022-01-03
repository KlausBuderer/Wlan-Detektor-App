package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentVisualisierungBinding
import com.gruppe4.wlan_detektor.ui.MessungListe
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MessungListeAdapter

class VisualisierungFragment : Fragment(), MessungListeAdapter.OnItemClickListener {

    private lateinit var visualisierungViewModel: VisualisierungViewModel
    private var _binding: FragmentVisualisierungBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        visualisierungViewModel =
            ViewModelProvider(this).get(VisualisierungViewModel::class.java)

        _binding = FragmentVisualisierungBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var messungen = MessungListe.messungListe
        val adapter = MessungListeAdapter(messungen, this)
        binding?.rvMessungsliste?.adapter = adapter



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {

    Navigation.findNavController(binding.root).navigate(
        R.id.action_navigation_Visualisierung_to_visualisierung_Grid_Fragment
    )

    }
}