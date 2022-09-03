package com.railway.ncs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.databinding.ActivityForgetPasswordBinding
import com.railway.ncs.databinding.ActivityLoginBinding

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window = window
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)


        clickListener()
    }
    private fun clickListener() {

        binding.btnSendEmail.setOnClickListener {
            val i = Intent(this, NewPasswordActivity::class.java)
            startActivity(i)
            finish()
            /*  if (checkField()){

              }*/
        }
    }


    }
