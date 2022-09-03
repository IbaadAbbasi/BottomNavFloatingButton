package com.railway.ncs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import com.android.print.sdk.PrinterConstants
import com.android.print.sdk.PrinterInstance
import com.google.gson.Gson
import com.kpk.eChallan.database.AppDao
import com.kpk.eChallan.database_tables.CitiesTable
import com.pha.lahore.dialogs.ProgressBarDialog
import com.pha.lahore.extension.remove
import com.pha.lahore.state.State
import com.pha.lahore.viewmodel.CitiesViewModel
import com.railway.ncs.Encription.Cryptography_Android
import com.railway.ncs.Utils.Constant
import com.railway.ncs.activities.LoginActivity
import com.railway.ncs.database_tables.AllTrainsTable
import com.railway.ncs.databinding.ActivityMainBinding
import com.railway.ncs.databinding.ActivityMainPrinterBinding
import com.railway.ncs.printpkg.IPrinterOpertion
import com.railway.ncs.printpkg.bluetooth.BluetoothDeviceList
import com.railway.ncs.printpkg.bluetooth.BluetoothOperation
import com.railway.ncs.printpkg.util.PrintUtils
import com.railway.ncs.printpkg.util.PrintUtils.printImage
import com.railway.ncs.extension.bottomToast
import com.railway.ncs.extension.hideProgressBar
import com.railway.ncs.extension.showProgressBar
import com.railway.ncs.model.*
import com.railway.ncs.viewmodel.GetAllTrainsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import java.util.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var appDao: AppDao
    private val citiesViewModel:CitiesViewModel by viewModels()
    private val allTrainsViewModel:GetAllTrainsViewModel by viewModels()
    var progressBarDialogRegister: ProgressBarDialog? = null
//printer
    companion object{
    var  isConnected: Boolean = false
  var myOpertion: IPrinterOpertion? = null
    const val CONNECT_DEVICE = 1
    const val ENABLE_BT = 2
    const val REQUEST_SELECT_FILE = 3
    const val REQUEST_PERMISSION = 4
    }

    private var bt_mac: String? = null
    private var bt_name: String? = null
    private var context: Context? = null

  var mPrinter: PrinterInstance? =null


//printer
    var flag:Boolean = false;
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var binding:ActivityMainBinding
    lateinit var binding1:ActivityMainPrinterBinding
    private var outBoxFragmentId by Delegates.notNull<Int>()
    private var historyFragmentId by Delegates.notNull<Int>()
    private var profileFragmentId by Delegates.notNull<Int>()
    private var batchFragmentId by Delegates.notNull<Int>()
    private var dashBoardFragmentId by Delegates.notNull<Int>()
    private var newBookingFragmentId by Delegates.notNull<Int>()
    private var receiptFragmentId by Delegates.notNull<Int>()
    private var printReceiptFragmentId by Delegates.notNull<Int>()
    private var rePrintReceiptFragmentId by Delegates.notNull<Int>()
    private var editProfileFragmentId by Delegates.notNull<Int>()
    private var id = 4
    var activeIndex = 4

    private val TIME_INTERVAL = 2000 // # milliseconds, desired time passed between two back presses.
    var back_pressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        val window = window
