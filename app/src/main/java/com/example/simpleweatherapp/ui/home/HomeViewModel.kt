import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleweatherapp.models.WeatherData
import com.example.simpleweatherapp.services.WeatherApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val _textCityName = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textCurrentTemp = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textFeelsLike = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textH = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textL = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textHumidity = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textSpeed = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textGust = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textSeaLevel = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textLat = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textLon = MutableLiveData<String>().apply {
        value = "Loading..."
    }
    private val _textDesc = MutableLiveData<String>().apply {
        value = "Loading..."
    }

    val textCityName: LiveData<String> = _textCityName
    val textCurrentTemp: LiveData<String> = _textCurrentTemp
    val textFeelsLike: LiveData<String> = _textFeelsLike
    val textH: LiveData<String> = _textH
    val textL: LiveData<String> = _textL
    val textHumidity: LiveData<String> = _textHumidity
    val textSpeed: LiveData<String> = _textSpeed
    val textGust: LiveData<String> = _textGust
    val textSeaLevel: LiveData<String> = _textSeaLevel
    val textLat: LiveData<String> = _textLat
    val textLon: LiveData<String> = _textLon
    val textDesc: LiveData<String> = _textDesc


    private val apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }

    fun fetchDataForCity(cityId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getWeatherData(
                    cityId,
                    "2f1ab48028539d0ade49625775da39cc",
                    "metric"
                )

                if (response.isSuccessful) {
                    val weatherData = response.body()
                    Log.d("HomeViewModel", "API request successful: $weatherData")
                    updateLiveData(weatherData)
                } else {
                    handleApiError(response.message())
                }
            } catch (e: Exception) {
                handleNetworkError(e.message)
            }
        }
    }

    private fun updateLiveData(weatherData: WeatherData?) {
        weatherData?.let {
            _textCityName.value = "${weatherData.name}"
            _textCurrentTemp.value = "Current:  ${weatherData.main.temp}"
            _textFeelsLike.value = "Feels Like: ${weatherData.main.feels_like}"
            _textH.value = "H: ${weatherData.main.temp_max}"
            _textL.value = "L: ${weatherData.main.temp_min}"
            _textHumidity.value = "%${weatherData.main.humidity}"
            _textSpeed.value = "Speed: ${weatherData.wind.speed} km/h"
            _textGust.value = "Gust: ${weatherData.wind.deg} km/h"
            _textSeaLevel.value = "${weatherData.main.pressure}"
            _textLat.value = "Latitude: ${weatherData.coord.lat}"
            _textLon.value = "Longitude: ${weatherData.coord.lon}"
            _textDesc.value = "${weatherData.weather[0].description}"
        }
    }

    private fun handleApiError(errorMessage: String) {
        Log.e("HomeViewModel", "API request failed: $errorMessage")
        // Handle API error if needed
    }

    private fun handleNetworkError(errorMessage: String?) {
        Log.e("HomeViewModel", "API request failed: $errorMessage")
        // Handle network error if needed
    }
}
