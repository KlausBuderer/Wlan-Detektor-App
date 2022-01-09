package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.ui.MessungListe
import kotlinx.android.synthetic.main.messung_liste_fragment.*
import kotlinx.coroutines.launch

class MessungListeFragment : Fragment(), MessungListeAdapter.OnItemClickListener {

    var _binding: MessungListeFragmentBinding? = null
    private val binding get() = _binding!!
    var messungsListe: List<TblMessung>? = null


    companion object {
        fun newInstance() = MessungListeFragment()
    }

    private lateinit var viewModel: MessungListeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessungListeFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungListeViewModel::class.java)

        lifecycleScope.launch { viewModel.getAlleMessungen() }



        viewModel.messungsliste.observe(viewLifecycleOwner, Observer {
            messungsListe = it
            val adapter = MessungListeAdapter(messungsListe, this, requireActivity().application)
            binding?.rvMessungsliste?.adapter = adapter
        })


    }

    override fun onItemClick(position: Int) {

        val action =
            MessungListeFragmentDirections.actionMessungListeFragmentToMessungBearbeitenFragment(
                messungsListe?.get(position)?.name ?: "kein Name"
            )

        Navigation.findNavController(binding.root).navigate(action)

    }

}