//        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
       // dialog = ProgressDialog(context)
        binding.txtHeader.setOnClickListener {
            showDialogPrinter()
        }
        showDialogPrinter()
        binding.testpirnt.setOnClickListener {
            printImageMain()
        }
        initView()
        context = this
        init()
        clickListener()

       activeIndex = savedInstanceState?.getInt("activeIndex") ?: 4

        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_outbox,
                R.drawable.avd_outbox,
                R.id.outbox
            ),
            CbnMenuItem(
                R.drawable.ic_history,
                R.drawable.avd_history,
                R.id.history
            ),
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.avd_profile,
                R.id.profile
            ),
            CbnMenuItem(
                R.drawable.ic_batch,
                R.drawable.avd_batch,
                R.id.layer
            ),
            CbnMenuItem(
                R.drawable.ic_dashboard,
                R.drawable.avd_dashboard,
                R.id.dashboard
            )
        )

        binding.navView.setMenuItems(menuItems, activeIndex)
        binding.navView.setupWithNavController(navController)

        binding.imgGoNew.setOnClickListener {
            showDialogLog()
            sharedPreferences.remove("userName")
            sharedPreferences.remove("userId")
            sharedPreferences.remove("First_Name")
            sharedPreferences.remove("Middle_Name")
            sharedPreferences.remove("Last_Name")
            sharedPreferences.remove("isLogin")

        }

        binding.navView.setOnMenuItemClickListener { cbnMenuItem, i ->
            id = i
            val lastLoadedFragment = navController.currentDestination?.id

            if (i==0){
                //  navController.navigate(R.id.action_dashBoardFragment_to_historyFragment)
                binding.txtHeader.text = " آؤٹ باکس "
                if (lastLoadedFragment == historyFragmentId){
                    navController.navigate(R.id.action_historyFragment_to_outBoxFragment)
                }
                if (lastLoadedFragment == profileFragmentId){
                    navController.navigate(R.id.action_profileFragment_to_outBoxFragment)
                }
                if (lastLoadedFragment == batchFragmentId){

                    navController.navigate(R.id.action_batchFragment_to_outBoxFragment)

                }
                if (lastLoadedFragment == dashBoardFragmentId){
                    navController.navigate(R.id.action_dashBoardFragment_to_outBoxFragment)

                }
                if (lastLoadedFragment == newBookingFragmentId){
                    navController.navigate(R.id.action_newBookingFragment_to_outBoxFragment)
                }
                //
                if (lastLoadedFragment == receiptFragmentId){
                    navController.navigate(R.id.action_receiptFragment_to_outBoxFragment)
                }
                if (lastLoadedFragment == printReceiptFragmentId){
                    navController.navigate(R.id.action_printReceiptFragment_to_outBoxFragment)
                }
                if (lastLoadedFragment == rePrintReceiptFragmentId){
                    navController.navigate(R.id.action_reprintFragment_to_outBoxFragment)
                }
                if (lastLoadedFragment == editProfileFragmentId){
                    navController.navigate(R.id.action_editProfileFragment_to_outBoxFragment)
                }


            }

            if (i==1){
                binding.txtHeader.text = " تاریخ "
                if (lastLoadedFragment == outBoxFragmentId){
                    navController.navigate(R.id.action_outBoxFragment_to_historyFragment)
                }
                if (lastLoadedFragment == profileFragmentId){
                    navController.navigate(R.id.action_profileFragment_to_historyFragment)
                }
                if (lastLoadedFragment == batchFragmentId){

                    navController.navigate(R.id.action_batchFragment_to_historyFragment)

                }
                if (lastLoadedFragment == dashBoardFragmentId){
                    navController.navigate(R.id.action_dashBoardFragment_to_historyFragment)
                }
                if (lastLoadedFragment == newBookingFragmentId){
                    navController.navigate(R.id.action_newBookingFragment_to_historyFragment)
                }

                //
                if (lastLoadedFragment == receiptFragmentId){
                    navController.navigate(R.id.action_receiptFragment_to_historyFragment)
                }
                if (lastLoadedFragment == printReceiptFragmentId){
                    navController.navigate(R.id.action_printReceiptFragment_to_historyFragment)
                }
                if (lastLoadedFragment == rePrintReceiptFragmentId){
                    navController.navigate(R.id.action_reprintFragment_to_historyFragment)
                }
                if (lastLoadedFragment == editProfileFragmentId){
                    navController.navigate(R.id.action_editProfileFragment_to_historyFragment)
                }

            }

            if (i==2){
                binding.txtHeader.text = " پروفائل "
                if (lastLoadedFragment == outBoxFragmentId){
                    navController.navigate(R.id.action_outBoxFragment_to_profileFragment)
                }
                if (lastLoadedFragment == historyFragmentId){
                    navController.navigate(R.id.action_historyFragment_to_profileFragment)
                }
                if (lastLoadedFragment == batchFragmentId){
                    navController.navigate(R.id.action_batchFragment_to_profileFragment)

                }
                if (lastLoadedFragment == dashBoardFragmentId){
                    navController.navigate(R.id.action_dashBoardFragment_to_profileFragment)

                }
                if (lastLoadedFragment == newBookingFragmentId){
                    navController.navigate(R.id.action_newBookingFragment_to_profileFragment)
                }
                //
                if (lastLoadedFragment == receiptFragmentId){
                    navController.navigate(R.id.action_receiptFragment_to_profileFragment)
                }
                if (lastLoadedFragment == printReceiptFragmentId){
                    navController.navigate(R.id.action_printReceiptFragment_to_profileFragment)
                }
                if (lastLoadedFragment == rePrintReceiptFragmentId){
                    navController.navigate(R.id.action_reprintFragment_to_profileFragment)
                }
                if (lastLoadedFragment == editProfileFragmentId){
                    navController.navigate(R.id.action_editProfileFragment_to_profileFragment)
                }
            }

            if (i==3){
                binding.txtHeader.text = " بیچ  "
                if (lastLoadedFragment == outBoxFragmentId){
                    navController.navigate(R.id.action_outBoxFragment_to_batchFragment)
                }
                if (lastLoadedFragment == historyFragmentId){
                    navController.navigate(R.id.action_historyFragment_to_batchFragment)
                }
                if (lastLoadedFragment == profileFragmentId){
                    navController.navigate(R.id.action_profileFragment_to_batchFragment)
                }
                if (lastLoadedFragment == dashBoardFragmentId){
                    navController.navigate(R.id.action_dashBoardFragment_to_batchFragment)

                }
                if (lastLoadedFragment == newBookingFragmentId){
                    navController.navigate(R.id.action_newBookingFragment_to_batchFragment)
                }

                //
                if (lastLoadedFragment == receiptFragmentId){
                    navController.navigate(R.id.action_receiptFragment_to_batchFragment)
                }
                if (lastLoadedFragment == printReceiptFragmentId){
                    navController.navigate(R.id.action_printReceiptFragment_to_batchFragment)
                }
                if (lastLoadedFragment == rePrintReceiptFragmentId){
                    navController.navigate(R.id.action_reprintFragment_to_batchFragment)
                }
                if (lastLoadedFragment == editProfileFragmentId){
                    navController.navigate(R.id.action_editProfileFragment_to_batchFragment)
                }
            }

            if (i==4){
                binding.txtHeader.text = " ڈیش بورڈ  "
                if (lastLoadedFragment == outBoxFragmentId){
                    navController.navigate(R.id.action_outBoxFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == historyFragmentId){
                    navController.navigate(R.id.action_historyFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == profileFragmentId){
                    navController.navigate(R.id.action_profileFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == batchFragmentId){
                    navController.navigate(R.id.action_batchFragment_to_dashBoardFragment)

                }
                if (lastLoadedFragment == newBookingFragmentId){
                    navController.navigate(R.id.action_newBookingFragment_to_dashBoardFragment)
                }
                //
                if (lastLoadedFragment == receiptFragmentId){
                    navController.navigate(R.id.action_receiptFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == printReceiptFragmentId){
                    navController.navigate(R.id.action_printReceiptFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == rePrintReceiptFragmentId){
                    navController.navigate(R.id.action_reprintFragment_to_dashBoardFragment)
                }
                if (lastLoadedFragment == editProfileFragmentId){
                    navController.navigate(R.id.action_editProfileFragment_to_dashBoardFragment)
                }
            }


        }



    }

    private fun init() {
        progressBarDialogRegister = ProgressBarDialog(this)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        outBoxFragmentId = navController.graph[R.id.outBoxFragment].id
        historyFragmentId = navController.graph[R.id.historyFragment].id
        profileFragmentId = navController.graph[R.id.profileFragment].id
        batchFragmentId =  navController.graph[R.id.batchFragment].id
        dashBoardFragmentId = navController.graph[R.id.dashBoardFragment].id
        newBookingFragmentId = navController.graph[R.id.newBookingFragment].id
        receiptFragmentId = navController.graph[R.id.receiptFragment].id
        printReceiptFragmentId = navController.graph[R.id.printReceiptFragment].id
        rePrintReceiptFragmentId = navController.graph[R.id.reprintFragment].id
        editProfileFragmentId = navController.graph[R.id.editProfileFragment].id


        getCitiesList()
        getAllTrains()

      }

    private fun getCitiesList() {

            citiesViewModel.booleanCities = false
            citiesViewModel.getCities()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED){
                    citiesViewModel.postStateFlowCities.collectLatest{ state->
                        when (state) {
                            is State.Loading -> {
                                showProgressBar(progressBarDialogRegister)
                            }
                            is State.Error -> {
                                citiesViewModel.booleanCities = false
                                hideProgressBar(progressBarDialogRegister)
                                when (state.code) {
                                    404 -> {
                                        bottomToast("Invalid user name or Password")
                                    }
                                    401 -> {
                                        bottomToast(state.message)
                                    }
                                    405 -> {
                                        bottomToast(state.message)
                                    }
                                    500 -> {
                                        bottomToast("Something went wrong, Please try Again Later ")
                                    }
                                    600 ->{
                                        bottomToast("Check your internet connection and try Again Later ")
                                    }
                                    else->{
                                        bottomToast(state.code.toString())
                                    }
                                }
                            }
                            is State.Success -> {

                                when (state.code) {
                                    200 -> {
                                        hideProgressBar(progressBarDialogRegister)
                                        val response : String = state.data.cipher.toString()
                                        Log.d("TAG1", "success $response")
                                        try {
                                            var jsonString = state.data.cipher
                                            var decrypted: String = Cryptography_Android.Decrypt(jsonString, Constant.CRYPTKEY)
                                            val obj = JSONObject(decrypted)
                                            val gson = Gson()
                                            Log.d("decrypted", "success $obj")

                                            val citiesDetail: CitiesDetail = gson.fromJson(
                                                obj.toString(),
                                                CitiesDetail::class.java
                                            )
                                            citiesDetail.data.forEach { it->
                                                lifecycleScope.launch(Dispatchers.IO) {
                                                    val citiesTable = CitiesTable(it.id,it.cityName,it.divisionId,it.cityCode,it.status,it.createdAt,it.updatedAt)
                                                    appDao.addCities(citiesTable)
                                                }
                                            }
                                        }catch (e: IOException){
                                            e.printStackTrace()
                                        }
                                    }
                                }
                            }
                            is State.Empty -> {
                                citiesViewModel.booleanCities = false
                            }

                        }
                    }
                }
            }


    }
    private fun getAllTrains() {

        allTrainsViewModel.booleanAllTrains = false
        allTrainsViewModel.getAllTrains()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                allTrainsViewModel.postStateFlowAllTrains.collectLatest{ state->
                    when (state) {
                        is State.Loading -> {
                            showProgressBar(progressBarDialogRegister)
                        }
                        is State.Error -> {
                            citiesViewModel.booleanCities = false
                            hideProgressBar(progressBarDialogRegister)
                            when (state.code) {
                                404 -> {
                                    bottomToast("Invalid user name or Password")
                                }
                                401 -> {
                                    bottomToast(state.message)
                                }
                                405 -> {
                                    bottomToast(state.message)
                                }
                                500 -> {
                                    bottomToast("Something went wrong, Please try Again Later ")
                                }
                                600 ->{
                                    bottomToast("Check your internet connection and try Again Later ")
                                }
                                else->{
                                    bottomToast(state.code.toString())
                                }
                            }
                        }
                        is State.Success -> {

                            when (state.code) {
                                201 ->{
                                    hideProgressBar(progressBarDialogRegister)
                                    val response : String = state.data.cipher.toString()
                                    Log.d("TAG1", "success $response")
                                    try {
                                        var jsonString = state.data.cipher
                                        var decrypted: String = Cryptography_Android.Decrypt(jsonString, Constant.CRYPTKEY)
                                        val obj = JSONObject(decrypted)
                                        val gson = Gson()
                                        Log.d("decrypted", "success $obj")

                                        val allTrains:AllTrains = gson.fromJson(
                                            obj.toString(),
                                            AllTrains::class.java
                                        )
                                        allTrains.data.forEach { newData->
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                val allTrainsTable = AllTrainsTable(newData.id,newData.trainName,newData.trainCode,newData.direction,
                                                    newData.status,newData.createdAt,newData.updatedAt)
                                                appDao.addTrainsData(allTrainsTable)
                                            }

                                        }
                                    }catch (e: IOException){
                                        e.printStackTrace()
                                    }
                                }
                                200 -> {
                                   /* hideProgressBar(progressBarDialogRegister)
                                    val response : String = state.data.cipher.toString()
                                    Log.d("TAG1", "success $response")
                                    try {
                                        var jsonString = state.data.cipher
                                        var decrypted: String = Cryptography_Android.Decrypt(jsonString, Constant.CRYPTKEY)
                                        val obj = JSONObject(decrypted)
                                        val gson = Gson()
                                        Log.d("decrypted", "success $obj")

                                        val citiesDetail: CitiesDetail = gson.fromJson(
                                            obj.toString(),
                                            CitiesDetail::class.java
                                        )
                                        citiesDetail.data.forEach { it->
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                val citiesTable = CitiesTable(it.id,it.cityName,it.divisionId,it.cityCode,it.status,it.createdAt,it.updatedAt)
                                                appDao.addCities(citiesTable)
                                            }
                                        }
                                    }catch (e: IOException){
                                        e.printStackTrace()
                                    }*/
                                }
                            }
                        }
                        is State.Empty -> {
                            citiesViewModel.booleanCities = false
                        }

                    }
                }
            }
        }


    }

    private fun clickListener() {

        binding.backAero.setOnClickListener {
            onBackPressed()
        }

    }

    fun setHeader(string: String){
        binding.txtHeader.text = string

    }
    fun showBackIcon(){
        binding.backAero.visibility = View.VISIBLE
    }



    override fun onBackPressed() {
       // super.onBackPressed()

        val lastLoadedFragment = navController.currentDestination?.id
        if (lastLoadedFragment == dashBoardFragmentId){
            //finish()
            if (back_pressed + 1000 > System.currentTimeMillis()){
                super.onBackPressed()
            }
            else{
                Toast.makeText(getBaseContext(),
                    "Press once again to exit!", Toast.LENGTH_SHORT)
                    .show();
            }
            back_pressed = System.currentTimeMillis()
        }
        if (lastLoadedFragment == batchFragmentId){
            super.onBackPressed()
            setmenu(4)
        }
        if (lastLoadedFragment == profileFragmentId){
            super.onBackPressed()
            setmenu(4)
            //  setmenu(4)
        }
        if (lastLoadedFragment == historyFragmentId){
            super.onBackPressed()
            setmenu(4)
            //  setmenu(4)
        }
        if (lastLoadedFragment == outBoxFragmentId){
            super.onBackPressed()
            setmenu(4)
            //  setmenu(4)
        }
        if (lastLoadedFragment == newBookingFragmentId){
            super.onBackPressed()
            setmenu(4)
        }

        if (lastLoadedFragment == receiptFragmentId){
            super.onBackPressed()
            setmenu(4)
        }
        if (lastLoadedFragment == printReceiptFragmentId){
            super.onBackPressed()
            setmenu(4)
        }
        if (lastLoadedFragment == rePrintReceiptFragmentId){
            super.onBackPressed()
            setmenu(4)
        }
        if (lastLoadedFragment == editProfileFragmentId){
            super.onBackPressed()
            setmenu(2)
        }
    }

    private fun setmenu(i: Int) {
        binding.navView.onMenuItemClick(i)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("activeIndex", binding.navView.getSelectedIndex())
        super.onSaveInstanceState(outState)
    }
    private fun showDialogLog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.log_out_dailog_box)
        val yesBtn = dialog.findViewById(R.id.btnYes) as TextView
        val noBtn = dialog.findViewById(R.id.btnNo) as TextView
        yesBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss() }
        dialog.show()

    }

    private fun showDialogPrinter() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.printer_dialog)
        val yesBtn = dialog.findViewById(R.id.btnYes) as TextView
        val noBtn = dialog.findViewById(R.id.btnNo) as TextView
        yesBtn.setOnClickListener {
            if (isConnected) {
                myOpertion?.close()
                myOpertion = null
                mPrinter = null
            }else {
                reselConn()
            }
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss() }
        dialog.show()

    }

    private fun openConn() {
      myOpertion = BluetoothOperation(context, mHandler)
        (myOpertion as BluetoothOperation).btAutoConn(context, mHandler)
    }

 var myTask: MyTask? = null

     class MyTask : TimerTask() {
        override fun run() {
            val mPrinter: PrinterInstance? = null
            if (isConnected && mPrinter != null) {
                val by: ByteArray = mPrinter.read()
                if (by != null) {
                    println(mPrinter.isConnected().toString() + " read byte " + Arrays.toString(by))
                }
            }
        }
    }

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PrinterConstants.Connect.SUCCESS -> {
                    isConnected = true
                    mPrinter = myOpertion!!.getPrinter()
                    val timer = Timer()
                    myTask = MyTask()
                    timer.schedule(myTask, 0, 2000)
                    Toast.makeText(context, R.string.yesconn, Toast.LENGTH_SHORT).show()
                }
                PrinterConstants.Connect.FAILED -> {
                    if (myTask != null) {
                        myTask!!.cancel()
                    }
                    isConnected = false
                    Toast.makeText(context, R.string.conn_failed, Toast.LENGTH_SHORT).show()
                }
                PrinterConstants.Connect.CLOSED -> {
                    if (myTask != null) {
                        myTask!!.cancel()
                    }
                   isConnected = false
                    Toast.makeText(context, R.string.conn_closed, Toast.LENGTH_SHORT).show()
                }
                PrinterConstants.Connect.NODEVICE -> {
                    isConnected = false
                    Toast.makeText(context, R.string.conn_no, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
            context?.hideProgressBar(progressBarDialogRegister)
        }
    }




    private fun initView() {
        progressBarDialogRegister = ProgressBarDialog(this)
        progressBarDialogRegister?.setTitle("Connecting")
        progressBarDialogRegister?.setMessage("Please Wait...")
        initDialog()
    }

    private fun initDialog() {
        progressBarDialogRegister?.dismiss()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==  CONNECT_DEVICE && resultCode == Activity.RESULT_OK) {
            bt_mac = data?.extras!!.getString(BluetoothDeviceList.EXTRA_DEVICE_ADDRESS)
            bt_name = data?.extras!!.getString(BluetoothDeviceList.EXTRA_DEVICE_NAME)
         //   dialog.show()
            progressBarDialogRegister?.show()
            Thread { myOpertion?.open(data) }.start()
        } else if (requestCode == ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                myOpertion?.chooseDevice()
            } else {
                Toast.makeText(this, R.string.bt_not_enabled, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun connClicknew() {
        if (isConnected) {
            myOpertion?.close()
            myOpertion = null
            mPrinter = null
        } else {
            AlertDialog.Builder(context)
                .setTitle(R.string.str_message)
                .setMessage(R.string.str_connlast)
                .setPositiveButton(
                    R.string.yesconn
                ) { dialog, arg1 -> openConn() }
                .setNegativeButton(
                    R.string.str_resel
                ) { dialog, which -> reselConn() }
                .show()
        }
    }

    private fun reselConn() {
       myOpertion = BluetoothOperation(context, mHandler)
       (myOpertion as BluetoothOperation).chooseDevice()
    }

     fun printImageMain() {
        if (!isConnected && mPrinter == null) {
            return
        }
        object : Thread() {
            override fun run() {
               printImage(context!!.resources, mPrinter)
            }
        }.start()
    }
}