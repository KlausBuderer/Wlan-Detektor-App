package com.gruppe4.wlan_detektor.ui.Visualisierung

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gruppe4.wlan_detektor.R


class VisuDetailFragment : Fragment() {

    companion object {
        fun newInstance() = VisuDetailFragment()
    }

    private lateinit var viewModel: VisuDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.visu_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisuDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}