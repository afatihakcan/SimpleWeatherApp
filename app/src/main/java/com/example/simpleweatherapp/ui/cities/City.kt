package com.example.simpleweatherapp.ui.cities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City{
    @SerializedName("id") @Expose var id: Int?=null
    @SerializedName("name") @Expose var name: String?=null
    @SerializedName("state") @Expose var state: String?=null
    @SerializedName("country") @Expose var country: String?=null
    @SerializedName("coord") @Expose var coord: Coord?=null

    override fun toString(): String {
        return this.name.toString();
    }
}

class Coord {
    @SerializedName("lon") @Expose var lon: Double?=null
    @SerializedName("lat") @Expose var lat: Double?=null
}
