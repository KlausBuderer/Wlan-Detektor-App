package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.net.wifi.SupplicantState
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentMessungHinzufuegenBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung


class MessungHinzufuegen : Fragment() {

    companion object {
        fun newInstance() = MessungHinzufuegen()
    }

    private lateinit var viewModel: MessungHinzufuegenViewModel
    private var _binding: FragmentMessungHinzufuegenBinding? = null
    lateinit var speichernButton: Button
    lateinit var eingabeNamen: EditText
    lateinit var raeumlichkeit: AutoCompleteTextView
    var raeumlichkeitPosition: Int = -1


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.raeumlichkeiten_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.dropdown_background))
        viewModel.startUpdateCoroutine()

        val dialog = Dialog(requireContext())

        //Pruefen ob die Berechtigung vorhanden ist
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Berechtigung vorhanden
        } else {
            // Erstellen eines Dialogs, um die Berechtigung zu erteilen
            dialog.setContentView(R.layout.berechtigung_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val okButton = dialog.findViewById<Button>(R.id.btn_einstellung)
            val abbrechenButton = dialog.findViewById<Button>(R.id.btn_abbrechen)

            okButton.setOnClickListener {
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                dialog.dismiss()
            }

            abbrechenButton.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "..Schade, leider können wir die SSID nicht ausgeben",
                    Toast.LENGTH_LONG
                ).show()

                dialog.dismiss()
            }
        }

        //Pruefung ob mit Netzwerk verbunden und reinitialisierung der Buttonfreigabe
        viewModel.konditionNetzAngemeldet = viewModel.netzwerkInfo.value?.supplicantState == SupplicantState.COMPLETED
        binding.messungSpeichern.isEnabled = viewModel.buttonFreigabe()
        binding.etNetzwerk.startIconDrawable?.setVisible(viewModel.konditionNetzAngemeldet,true)
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

        speichernButton = binding.messungSpeichern
        eingabeNamen = binding.editTextMessungName
        raeumlichkeit = binding.autoCompleteTextView




        eingabeNamen.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            //Validierung des Namens der Messung
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.namenValidierenRoutine(eingabeNamen.text.toString())
                Log.e("OnChange","${eingabeNamen.text.toString()}")


            }

            //Signalisierung das Namen der Messung bereits vergeben ist
            override fun afterTextChanged(s: Editable?) {
                var validierung: Boolean = true

                viewModel.nameValide.observe(viewLifecycleOwner, Observer {
                    validierung = it
                })

                Log.d("validierung","${validierung}")

                if (viewModel.result > 0) {
                    eingabeNamen.error = resources.getString(R.string.txt_namen_bereits_vergeben)
                    viewModel.konditionNamenValide = false
                    speichernButton.isEnabled = viewModel.buttonFreigabe()
                }else if (s.isNullOrBlank()){
                    viewModel.konditionNamenValide = false
                    speichernButton.isEnabled = viewModel.buttonFreigabe()
                } else {
                    eingabeNamen.error = null
                    viewModel.konditionNamenValide = true
                    speichernButton.isEnabled = viewModel.buttonFreigabe()
                }
            }
        })

        binding.etNetzwerk.setEndIconOnClickListener{
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        viewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {

            //Pruefung ob mit Wlan verbunden
            if (it.supplicantState == SupplicantState.COMPLETED){
                binding.netzwerk.editableText.clear()
                binding.netzwerk.editableText.insert(0, it.ssid)
            }else{
                binding.netzwerk.editableText.clear()
                binding.netzwerk.editableText.insert(0, getString(R.string.txt_netzTitel_de))
            }
        })

        //Pruefen ob Art der Raeumlichkeit gewählt wurde
        raeumlichkeit.setOnItemClickListener { parent, view, position, id ->
            viewModel.pruefenRaeumlichkeitWahl(position)
            raeumlichkeitPosition = position
            speichernButton.isEnabled = viewModel.buttonFreigabe()
        }


        //Speicherbutton Freigabe
        viewModel.speicherFreigabe.observe(viewLifecycleOwner, Observer {
            speichernButton.isEnabled = it
        })

        speichernButton.setOnClickListener {


            var messung: TblMessung = TblMessung(
                eingabeNamen.text.toString(),
                binding.netzwerk.editableText.toString(),
                raeumlichkeitPosition,
                viewModel.getDatum(),
                viewModel.getZeit()
            )

            viewModel.messungSpeichern(messung)

            val action =
                MessungHinzufuegenDirections.actionMessungHinzufuegenToMessungBearbeitenFragment(
                    eingabeNamen.text.toString()
                )

            Navigation.findNavController(binding.root).navigate(action)

        }

    }
    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED


    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopUpdateCoroutine()
    }
}

