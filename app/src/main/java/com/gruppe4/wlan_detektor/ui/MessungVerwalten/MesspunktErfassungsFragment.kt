package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.getExternalFilesDirs
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MesspunktErfassungsFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

lateinit var currentPhotoPath: String


class MesspunktErfassungsFragment : Fragment() {


    private var _binding: MesspunktErfassungsFragmentBinding? = null
    private val binding get() = _binding!!
    val args: MesspunktErfassungsFragmentArgs by navArgs()
    private var gebaeudeNamen: String = ""
    private var stockwerk: Int = -1
    private var raumname: String = ""
    lateinit var editGebaeude: EditText
    lateinit var editStockwerk: AutoCompleteTextView
    lateinit var editRaumname: EditText
    lateinit var editZusatzInfo: EditText
    lateinit var messungStarten: Button

    //lateinit var abbrechen: Button
    lateinit var speichern: Button
    lateinit var fotoHinzufuegen: Button
    lateinit var progressBar: ProgressBar
    lateinit var signalText: TextView
    lateinit var messungsName: TextView
    private var stockwerkPosition: Int = -1
    private var signalStaerke: Int = 0
    private var fotoPfad: String = ""
    private var messpunkt: TblMesspunkt = TblMesspunkt()
    val REQUEST_IMAGE_CAPTURE = 1

    companion object {
        fun newInstance() = MesspunktErfassungsFragment()
    }

    private lateinit var viewModel: MesspunktErfassungsViewModel

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.stockwerk_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MesspunktErfassungsFragmentBinding.inflate(inflater, container, false)

