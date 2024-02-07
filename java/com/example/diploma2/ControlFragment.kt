package com.example.diploma2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.diploma2.databinding.FragmentControlBinding
import okhttp3.Request

class ControlFragment : Fragment() {

    private var _binding: FragmentControlBinding? = null
    private val binding: FragmentControlBinding get() = _binding!!


    private val viewModel: ControlViewModel by viewModels{
        ControlViewModelFactory((requireActivity().application as App).adapterProvider)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.startscan.setOnClickListener {
            viewModel.StartCollecting(Rtypes.Startscan)
        }


        binding.stopscan.setOnClickListener {
            viewModel.endCollecting(Rtypes.Stopscan)
        }

        binding.setfreq.setOnClickListener {
            viewModel.SetFreq(Rtypes.SetFreq)
        }

        binding.calibrate.setOnClickListener {
            viewModel.Calibrate(Rtypes.Calibrate)
        }

        binding.getdata.setOnClickListener {
            viewModel.Getdata(Rtypes.Download)
        }



1    }


    override fun onStop() {
        super.onStop()
        viewModel.disconnect()
    }
    override fun onResume() {
        super.onResume()
        val deviceAddress = requireArguments().getString(KEY_DEVICE_ADDRESS)!!
        viewModel.connect(deviceAddress)
    }


    companion object {
        private const val KEY_DEVICE_ADDRESS = "key device address"
        @JvmStatic
        fun newInstance(deviceAddress: String) = ControlFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_DEVICE_ADDRESS, deviceAddress)
                }
            }
    }
}


