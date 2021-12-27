package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentMessungHinzufuegenBinding

class MessungHinzufuegen : Fragment() {

    companion object {
        fun newInstance() = MessungHinzufuegen()
    }

    private lateinit var viewModel: MessungHinzufuegenViewModel
    private var _binding: FragmentMessungHinzufuegenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.raeumlichkeiten_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMessungHinzufuegenBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungHinzufuegenViewModel::class.java)
        // TODO: Use the ViewModel
    }


}