        editGebaeude = binding.etGebaeudeEdit
        editRaumname = binding.etRaumnameEdit
        editStockwerk = binding.autoCompleteTextView
        editZusatzInfo = binding.etZusatzinformationEdit
        speichern = binding.messpunktSpeichern
        //abbrechen = binding.abbrechen
        messungStarten = binding.btnStartMesspunktMessung
        progressBar = binding.pgProgressBar
        signalText = binding.tvSignalstaerkeWert
        messungsName = binding.tvMessungNamen
        fotoHinzufuegen = binding.btnBildHinzufuegen
        messungsName.text = args.messungsname



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MesspunktErfassungsViewModel::class.java)
        viewModel.messungsId = args.messungsId

        //Falls dieses Bild aus einer besteheder Messung aufgerufen wird ist der Speicherbutton
        //freigeschalten
        if (args.messpunktId != -1L) {
            viewModel.konditionStockwerk = true
            viewModel.konditionRaumname = true
            viewModel.konditionGebaeude = true
            speichern.isEnabled = true
        }



        if (args.messpunktId != -1L) {
            lifecycleScope.launch { viewModel.getMesspunkt(args.messpunktId) }
        }else{
            //Vorgeben des zuletzt eingegebenen Gebäude um die Eingabe zu erleichtern

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getMesspunkte(args.messungsId)
            }

            viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
                editGebaeude.editableText.insert(0, it[it.lastIndex].gebaeude)
            })

            viewModel.konditionGebaeude = true

        }


        var netzwerkInfo = NetzwerkInfo(requireActivity().application)

        viewModel.messpunkt.observe(viewLifecycleOwner, Observer {
            try {
                messpunkt = it
                editGebaeude.editableText.insert(0, messpunkt.gebaeude)
                editRaumname.editableText.insert(0, messpunkt.raumname)
                editStockwerk.setText(
                    editStockwerk.adapter.getItem(messpunkt.stockwerkID).toString(), false
                )
                editZusatzInfo.editableText.insert(0, messpunkt.zusatzinformation)
                progressBar.progress = messpunkt.pegelmessung
                progressBar.progressTintList =
                    ColorStateList.valueOf(netzwerkInfo.progressBarFarbeEinstellen(messpunkt.pegelmessung))
                signalText.text = messpunkt.pegelmessung.toString()
                stockwerkPosition = messpunkt.stockwerkID
            } catch (e: Exception) {

                Log.e("Messpunkterfassung", "Schreiben von Werten in die Editboxen nicht möglich")
            }
        })


        Toast.makeText(
            requireContext(),
            args.messpunktId.toString() + args.messungsId.toString() + args.messungsname,
            Toast.LENGTH_LONG
        ).show()

        binding.btnStartMesspunktMessung.setOnClickListener {
            lifecycleScope.launch { viewModel.startUpdates() }
            viewModel.konditionMessung = true
        }

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            progressBar.progressTintList = ColorStateList.valueOf(it)
        })

        viewModel.signalstaerke.observe(viewLifecycleOwner, Observer {
            progressBar.progress = it
            signalText.text = it.toString()
            signalStaerke = it
        })

        editGebaeude.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editGebaeude.text.isNotEmpty()) {
                    viewModel.konditionGebaeude = true

                }

            }

            override fun afterTextChanged(s: Editable?) {
                if (editGebaeude.text.isNotEmpty()) {
                    viewModel.konditionGebaeude = true
                }

                speichern.isEnabled = viewModel.buttonFreigeben()
            }

        })

        editRaumname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editRaumname.text.isNotEmpty()) {
                    viewModel.konditionRaumname = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (editRaumname.text.isNotEmpty()) {
                    viewModel.konditionRaumname = true
                }

                speichern.isEnabled = viewModel.buttonFreigeben()
            }

        })

        editStockwerk.setOnItemClickListener { parent, view, position, id ->
            stockwerkPosition = position
            viewModel.konditionStockwerk = true
            speichern.isEnabled = viewModel.buttonFreigeben()
        }


        fotoHinzufuegen.setOnClickListener {
            //Pruefen ob die Berechtigung vorhanden ist
            if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) || android.os.Build.VERSION.SDK_INT >= 18) {
                //Berechtigung vorhanden
                //val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    //this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    fotoPfad = dispatchTakePictureIntent()
                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                    Log.e("Foto aufnehmen:", "dispatchT... nicht erfolgreich")
                }

            } else {
                val builder = AlertDialog.Builder(requireContext())
                //Dialog Titel
                builder.setTitle("Beachte")
                //Dialog Text
                builder.setMessage("Um die Informationen des Wlan Routers auslesen zu können muss die Lokalisierung erlaubt werden.")
                //Dialog Icon
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //Ja Button
                builder.setPositiveButton("Einstellungen") { dialogInterface, which ->
                    try {
                        //context?.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", activity?.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                }

                //Nein Button
                builder.setNegativeButton("Abbrechen") { dialogInterface, which ->
                    Toast.makeText(
                        requireContext(),
                        "..Schade, leider können wir die SSID nicht ausgeben",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Erstellen des Dialogs
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
        }

        speichern.setOnClickListener {


            if (args.messpunktId == -1L) {
                var _messpunkt: TblMesspunkt = TblMesspunkt(
                    args.messungsId,
                    editGebaeude.text.toString(),
                    stockwerkPosition,
                    editRaumname.text.toString(),
                    editZusatzInfo.text.toString(),
                    signalStaerke,
                    "dB",
                    viewModel.datum,
                    viewModel.zeit,
                    fotoPfad
                )
                viewModel.messpunktSpeichern(_messpunkt)
                Log.e("Messpunkt erfassen viewmodel: ", "messpunkt id -1")
            } else {
                var _messpunkt: TblMesspunkt = TblMesspunkt(
                    messpunkt.idmesspunkt,
                    args.messungsId,
                    editGebaeude.text.toString(),
                    stockwerkPosition,
                    editRaumname.text.toString(),
                    editZusatzInfo.text.toString(),
                    signalStaerke,
                    "dB",
                    messpunkt.erfassungsDatum,
                    messpunkt.erfassungsZeit,
                    viewModel.datum,
                    viewModel.zeit,
                    fotoPfad
                )
                viewModel.messpunktUpdate(_messpunkt)
                Log.e("Messpunkt erfassen viewmodel: ", "messpunkt nicht id -1")
            }


            val action =
                MesspunktErfassungsFragmentDirections.actionMesspunktErfassungsFragmentToMessungBearbeitenFragment(
                    args.messungsname
                )

            Navigation.findNavController(binding.root).navigate(action)
        }


        /*abbrechen.setOnClickListener{

            val action = MesspunktErfassungsFragmentDirections.actionMesspunktErfassungsFragmentToMessungBearbeitenFragment(
                args.messungsname
            )

            Navigation.findNavController(binding.root).navigate(action)

        }*/

    }

    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent(): String {

        var photoFile: File? = null
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.gruppe4.wlan_detektor.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
        return photoFile!!.absolutePath
    }

}