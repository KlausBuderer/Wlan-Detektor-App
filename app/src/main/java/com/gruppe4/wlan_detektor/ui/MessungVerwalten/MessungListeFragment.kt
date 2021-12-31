package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor.ui.MessungListe

class MessungListeFragment : Fragment(), MessungListeAdapter.OnItemClickListener {

    var _binding: MessungListeFragmentBinding? = null
    private val binding get() = _binding!!

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

        var messungen = MessungListe.messungListe
        val adapter = MessungListeAdapter(messungen, this)
        binding?.rvmessungsliste?.adapter = adapter



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungListeViewModel::class.java)
    }

    override fun onItemClick(position: Int) {
       Navigation.findNavController(binding.root).navigate(
          R.id.action_messungListeFragment_to_messungBearbeitenFragment
        )
    }

}