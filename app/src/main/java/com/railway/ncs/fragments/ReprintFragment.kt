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
import com.railway.ncs.databinding.FragmentReprintBinding


class ReprintFragment : Fragment() {
    lateinit var binding:FragmentReprintBinding


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
     //   return inflater.inflate(R.layout.fragment_reprint, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reprint, container, false)
        var header = " رسید "
        (requireActivity() as MainActivity).setHeader(header)
        clickListener()
        return binding.root
    }

    private fun clickListener() {
        binding.txtPrintAgain.setOnClickListener {
            //printing...
            findNavController().navigate(R.id.action_reprintFragment_to_dashBoardFragment)
        }
        binding.txtcancel.setOnClickListener {
            findNavController().navigate(R.id.action_reprintFragment_to_dashBoardFragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReprintFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}