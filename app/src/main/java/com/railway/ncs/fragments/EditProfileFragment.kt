package com.railway.ncs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentEditProfileBinding
import com.railway.ncs.databinding.FragmentProfileBinding

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile, container, false)

        binding.btnSave.setOnClickListener {
            //printing...
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        return binding.root
    }


}