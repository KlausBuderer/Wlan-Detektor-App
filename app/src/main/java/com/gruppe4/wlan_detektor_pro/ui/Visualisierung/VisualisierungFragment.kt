package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor_pro.databinding.FragmentVisualisierungBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MESSUNGLISTE_KONTEXT
import com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungListeAdapter
import kotlinx.coroutines.launch

/**
 * Visualisierung View
 * Auflistung aller gespeicherten Messungen
 * @author Klaus Buderer
 * @since 1.0.0
 */
class VisualisierungFragment : Fragment(), MessungListeAdapter.OnItemClickListener {

    private lateinit var visualisierungViewModel: VisualisierungViewModel
    private var _binding: FragmentVisualisierungBinding? = null

    private val binding get() = _binding!!
    private var messungsListe: List<TblMessung>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        visualisierungViewModel =
            ViewModelProvider(this).get(VisualisierungViewModel::class.java)

        _binding = FragmentVisualisierungBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch { visualisierungViewModel.getAlleMessungen() }

        visualisierungViewModel.messungsliste.observe(viewLifecycleOwner, Observer {
            messungsListe = it

            val adapter = MessungListeAdapter(it, this, requireActivity().application, MESSUNGLISTE_KONTEXT.Visualisierung.toString())
            binding?.rvMessungsliste?.adapter = adapter

            if (it.isNullOrEmpty()){
                binding.tvKeineMessungen.visibility = TextView.VISIBLE
            }
        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {

        val action = VisualisierungFragmentDirections.actionNavigationVisualisierungToVisualisierungGridFragment(
            messungsListe?.get(position)?.idmessung ?: -1
        )
        Navigation.findNavController(binding.root).navigate(action)
    }
}