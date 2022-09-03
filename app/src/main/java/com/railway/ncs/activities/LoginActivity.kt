package com.railway.ncs.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.pha.lahore.dialogs.ProgressBarDialog
import com.railway.ncs.extension.bottomToast
import com.railway.ncs.extension.hideProgressBar
import com.pha.lahore.extension.putAny
import com.railway.ncs.extension.showProgressBar
import com.pha.lahore.state.State
import com.railway.ncs.viewmodel.LoginViewModel
import com.railway.ncs.Encription.Cryptography_Android
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.Utils.Constant
import com.railway.ncs.databinding.ActivityLoginBinding
import com.railway.ncs.model.EncryptedRequest
import com.railway.ncs.model.LoginRequest
import com.railway.ncs.model.LoginResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    var progressBarDialogRegister: ProgressBarDialog? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        progressBarDialogRegister = ProgressBarDialog(this)
        clickListener()
    }

    private fun clickListener() {

        binding.btnSignIn.setOnClickListener {
            if (checkField()){
                Login(binding.etUserName.text.toString(),binding.etPassword.text.toString())
            }
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//            finish()
       /*  if (checkField()){

         }*/
        }
//        n : forget password to forget password screen
        binding.tvForgetPassword.setOnClickListener {
            val i = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(i)
            finish()

        }
    }
    private fun checkField():Boolean{
        var boolean :Boolean = false
        var userName = binding.etUserName.text?.trim().toString().replace(" ", "")
        var userPass = binding.etPassword.text?.trim().toString().replace(" ", "")
        if (userName == ""){


            binding.textInputLayoutUserName.error = null;
            binding.textInputLayoutUserName.isErrorEnabled = false;

            binding.textInputLayoutUserName.error = "Error Message";
            boolean= false
            return boolean
        }
        if (userPass == ""){
            val til =binding.textInputLayouts
            til.isErrorEnabled = true
            til.error = "You need to enter a pass"

            boolean = false
            return boolean
        }
        boolean= true
        return boolean
    }

    private fun Login(userName: String, password: String) {
      val login_request = LoginRequest()
        login_request.password=password
        login_request.userId=userName





      //  val json = Gson().toJson(login_request)
        val json = Gson().toJson(login_request)

        var reqeust = ""
        try {
//            reqeust=RSA.encryptRSAToString(json,Cryptography_Android.PublicKey)
//            reqeust=TestEncryptData(json).toString()
//            reqeust= TestEncryptData(json).toString()
            reqeust = Cryptography_Android.Encrypt(json, Constant.CRYPTKEY)
//            reqeust = Cryptography_Android.Encrypt(json, Cryptography_Android.PrivateKey)
//            reqeust = Cryptography_Android.EncryptWitPrivate(json, mKey,Cryptography_Android.PublicKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            reqeust = Cryptography_Android.Encrypt(json, Constant.CRYPTKEY)

            var testRes:String=""
        var test1:String ="04uYILaQTRWdER55WDIGYw=="
            testRes=Cryptography_Android.Decrypt(test1,Constant.CRYPTKEY)

            Log.d("TAG", "Decripted: "+testRes.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }



        val EncryptedRequest = EncryptedRequest()
//        loginEncryptedRequest.data?.cipher= reqeust
        EncryptedRequest.cipher= reqeust
       // EncryptedRequest.cipher= "PQUmlB45iJoqxq5RcOaCvHISYMJVc93Zetf/DiCe1K0wGdJrEArElhAAyb/uucXYZ7mEKB0sdE/nPsaX/njysw=="
//        loginEncryptedRequest.setText(reqeust)

//        var loginRequest: LoginEncryptedRequest = LoginEncryptedRequest()
        loginViewModel.booleanLogin = false
        loginViewModel.login(EncryptedRequest)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                loginViewModel.postStateFlowLogin.collectLatest{ state->
                    when (state) {
                        is State.Loading -> {
                            showProgressBar(progressBarDialogRegister)
                        }
                        is State.Error -> {
                            loginViewModel.booleanLogin = false
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
                            Log.d("TAG1", "success ${state.data}")
                            when (state.code) {
                                200 -> {
                                    hideProgressBar(progressBarDialogRegister)
                                    val response : String = state.data.cipher.toString()

                                    var loginResponse:LoginResponse
//                                    loginResponse=state.data.cipher.toString()


                                    //Response
//                                    val body: EncryptedResponse = response
                                    try {
                                        var jsonString = state.data.cipher
                                        var decrypted: String = Cryptography_Android.Decrypt(jsonString,Constant.CRYPTKEY)

//                                        JSONObject obj = new JSONObject(decrypted)

                                        val obj = JSONObject(decrypted)
                                        val gson = Gson()

                                        val loginResponse: LoginResponse = gson.fromJson(
                                            obj.toString(),
                                            LoginResponse::class.java
                                        )
                                        Log.d("TAG", "LoginDecrypted: "+loginResponse.toString())

                                        sharedPreferences.putAny("userName",loginResponse.data?.userName)
                                        sharedPreferences.putAny("userId",loginResponse.data?.id)
//                                        sharedPreferences.putAny("userPass",pass)
                                        sharedPreferences.putAny("First_Name",loginResponse.data?.firstName)
                                        sharedPreferences.putAny("Middle_Name",loginResponse.data?.middleName)
                                        sharedPreferences.putAny("Last_Name",loginResponse.data?.lastName)
                                        sharedPreferences.putAny("isLogin",true)
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)

                                    }catch (e:IOException){
                                        e.printStackTrace()
                                    }
//                                    sharedPreferences.putAny("userName",name)
//                                    sharedPreferences.putAny("userPass",pass)
                                    sharedPreferences.putAny("isLogin",true)
                                    sharedPreferences.putAny("userId",response)
                                    Log.d("Tag",response)


                                }
                            }
                        }
                        is State.Empty -> {
                            loginViewModel.booleanLogin = false
                        }
                        else -> {}
                    }
                }
            }
        }

    }

/*    fun TestEncryptData(dataToEncrypt: String?) {
        // generate a new public/private key pair to test with (note. you should only do this once and keep them!)
        val kp = RSA.getKeyPair()
        val publicKey = kp.public
        val publicKeyBytes = publicKey.encoded
        val publicKeyBytesBase64 = String(Base64.encode(publicKeyBytes, Base64.DEFAULT))
        val privateKey = kp.private
        val privateKeyBytes = privateKey.encoded
        val privateKeyBytesBase64 = String(Base64.encode(privateKeyBytes, Base64.DEFAULT))

        // test encryption
        val encrypted = RSA.encryptRSAToString(dataToEncrypt, publicKeyBytesBase64)

        // test decryption
        val decrypted = RSA.decryptRSAToString(encrypted, privateKeyBytesBase64)

        Log.d("TAG", "decrypted: "+decrypted)
    }*/
}