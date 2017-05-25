package com.appdockproject.appdock.Data

/**
 * Created by andrew on 9/23/16.
 */
class Answer {


    //Getters
    //Setters
    var age: String? = null
    var gender: String? = null
    var edu: String? = null
    var profession: String? = null
    var phone: String? = null
    var time: String? = null
    var rating: String? = null
    var use: String? = null
    private var latitude: String? = null
    private var longitude: String? = null

    fun setLocation(latitude: String, longitude: String) {
        this.latitude = latitude
        this.longitude = longitude
    }

    val location: String
        get() = "Latitude: $latitude Longitude: $longitude"
}
