package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentVisualisierungBinding
import com.gruppe4.wlan_detektor.databinding.VisuDetailFragmentBinding
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MessungHinzufuegenDirections
import java.io.File


class VisuDetailFragment : Fragment() {

    companion object {
        fun newInstance() = VisuDetailFragment()
    }

    private lateinit var viewModel: VisuDetailViewModel
    val args: VisuDetailFragmentArgs by navArgs()
    private var _binding: VisuDetailFragmentBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = VisuDetailFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root
        val netzwerkInfo = NetzwerkInfo(requireActivity().application)
        var myBitmap: Bitmap? = null
        if (args.bildPfad.isNotBlank()) {
            val bildFile = File(args.bildPfad)

            //Reduzierung der Auflösung um Speicherauslastung zu reduzieren
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            //Aufruf Bild aus Files
             myBitmap = BitmapFactory.decodeFile(bildFile.absolutePath,options)

        }

        binding.tvRaumName.text = args.raumname
        binding.tvGebaeude.text = args.gebaeude
        binding.tvStockwerk.text = args.stockwerk
        binding.tvZusatzinfo.text = args.zusatzinfo
        binding.tvDatum.text = args.datum
        binding.tvZeit.text = args.zeit
        binding.tvDatumAendern.text = args.aenderungsdatum
        binding.tvZeitAendern.text = args.aenderungszeit
        binding.tvPegel.text = args.pegel.toString()
        binding.pgProgressBar.progress = args.pegel
       // binding.pgProgressBar.progressTintList = ColorStateList.valueOf(netzwerkInfo.progressBarFarbeEinstellen(args.pegel))
        if (myBitmap != null) {
            binding.musspunktBild.setImageBitmap(myBitmap)

            binding.musspunktBild.setOnClickListener{
                val action =
                    VisuDetailFragmentDirections.actionVisuDetailFragmentToVisuFullScreenBild(
                        args.bildPfad
                    )

                Navigation.findNavController(binding.root).navigate(action)
            }
        }



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VisuDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}