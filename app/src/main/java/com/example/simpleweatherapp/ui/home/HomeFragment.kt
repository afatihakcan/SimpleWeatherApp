package com.example.simpleweatherapp.ui.home

import HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleweatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var cityId: String;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewCityName: TextView = binding.textHomeCityName
        val textViewCurrentTemp: TextView = binding.textHomeCurrentTemp
        val textViewFeelsLike: TextView = binding.textHomeFeelsLike
        val textViewH : TextView = binding.textHomeH
        val textViewL : TextView = binding.textHomeL
        val textViewHumidity : TextView = binding.textHomeHumidity
        val textViewSpeed : TextView = binding.textHomeSpeed
        val textViewGust : TextView = binding.textHomeGust
        val textViewSeaLevel : TextView = binding.textHomeSeaLevel
        val textViewLat : TextView = binding.textHomeLat
        val textViewLon : TextView = binding.textHomeLon
        val textViewDesc : TextView = binding.textHomeDesc

        homeViewModel.textCityName.observe(viewLifecycleOwner) {
            textViewCityName.text = it
        }
        homeViewModel.textCurrentTemp.observe(viewLifecycleOwner) {
            textViewCurrentTemp.text = it
        }
        homeViewModel.textFeelsLike.observe(viewLifecycleOwner) {
            textViewFeelsLike.text = it
        }
        homeViewModel.textH.observe(viewLifecycleOwner) {
            textViewH.text = it
        }
        homeViewModel.textL.observe(viewLifecycleOwner) {
            textViewL.text = it
        }
        homeViewModel.textHumidity.observe(viewLifecycleOwner) {
            textViewHumidity.text = it
        }
        homeViewModel.textSpeed.observe(viewLifecycleOwner) {
            textViewSpeed.text = it
        }
        homeViewModel.textGust.observe(viewLifecycleOwner) {
            textViewGust.text = it
        }
        homeViewModel.textSeaLevel.observe(viewLifecycleOwner) {
            textViewSeaLevel.text = it
        }
        homeViewModel.textLat.observe(viewLifecycleOwner) {
            textViewLat.text = it
        }
        homeViewModel.textLon.observe(viewLifecycleOwner) {
            textViewLon.text = it
        }
        homeViewModel.textDesc.observe(viewLifecycleOwner) {
            textViewDesc.text = it
        }


        if(requireArguments().getString("cityId") != null){
            cityId = requireArguments().getString("cityId").toString()
        }else{
            cityId = "745044"
        }

        homeViewModel.fetchDataForCity(cityId)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}