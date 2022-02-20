package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor_pro.databinding.FragmentVisuFullScreenBildBinding
import java.io.File

class VisuFullScreenBild : Fragment() {

    private var _binding: FragmentVisuFullScreenBildBinding? = null
    private val binding get() = _binding!!
    val args: VisuFullScreenBildArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVisuFullScreenBildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var myBitmap: Bitmap? = null
        if (args.bildpfad.isNotBlank()) {
            val bildFile = File(args.bildpfad)

            // Reduzierung der Auflösung um Speicherauslastung zu reduzieren
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            // Aufruf Bild aus Files
            myBitmap = BitmapFactory.decodeFile(bildFile.absolutePath, options)

            binding.ivBild.setImageBitmap(myBitmap)
        }

        return root
    }
}
