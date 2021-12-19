package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitmessungBinding

class EchtezeitmessungFragment : Fragment() {

    private lateinit var echtzeitmessungViewModel: EchtzeitmessungViewModel
    private var _binding: FragmentEchtzeitmessungBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        echtzeitmessungViewModel =
            ViewModelProvider(this).get(EchtzeitmessungViewModel::class.java)

        _binding = FragmentEchtzeitmessungBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvSignalstaerkeWert
        echtzeitmessungViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

        binding.netzwerkwahl.setOnClickListener {

        }

        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}