package com.railway.ncs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentDashBoardBinding
import com.railway.ncs.databinding.FragmentHistoryBinding

class DashBoardFragment : Fragment() {
    lateinit var binding: FragmentDashBoardBinding


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
//        return inflater.inflate(R.layout.fragment_dash_board, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dash_board, container, false)
        var header = " ڈیش بورڈ "
        (requireActivity() as MainActivity).setHeader(header)
         clickListenr()

        return binding.root
    }

    private fun clickListenr() {
        binding.txtAddNew.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_newBookingFragment)
        }

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashBoardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}