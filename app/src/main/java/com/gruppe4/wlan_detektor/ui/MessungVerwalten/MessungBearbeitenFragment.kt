package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MesspunktItemBinding
import com.gruppe4.wlan_detektor.databinding.MessungBearbeitenFragmentBinding
import com.gruppe4.wlan_detektor.databinding.NetzwerklisteFragmentBinding
import com.gruppe4.wlan_detektor.ui.MesspunktListe

class MessungBearbeitenFragment : Fragment(), MesspunktBearbeitenAdapter.OnItemClickListener {

    var _binding: MessungBearbeitenFragmentBinding? = null
    private val binding get() = _binding!!

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

        var messpunkte = MesspunktListe.messpunktListe
        val adapter = MesspunktBearbeitenAdapter(messpunkte, this)
        binding?.rvnetzwerkliste?.adapter = adapter


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungBearbeitenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onItemClick(position: Int) {
    }

}