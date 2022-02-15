package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MesspunktErfassungsFragmentBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor_pro.ui.Utility.URIPathHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.lang.Exception
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
    lateinit var loeschButton: Button
    lateinit var bildEingefuegtText: TextView
    lateinit var bildEingefuegtBild: ImageView
    lateinit var speichern: Button
    lateinit var fotoHinzufuegen: Button
    lateinit var progressBar: ProgressBar
    lateinit var signalText: TextView
    lateinit var messungsName: TextView
    private var stockwerkPosition: Int = -1
    private var signalStaerke: Int = 0
    private var fotoPfad: String = ""
    private var messpunkt: TblMesspunkt = TblMesspunkt()
    private var wiederholteMessung: Boolean = false
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

        if (fotoPfad.isNotBlank()) {
            bildEingefuegtBild.visibility = ImageView.VISIBLE
            bildEingefuegtText.visibility = ImageView.VISIBLE
        } else {

            bildEingefuegtBild.visibility = ImageView.INVISIBLE
            bildEingefuegtText.visibility = ImageView.INVISIBLE
        }
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
        messungStarten = binding.btnStartMesspunktMessung
        progressBar = binding.pgProgressBar
        signalText = binding.tvSignalstaerkeWert
        messungsName = binding.tvMessungNamen
        fotoHinzufuegen = binding.btnBildHinzufuegen
        messungsName.text = args.messungsname
        loeschButton = binding.messpunktLoeschen
        bildEingefuegtText = binding.tvBildEingefuegt
        bildEingefuegtBild = binding.ivBildEingefuegtCheck

        editStockwerk.setTextColor(resources.getColor(R.color.white))
        editStockwerk.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.dropdown_background))


        if (args.messpunktId == -1L) {
            loeschButton.visibility = Button.INVISIBLE
        }



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
            viewModel.konditionMessung = true
            speichern.isEnabled = true
        }



        if (args.messpunktId != -1L) {
            lifecycleScope.launch { viewModel.getMesspunkt(args.messpunktId) }
        } else {

            //Vorgeben des zuletzt eingegebenen Gebäude um die Eingabe zu erleichtern

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getMesspunkte(args.messungsId)
            }

            viewModel.messpunkte.observe(viewLifecycleOwner, Observer {
                if (it.lastIndex >= 0) {
                    editGebaeude.editableText.insert(0, it[it.lastIndex].gebaeude)
                }
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
                signalText.text = messpunkt.pegelmessung.toString()
                stockwerkPosition = messpunkt.stockwerkID

                if (it.bildPfad.isNotBlank()) {
                    bildEingefuegtBild.visibility = ImageView.VISIBLE
                    bildEingefuegtText.visibility = ImageView.VISIBLE
                } else {

                    bildEingefuegtBild.visibility = ImageView.INVISIBLE
                    bildEingefuegtText.visibility = ImageView.INVISIBLE
                }

            } catch (e: Exception) {

                Log.e("Messpunkterfassung", "Schreiben von Werten in die Editboxen nicht möglich")
            }
        })




        binding.btnStartMesspunktMessung.setOnClickListener {
            viewModel.konditionMessung = true
            speichern.isEnabled = viewModel.buttonFreigeben()

            if (args.messpunktId != -1L) {

                wiederholteMessung = true
            }
            lifecycleScope.launch { viewModel.startUpdates() }
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
                try {
                    fotoPfad = dispatchTakePictureIntent()
                    //Pruefung ob Bild eingefuegt wurde

                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                    Log.e("Foto aufnehmen:", "dispatchT... nicht erfolgreich")
                }

            } else {
                val builder = AlertDialog.Builder(requireContext())
                //Dialog Titel
                builder.setTitle("Beachte")
                //Dialog Text
                builder.setMessage("Um ein Bild aufnehmen zu können, ist die Berechtigung die Kamera zu verwenden notwendig.")
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
                        "..Schade, leider kann kein Bild aufgenommen werden",
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


                var erneuteMessung: Int

                if (wiederholteMessung) {
                    erneuteMessung = signalStaerke
                } else {
                    erneuteMessung = messpunkt.pegelmessung
                }



                var _messpunkt: TblMesspunkt = TblMesspunkt(
                    messpunkt.idmesspunkt,
                    args.messungsId,
                    editGebaeude.text.toString(),
                    stockwerkPosition,
                    editRaumname.text.toString(),
                    editZusatzInfo.text.toString(),
                    erneuteMessung,
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



        loeschButton.setOnClickListener {


            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.loesch_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.findViewById<TextView>(R.id.tv_loeschen_text).text =
                getString(R.string.txt_messpunkt_loeschen)


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
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.deleteMesspunkt(messpunkt.idmesspunkt)
                    }
                    val action =
                        MesspunktErfassungsFragmentDirections.actionMesspunktErfassungsFragmentToMessungBearbeitenFragment(
                            args.messungsname
                        )

                    Navigation.findNavController(binding.root).navigate(action)
                } catch (e: IOException) {
                    Log.e("Löschauftrag:", "Fehlgeschlagen")
                }
                dialog.dismiss()
            }

        }
    }

    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Erstelle Filename
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* pfad */
        ).apply {

            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent(): String {

        var photoFile: File? = null
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Erstelle File für das Bild
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                }
                // Zeige das Bild falls Erfassung erfolgreich
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),

                        "com.gruppe4.wlan_detektor_pro.android.fileprovider",

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