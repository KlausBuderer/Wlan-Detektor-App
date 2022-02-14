package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.VisualisierungGridFragmentBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class Visualisierung_Grid_Fragment : Fragment(), MesspunktVisuAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = Visualisierung_Grid_Fragment()
    }

    var _binding: VisualisierungGridFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VisualisierungGridViewModel
    private var messungsId: Long = -1
    val args: Visualisierung_Grid_FragmentArgs by navArgs()
    var messungListe = listOf(TblMesspunkt())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = VisualisierungGridFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root


        binding.rvMesspunktVisu.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisualisierungGridViewModel::class.java)

        lifecycleScope.launch(Dispatchers.Main){
            viewModel.getMessung(args.messungsId)
        }

        viewModel.messung.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvMessungsname.text = it.name
            }
        })

        lifecycleScope.launch { viewModel.getMesspunkte(args.messungsId) }
        viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
            val adapter = GebauedeVisuAdapter(it, this, requireActivity().application)
            messungListe = it
            binding?.rvMesspunktVisu?.adapter = adapter
            if (it.isEmpty()){
                binding.tvKeineMesspunkte.visibility = TextView.VISIBLE
            }
        })
    }

    override fun onItemClick(messpunktId: Long) {

        var messpunkt: TblMesspunkt? = null

        messungListe.forEach lit@{
            if (it.idmesspunkt == messpunktId)  {
                messpunkt = it
                return@lit
            }
        }
    try {
        val action = messpunkt?.pegelmessung?.let {
            Visualisierung_Grid_FragmentDirections.actionVisualisierungGridFragmentToVisuDetailFragment(
                messpunkt!!.raumname,
                messpunkt!!.gebaeude,
                activity?.resources!!.getStringArray(R.array.stockwerk_array)[messpunkt!!.stockwerkID],
                it,
                messpunkt!!.zusatzinformation,
                messpunkt!!.erfassungsDatum,
                messpunkt!!.erfassungsZeit,
                messpunkt!!.bildPfad,
                messpunkt!!.aenderungsDatum,
                messpunkt!!.aenderungsZeit

            )
        }

        if (action != null) {
            Navigation.findNavController(binding.root).navigate(action)
        }


    }catch (e: NullPointerException){
        Log.e("Messpunktinformationen an Detailsicht", "Messpunkt ist Null")
    }

}
}