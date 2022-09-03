package com.itp.drivingtest.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

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
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
/*    @Inject
    lateinit var sharedPreferences: SharedPreferences*/

    private lateinit var manager: RecyclerView.LayoutManager
    lateinit var binding: FragmentHomeBinding

   // var progressBarDialogRegister: ProgressBarDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        init()
        clickListeners()

        return binding.root
    }

    private fun clickListeners() {

    }

    private fun init() {
     /*   progressBarDialogRegister = ProgressBarDialog(requireContext())
        navigationController = navigator.getNaveHostFragment(R.id.nav_main_container)*/

    }


    companion object {
        //        lateinit var userReportDetailResponse: UserRespModel
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }






}