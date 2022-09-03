package com.railway.ncs.fragments

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kpk.eChallan.database.AppDao
import com.kpk.eChallan.database_tables.TicketsTable
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentNewBookingBinding
import com.railway.ncs.extension.toast
import com.railway.ncs.extension.utils.isValidCnic
import com.railway.ncs.extension.utils.isValidPhoneNumber
import com.railway.ncs.model.GenerateTicketRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class NewBookingFragment : Fragment() {
    @Inject
    lateinit var appDao: AppDao
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentNewBookingBinding
    var noOfAdultsSeate =0
    var noOfChildsSeate =0
    var cnic = ""
    var mobile = ""
    var train = ""
    var from = ""
    var to =""
    var classType = ""
    var adult = ""
    var child = ""
    var totalFare =""
    var bitmap: Bitmap? = null
    var imgStringQr: String=""
    var base65=""
    var toCityId:Int=0
    var fromCityId:Int=0
    var trainId =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout.fragment_new_booking, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_booking, container, false)

     init()

        clickListener()


        return binding.root
    }

    private fun init() {
         cnic = binding.etCNIC.text.toString().trim()
         mobile = binding.etMobileNumber.text.toString().trim()
         train = binding.etTrains.text.toString().trim()
         from = binding.edtFrom.text.toString().trim()
         to = binding.edtT0.text.toString().trim()
         classType = binding.edtClass.text.toString().trim()
         adult = binding.edtAdult.text.toString().trim()
         child = binding.edtChild.text.toString().trim()
         totalFare = binding.txttotalAmount.text.toString().trim()


        var header = " نئی بکنگ "
        (requireActivity() as MainActivity).setHeader(header)

        appDao.getAllTrains().observe(requireActivity(), androidx.lifecycle.Observer {it->
            if (it!=null){
                val etTrainArray: ArrayList<String> = ArrayList()
                it.forEach {data->
                 etTrainArray.add(data.trainName)
                }
                //EditText Train
                val etTrain = binding.etTrains
              //  val etTrainArray: ArrayList<String> = ArrayList()
               /* etTrainArray.add("Green Lines Islamabad")
                etTrainArray.add("Subak Khurram")
                etTrainArray.add("Akbar Express")
                etTrainArray.add("Attock Passenger")*/
                val etTrainList: ArrayList<String> = etTrainArray
                val textViewTrain = etTrain as AutoCompleteTextView
                val adapterTrain = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, etTrainList)
                textViewTrain.threshold = 1
                val filtersListTrain = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(40))
                textViewTrain.filters = filtersListTrain
                textViewTrain.setAdapter(adapterTrain)
            }
        })



        //from db
        appDao.getCities().observe(requireActivity(), androidx.lifecycle.Observer {
            if (null != it) {
                //EditText From
                val edtFrom = binding.edtFrom
                val fromArray: ArrayList<String> = ArrayList()
                it.forEach { list ->
                    fromArray.add(list.cityName)
                }
                val FromList: ArrayList<String> = fromArray
                val textViewFrom = edtFrom as AutoCompleteTextView
                val adapterFrom = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, FromList)
                textViewFrom.threshold = 1
                val filtersFrom = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(40))
                textViewFrom.filters = filtersFrom
                textViewFrom.setAdapter(adapterFrom)
                //EditText To
                val edtTo = binding.edtT0
                val edtToList: ArrayList<String> = fromArray
                val textViewTo = edtTo as AutoCompleteTextView
                val adapterTo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, edtToList)
                textViewTo.threshold = 1
                val filtersListTo = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(40))
                textViewTo.filters = filtersListTo
                textViewTo.setAdapter(adapterTo)
            }
        })


        //EditText Class
        val edtClass = binding.edtClass
        val edtClassArray: ArrayList<String> = ArrayList()
        edtClassArray.add("Bussiness")
        edtClassArray.add("Economy")

        val edtClassList: ArrayList<String> = edtClassArray
        val textViewClass = edtClass as AutoCompleteTextView
        val adapterClass = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, edtClassList)
        textViewClass.threshold = 1
        val filtersListClass = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(40))
        textViewClass.filters = filtersListClass
        textViewClass.setAdapter(adapterClass)
            }

    private fun clickListener() {
      /*  binding.etTrains.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etTrains.hint = null
            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!char.isNullOrEmpty()){
                    binding.etTrains.hint = null

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })*/

        binding.txtCalculate.setOnClickListener {
          var total = noOfAdultsSeate+noOfChildsSeate
            binding.txttotalAmount.text = total.toString()
        }

        binding.txtNext.setOnClickListener {

            if(checkFields()){
                generateQr()
                if (imgStringQr!="") {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val currentDateandTime: String = sdf.format(Date())
                    ticketsTableCompanion.apply {
                        this?.equals(null)
                    }


                    appDao.getCities()
                        .observe(requireActivity(), androidx.lifecycle.Observer { it ->
                            it.forEach { list ->
                                if (list.cityName == binding.edtFrom.text.toString()) {
                                    toCityId = list.id
                                    Log.d("To", toCityId.toString())
                                }
                                if (list.cityName == binding.edtT0.text.toString()) {
                                    fromCityId = list.id
                                    Log.d("From", fromCityId.toString())
                                }
                            }

                            var trainClass = ""
                            appDao.getAllTrains()
                                .observe(requireActivity(), androidx.lifecycle.Observer { trains ->

                                    trains.forEach { list ->
                                        if (list.trainName == binding.edtClass.text.toString()) {
                                            trainId = list.id
                                            trainClass = list.trainCode
                                        }
                                    }
                                val ticketsTable:TicketsTable = TicketsTable(
//                                        12164,
                                        base65,
                                        noOfAdultsSeate,
                                        noOfChildsSeate,
                                        cnic,
                                        currentDateandTime,
                                        fromCityId,
                                        mobile,
                                        noOfChildsSeate + noOfAdultsSeate,
                                        1,
                                        toCityId,
                                        "" + noOfChildsSeate + noOfAdultsSeate,
                                        1,
                                        trainId,
                                        1
                                    )
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        appDao.addTicket(ticketsTable)

                                    }
                                })


                        })
                   /* var trainClass =""
                    appDao.getAllTrains().observe(requireActivity(), androidx.lifecycle.Observer { trains->

                        trains.forEach {list->
                            if (list.trainName == binding.edtClass.text.toString()){
                                trainId = list.id
                                trainClass = list.trainCode
                            }
                        }
                    })*/

                         //   val userId:Int = sharedPreferences.getInt("userId",0)
                    /* ticketsTableCompanion = TicketsTable(12164,base65, noOfAdultsSeate, noOfChildsSeate,
                        cnic,currentDateandTime,fromCityId,mobile,noOfChildsSeate+noOfAdultsSeate,
                        1,toCityId,""+noOfChildsSeate+noOfAdultsSeate,1,trainId,1)*/
                    findNavController().navigate(R.id.action_newBookingFragment_to_receiptFragment)

                }else{
                    requireContext().toast("base64")
                }



            }

        }
        binding.txtcancel.setOnClickListener {
            findNavController().navigate(R.id.action_newBookingFragment_to_dashBoardFragment)
        }

        binding.imgAdultPlus.setOnClickListener {
           noOfAdultsSeate++
            binding.edtAdult.setText(noOfAdultsSeate.toString())
        }
        binding.imgAdultMinus.setOnClickListener {
            noOfAdultsSeate--
            if (noOfAdultsSeate>=0){
                binding.edtAdult.setText(noOfAdultsSeate.toString())
            }else{
                noOfAdultsSeate = 0
            }

        }

        binding.imgChildPlus.setOnClickListener {
            noOfChildsSeate++
            binding.edtChild.setText(noOfChildsSeate.toString())
        }

        binding.imgChildMinus.setOnClickListener {
            noOfChildsSeate--
            if (noOfChildsSeate>=0){
                binding.edtChild.setText(noOfChildsSeate.toString())
            }else{
                noOfChildsSeate = 0
            }

        }


    }

    private fun checkFields(): Boolean {
        var bool = true
         cnic = binding.etCNIC.text.toString().trim();
         mobile = binding.etMobileNumber.text.toString().trim();
         train = binding.etTrains.text.toString().trim();
         from = binding.edtFrom.text.toString().trim();
         to = binding.edtT0.text.toString().trim();
         classType = binding.edtClass.text.toString().trim();
         adult = binding.edtAdult.text.toString().trim();
         child = binding.edtChild.text.toString().trim();
         totalFare = binding.txttotalAmount.text.toString().trim();
        if (!isValidCnic(cnic)){
            bool = false
             requireActivity().toast("Enter Valid Cnic")
            return bool

        }
        if (!isValidPhoneNumber(mobile)){
            bool = false
            requireActivity().toast("Enter Mobile Number")
            return bool
        }
        if (train == ""){
            bool = false
             requireActivity().toast("Select Train")
            return bool
        }
        if (from == ""){
             requireActivity().toast("Select From")
            return bool
        }
        if (to == ""){
            bool = false
             requireActivity().toast("Select To")
            return bool
        }
        if (classType == ""){
            bool = false
            requireActivity().toast("Select Class Type")
            return bool
        }
        if (adult == ""){
            bool = false
            requireActivity().toast("Enter Cnic")
            return bool
        }
        if (child == ""){
            bool = false

            requireActivity().toast("Enter No of child")
            return bool
        }
       /* if (totalFare == ""){
            bool = false
            requireActivity().toast("Enter Cnic")
        }*/
     return bool
    }
    private fun generateQr() {
        var data = ""
        data = """
             CNIC : $cnic
             Mobile No  : $mobile
             Train : $train
             From : $from
             To : $to
             Class : $classType
             Adult : $adult
             Child : $child
             Total Amount : $totalFare
             """.trimIndent()

        // Initialize multi format writer
        val writer = MultiFormatWriter()
        // Initialize bit matrix
        try {
            //val encryptedString: String = encrypt(data)
            val matrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 370, 250)

            // Initialize barcode encoder
            val encoder = BarcodeEncoder()

            // Initialize Bitmap
            bitmap = encoder.createBitmap(matrix)
            getBase64ImageString(bitmap)
         base65 = getBase64ImageString(bitmap)
            Log.d("Base",base65.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getBase64ImageString(photo: Bitmap?): String {
       
        imgStringQr = if (photo != null) {
            val outputStream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val profileImage: ByteArray = outputStream.toByteArray()
            Base64.encodeToString(
                profileImage,
                Base64.NO_WRAP
            )
        } else {
            ""
        }
        return imgStringQr
    }
    companion object {
         var ticketsTableCompanion:TicketsTable?=null
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewBookingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}