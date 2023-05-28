package com.example.simpleweatherapp.ui.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.FragmentCitiesBinding
import com.google.gson.Gson
import java.io.InputStream


class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val citiesViewModel =
            ViewModelProvider(this).get(CitiesViewModel::class.java)

        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCities
        citiesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        val jsonString = loadJson(requireContext())
        val cities = Gson().fromJson(jsonString, Array<City>::class.java)
        val cityNames = cities.map { it.name?.lowercase() }.toTypedArray()

        val citiesAdapter : ArrayAdapter<City> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cities)
        binding.listViewCities.adapter = citiesAdapter

        binding.searchViewCities.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchViewCities.clearFocus()
                if(cityNames.contains(p0?.lowercase())){
                    citiesAdapter.filter.filter(p0)
                }
                else if(p0.isNullOrBlank()) {
                    citiesAdapter.filter.filter("")
                }


                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(cityNames.contains(p0?.lowercase())){
                    citiesAdapter.filter.filter(p0)
                }else if (p0.isNullOrBlank()) {
                    citiesAdapter.filter.filter("")
                }

                return false
            }

        })

        binding.listViewCities.setOnItemClickListener() { adapterView, view, position, id ->
            val city = adapterView.getItemAtPosition(position) as City
            findNavController().navigate(R.id.action_navigation_cities_to_navigation_home, Bundle().apply {
                putString("cityId", city.id.toString())
            })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadJson(context: Context): String? {
        var input: InputStream? = null
        var jsonString: String

        try {
            // Create InputStream
            input = context.assets.open("city.list.json")

            val size = input.available()

            // Create a buffer with the size
            val buffer = ByteArray(size)

            // Read data from InputStream into the Buffer
            input.read(buffer)

            // Create a json String
            jsonString = String(buffer)
            return jsonString;
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            // Must close the stream
            input?.close()
        }

        return null
    }

}