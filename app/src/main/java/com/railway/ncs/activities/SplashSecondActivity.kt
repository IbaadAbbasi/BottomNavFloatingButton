package com.railway.ncs.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.databinding.ActivitySplashSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SplashSecondActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding : ActivitySplashSecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        //setContentView(R.layout.activity_splash_second)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = window
        window.statusBarColor = ContextCompat.getColor(this,R.color.white)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_second)
        clickListener()

    }

    private fun clickListener() {


        binding.txtStart.setOnClickListener {
            val login: Boolean = sharedPreferences.getBoolean("isLogin", false)
            if(login){
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
            }
        }
    }
}