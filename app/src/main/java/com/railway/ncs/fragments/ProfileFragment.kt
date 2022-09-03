package com.railway.ncs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
   private lateinit var binding: FragmentProfileBinding
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
       // return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.ivEdit.setOnClickListener {
            //printing...
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}