package com.railway.ncs.fragments

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import com.railway.ncs.R
import com.railway.ncs.databinding.FragmentBatchBinding
import com.railway.ncs.databinding.FragmentHistoryBinding
import com.railway.ncs.databinding.FragmentHomeBinding


class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding


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
//        return inflater.inflate(R.layout.fragment_history, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false)


        val etByTrain = binding.etByTrain
        val etByTrainArray: ArrayList<String> = ArrayList()
        etByTrainArray.add("G.L.I.")
        etByTrainArray.add("S.K.")
        etByTrainArray.add("A.E.")
        etByTrainArray.add("A.P.")
        val etByTrainList: ArrayList<String> = etByTrainArray
        val textViewByTrain = etByTrain as AutoCompleteTextView
        val adapterByTrain = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, etByTrainList)
        textViewByTrain.threshold = 1
        val filtersListByTrain = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(40))
        textViewByTrain.filters = filtersListByTrain
        textViewByTrain.setAdapter(adapterByTrain)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}