package com.example.simpleweatherapp.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CitiesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Cities"
    }
    val text: LiveData<String> = _text
}