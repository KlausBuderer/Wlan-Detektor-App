package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitDialogBinding

class EchtzeitDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEchtzeitDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEchtzeitDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val videoNetzwahlView = binding.video
        val videoNetzwahlPfad = "android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl

        val videoMessungView = binding.videoMessung
        val videoMessungPfad = "android.resource://" + requireActivity().packageName + "/" + R.raw.messung

        val uriNetzwahl = Uri.parse(videoNetzwahlPfad)
        val uriMessung = Uri.parse(videoMessungPfad)

        val mediaController = MediaController(context)
        videoNetzwahlView.setMediaController(mediaController)
        videoNetzwahlView.setVideoURI(uriNetzwahl)
        mediaController.setAnchorView(videoNetzwahlView)
        videoNetzwahlView.requestFocus()
        videoNetzwahlView.start()
        videoNetzwahlView.setOnCompletionListener {
            videoNetzwahlView.start()
        }

        videoMessungView.setMediaController(mediaController)
        videoMessungView.setVideoURI(uriMessung)
        mediaController.setAnchorView(videoMessungView)
        videoMessungView.requestFocus()
        videoMessungView.start()
        videoMessungView.setOnCompletionListener {
            videoMessungView.start()
        }

        Log.e("Videopfad","${uriNetzwahl.toString()}")
        return root


    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}