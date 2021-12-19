package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gruppe4.wlan_detektor.databinding.FragmentVisualisierungBinding

class VisualisierungFragment : Fragment() {

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

        val textView: TextView = binding.textVisualisierung
        visualisierungViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}