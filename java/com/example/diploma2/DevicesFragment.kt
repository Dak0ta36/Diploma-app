package com.example.diploma2

import android.app.Instrumentation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma2.databinding.FragmentDeviceBinding
import android.bluetooth.BluetoothDevice
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import java.util.jar.Manifest

class DevicesFragment: Fragment(), DevicesAdapter.Callback {

    private var _binding: FragmentDeviceBinding? = null
    private val binding: FragmentDeviceBinding get() = _binding!!

    private val devicesAdapter = DevicesAdapter()

    private val viewModel: DevicesViewModel by viewModels {
        DeviceViewModelFactory((requireActivity().application as App).adapterProvider)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.devicesRecycler.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = devicesAdapter
        }

        devicesAdapter.addCallback(this)

        binding.fabStartScan.setOnClickListener{
            checkLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeOnViewModel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopscan()
    }

    private fun subscribeOnViewModel(){
        viewModel.devices.observe(viewLifecycleOwner, {
            devices -> devicesAdapter.update(devices)
        })
    }

    override fun onItemClick(device: BluetoothDevice) {
        Toast.makeText(requireContext(),"Connection to device ${device.address}", Toast.LENGTH_SHORT).show()
    }

    private val checkLocation = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        granted ->
        if (granted){
            viewModel.startScan()
        }
    }

    companion object{
        fun newInstance() = DevicesFragment()

    }
}