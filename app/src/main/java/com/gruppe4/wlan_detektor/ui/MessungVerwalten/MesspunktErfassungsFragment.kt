package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MesspunktErfassungsFragmentBinding


class MesspunktErfassungsFragment : Fragment() {

    private var _binding: MesspunktErfassungsFragmentBinding? = null


    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MesspunktErfassungsFragment()
    }

    private lateinit var viewModel: MesspunktErfassungsViewModel

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.stockwerk_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MesspunktErfassungsFragmentBinding.inflate(inflater,container,false)



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MesspunktErfassungsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}