package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Messungsliste View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungListeFragment : Fragment(), MessungListeAdapter.OnItemClickListener {

    var _binding: MessungListeFragmentBinding? = null
    private val binding get() = _binding!!
    var messungsListe: List<TblMessung>? = null
    val args: MessungListeFragmentArgs by navArgs()
    lateinit var dialog: Dialog

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

        dialog = Dialog(requireContext())
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungListeViewModel::class.java)

        lifecycleScope.launch { viewModel.getAlleMessungen() }

        viewModel.messungsliste.observe(viewLifecycleOwner, Observer {
            messungsListe = it
            val adapter = MessungListeAdapter(
                messungsListe,
                this,
                requireActivity().application,
                args.context
            )
            binding?.rvMessungsliste?.adapter = adapter
            if (it.isNullOrEmpty()) {
                binding.tvKeineMessungen.visibility = TextView.VISIBLE
            }
        })
    }

    override fun onItemClick(position: Int) {
        //Messung bearbeiten
        if (args.context.equals(MESSUNGLISTE_KONTEXT.Bearbeiten.toString())) {
            val action =
                MessungListeFragmentDirections.actionMessungListeFragmentToMessungBearbeitenFragment(
                    messungsListe?.get(position)?.name ?: "kein Name"
                )
            Navigation.findNavController(binding.root).navigate(action)

        } else {

            dialog.setContentView(R.layout.loesch_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val abbrechenButton = dialog.findViewById<Button>(R.id.btn_abbrechen)
            val loeschenButton = dialog.findViewById<Button>(R.id.btn_loeschen)

            abbrechenButton.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Messung nicht gelöscht",
                    Toast.LENGTH_LONG
                ).show()
                dialog.dismiss()
            }

            loeschenButton.setOnClickListener {
                try {
                    val job = lifecycleScope.launch(Dispatchers.IO) {
                        messungsListe?.get(position)?.let { viewModel.deleteMessung(it.name) }
                    }
                    Navigation.findNavController(binding.root).navigate(
                        R.id.action_messungListeFragment_to_navigation_messung

                    )
                } catch (e: IOException) {
                    Log.e("Löschauftrag:", "Fehlgeschlagen")
                }
                dialog.dismiss()
            }
        }
    }
}