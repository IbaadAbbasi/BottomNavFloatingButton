package com.railway.ncs.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.railway.ncs.MainActivity
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentBatchBinding


class BatchFragment : Fragment() {
      lateinit var binding:FragmentBatchBinding

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
//        return inflater.inflate(R.layout.fragment_batch, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_batch, container, false)
       // (requireActivity() as MainActivity).showBackIcon()
        return binding.root
    }

    companion object {


        fun newInstance(param1: String, param2: String) =
            BatchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}