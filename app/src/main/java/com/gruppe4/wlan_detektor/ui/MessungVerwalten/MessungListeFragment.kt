package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MessungListeFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

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
                    val job =  lifecycleScope.launch(Dispatchers.IO) {
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
           /* //Messung löschen
            val builder = AlertDialog.Builder(requireContext())
            //Dialog Titel
            builder.setTitle("Messung löschen")
            //Dialog Text
            builder.setMessage("Soll die Messung entgültig gelöscht werden?")
            //Dialog Icon
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //Ja Button
            builder.setPositiveButton("Löschen") { dialogInterface, which ->

            }



            //Nein Button

            }

            // Erstellen des Dialogs
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
*/
        }


    }
}