package com.gruppe4.wlan_detektor.ui.Visualisierung

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.gruppe4.wlan_detektor.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.ui.MesspunktListe.messpunktListe
import com.gruppe4.wlan_detektor.ui.MessungListe
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktBearbeitenAdapter
import kotlinx.coroutines.launch

class Visualisierung_Grid_Fragment : Fragment(), MesspunktVisuAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = Visualisierung_Grid_Fragment()
    }

    var _binding: MessungListeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VisualisierungGridViewModel
    private var messungsId: Long = -1
    val args: Visualisierung_Grid_FragmentArgs by navArgs()
    var messungListe = listOf(TblMesspunkt())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessungListeFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root


        binding.rvMessungsliste.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
        }



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisualisierungGridViewModel::class.java)

        lifecycleScope.launch { viewModel.getMesspunkte(args.messungsId) }
        viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
            val adapter = MesspunktVisuAdapter(it, this, requireActivity().application)
            messungListe = it
            binding?.rvMessungsliste?.adapter = adapter
        })

    }

    override fun onItemClick(position: Int) {

        val action = Visualisierung_Grid_FragmentDirections.actionVisualisierungGridFragmentToVisuDetailFragment(
            messungListe[position].raumname,
            messungListe[position].gebaeude,
            messungListe[position].stockwerkID.toString(),
            messungListe[position].pegelmessung,
            messungListe[position].zusatzinformation,
            messungListe[position].erfassungsDatum,
            messungListe[position].erfassungsZeit,
            messungListe[position].bildPfad

        )

        Navigation.findNavController(binding.root).navigate(action)

    }

}