package com.railway.ncs.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.kpk.eChallan.database.AppDao
import com.pha.lahore.dialogs.ProgressBarDialog
import com.pha.lahore.state.State
import com.pha.lahore.viewmodel.GenerateTicketViewModel
import com.railway.ncs.Encription.Cryptography_Android
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.Utils.Constant
import com.railway.ncs.databinding.FragmentReceiptBinding
import com.railway.ncs.extension.bottomToast
import com.railway.ncs.extension.hideProgressBar
import com.railway.ncs.extension.showProgressBar
import com.railway.ncs.extension.toast
import com.railway.ncs.fragments.NewBookingFragment.Companion.ticketsTableCompanion
import com.railway.ncs.model.GenerateTicketRequest
import com.railway.ncs.model.EncryptedRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReceiptFragment : Fragment() {
    val ticketViewModel:    GenerateTicketViewModel by viewModels()
    var progressBarDialogRegister: ProgressBarDialog? = null
    lateinit var binding:FragmentReceiptBinding
    @Inject
    lateinit var appDao: AppDao
    var fromId by Delegates.notNull<Int>()
    var toId by Delegates.notNull<Int>()
    var trainId by Delegates.notNull<Int>()


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
       // return inflater.inflate(R.layout.fragment_receipt, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_receipt, container, false)
      init()

        clickListener()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        progressBarDialogRegister = ProgressBarDialog(requireContext())
        var header = " رسید "
        (requireActivity() as MainActivity).setHeader(header)
        appDao.getBookedTicket().observe(requireActivity(), androidx.lifecycle.Observer {
            it?.forEach { list ->
                binding.txtTicketId.text = "SR# "+ticketsTableCompanion?.ticketId.toString()
                binding.txtDateTime.text = list.dateAndTime
                binding.txtTotalSeat.text = list.noOfPassenger.toString()
                binding.tvFarePrice.text = list.totalFare


                var adult:Int = list.adult
                var child:Int = list.child
                var cnic:String = list.cnic
                var currentDateandTime:String = list.dateAndTime
                var mobile:String = list.mobile
                var totalPassenger:Int = list.noOfPassenger
                var toCity:Int = list.toCity
                var FromCity:Int = list.fromCity
                var totalFare:String = list.totalFare
            }
        })





    }

    private fun clickListener() {
        binding.txtConfirm.setOnClickListener {
            /*viewLifecycleOwner.lifecycleScope.launch {
                         appDao.tickets(ticketsTableCompanion)
                     }*/
            //printing...
           /* var adult:Int = ticketsTableCompanion!!.adult
            var child:Int = ticketsTableCompanion!!.child
            var cnic:String = ticketsTableCompanion!!.cnic
            var currentDateandTime:String = ticketsTableCompanion!!.dateAndTime
            var mobile:String = ticketsTableCompanion!!.mobile
            var totalPassenger:Int = ticketsTableCompanion!!.noOfPassenger
            var toCity:Int = ticketsTableCompanion!!.toCity
            var FromCity:Int = ticketsTableCompanion!!.fromCity
            var totalFare:String = ticketsTableCompanion!!.noOfPassenger.toString()*/

            appDao.getBookedTicket().observe(requireActivity(), androidx.lifecycle.Observer {
                it?.forEach { list ->
                    var adult:Int = list.adult
                    var child:Int = list.child
                    var cnic:String = list.cnic
                    var currentDateandTime:String = list.dateAndTime
                    var mobile:String = list.mobile
                    var totalPassenger:Int = list.noOfPassenger
                    var toCity:Int = list.toCity
                    var FromCity:Int = list.fromCity
                    var totalFare:String = list.totalFare
                    var qr ="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKUAAAB8CAMAAAA/xIAPAAAAXVBMVEXDw8MAAACxsbEeHh7IyMgMDAxwcHC0tLRqamptbW2rq6sRERHLy8ukpKS3t7chISE6Ojo/Pz9iYmKMjIydnZ0tLS1JSUmCgoJWVlaVlZUmJiZ8fHy9vb1RUVEzMzMTCS+gAAABZklEQVR4nO3Y3W6CQBBAYQbHgrCs4H8t9f0fs6BowRrgppltc74rM/HiZBkl2SgCAAAAAAAAAAAAAPxL6uNkntirWaWvFjOtK29WGedlOk+Zx3aVyzenc7i3pWlls27q3NTSqXmlFpu0mMg0r9StNHbjmfaVbaSU9egXrStdfK1cj0dYV74+S/X1YAXsKz/ayvdh1O5cJv2JeWWTlK4K1x9rsmhOdzAxr4zav+3BWKv2eNPeMIDKZ7cdGGxBeJW6k873aoZXWe/vlZ+PWSiVmnVvST3IQ3r/UQVSqYXIsf3sjtJzf78HUpmcb01aLPuV5ySkSnd9zJdaNZeBgwunUrvHfHKpPDlqKJXtUnaZz5HdaoZQGV1+xj1cojAq9cUJ9pw0hEq3HY0U2Tr7Ss3yico8U/vK/USkyN6+cjMZKbIxr8zmsK3My9U8ljcwvlr/gdss9fFchjeDAAAAAAAAAAAAAIBf9QWz2hOtRq0ApAAAAABJRU5ErkJggg=="
                    // var qr = ticketsTableCompanion!!.QrCode
                    var generateTicketRequest=
                        GenerateTicketRequest(
                            "data:image/png;base64,$qr","123455", 1,
                            123,mobile,cnic,FromCity,toCity,1, totalPassenger,adult,child,totalFare,
                            currentDateandTime,1)
                    generateTicket(generateTicketRequest)
                }

            })


          //  findNavController().navigate(R.id.action_receiptFragment_to_printReceiptFragment)
        }
        binding.txtcancel.setOnClickListener {
            findNavController().navigate(R.id.action_receiptFragment_to_newBookingFragment)
        }
    }

    private fun generateTicket(generateTicketRequest: GenerateTicketRequest) {
        val json = Gson().toJson(generateTicketRequest)

        var reqeust = ""
        try {
            reqeust = Cryptography_Android.Encrypt(json, Constant.CRYPTKEY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val EncryptedRequest = EncryptedRequest()
        EncryptedRequest.cipher= reqeust
        ticketViewModel.booleanGenerateTicket = false
        ticketViewModel.generateTicket(EncryptedRequest)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                ticketViewModel.postStateFlowGenerateTicket.collectLatest{ state->
                    when (state) {
                        is State.Loading -> {
                            requireActivity().showProgressBar(progressBarDialogRegister)
                        }
                        is State.Error -> {
                            ticketViewModel.booleanGenerateTicket = false
                            requireActivity().hideProgressBar(progressBarDialogRegister)
                            when (state.code) {
                                404 -> {
                                    requireActivity().bottomToast("Invalid user name or Password")
                                }
                                401 -> {
                                    requireActivity().bottomToast(state.message)
                                }
                                405 -> {
                                    requireActivity().bottomToast(state.message)
                                }
                                500 -> {
                                    requireActivity().bottomToast("Something went wrong, Please try Again Later ")
                                }
                                600 ->{
                                    requireActivity().bottomToast("Check your internet connection and try Again Later ")
                                }
                                else->{
                                    requireActivity().bottomToast(state.code.toString())
                                }
                            }
                        }
                        is State.Success -> {

                            when (state.code) {
                                200 -> {

                                    requireActivity().hideProgressBar(progressBarDialogRegister)
                                    val response : String = state.data.cipher.toString()
                                    Log.d("TAG1", "success $response")
                                    requireActivity().toast("success")
                                  //  findNavController().navigate(R.id.action_receiptFragment_to_printReceiptFragment)

                                }
                                201 -> {

                                    requireActivity().hideProgressBar(progressBarDialogRegister)
                                    val response : String = state.data.cipher.toString()
                                    Log.d("TAG1", "success $response")
                                    requireActivity().toast("success")
                                  //  findNavController().navigate(R.id.action_receiptFragment_to_printReceiptFragment)

                                }
                            }
                        }
                        is State.Empty -> {
                            ticketViewModel.booleanGenerateTicket = false
                        }

                    }
                }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceiptFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}