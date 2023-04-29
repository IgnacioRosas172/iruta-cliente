package com.psp.i_rutacliente.activities

import android.content.res.Resources
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.psp.i_rutacliente.R
import com.psp.i_rutacliente.databinding.ActivityTripInfoBinding

class tripInfoActivity : AppCompatActivity(), OnMapReadyCallback, Listener {

    private lateinit var binding: ActivityTripInfoBinding
    private var googleMap: GoogleMap? = null
    private var easyWayLocation: EasyWayLocation? = null
    private var extraOriginName = ""
    private var extraDestinationName = ""
    private var extraDestinationLat = 0.0
    private var extraDestinationLng = 0.0
    private var extraOriginLat = 0.0
    private var extraOriginLng = 0.0
    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        //extras
        extraOriginName = intent.getStringExtra("origin")!!
        extraDestinationName = intent.getStringExtra("destination")!!
        extraOriginLat = intent.getDoubleExtra("origin_lat", 0.0)
        extraOriginLng = intent.getDoubleExtra("origin_lng", 0.0)
        extraDestinationLat = intent.getDoubleExtra("destination_lat", 0.0)
        extraDestinationLng = intent.getDoubleExtra("destination_lng", 0.0)

        originLatLng = LatLng(extraOriginLat, extraOriginLng)
        destinationLatLng = LatLng(extraDestinationLat, extraDestinationLng)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
        }



        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = Priority.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 1f
        }

        easyWayLocation = EasyWayLocation(this, locationRequest, false, false, this)

        binding.textViewOrigin.text = extraOriginName
        binding.textViewDestination.text = extraDestinationName

        Log.d("Localizacion","Origin lat: ${originLatLng?.latitude}")
        Log.d("Localizacion","Origin lng: ${originLatLng?.longitude}")
        Log.d("Localizacion","Origin lat: ${destinationLatLng?.latitude}")
        Log.d("Localizacion","Origin lat: ${destinationLatLng?.longitude}")

        binding.imageViewBack.setOnClickListener { finish() }

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true

        try {
            val success = googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.style)
            )
            if (!success!!) {
                Log.d("MAPAS", "No se pudo encontrar el estilo")
            }

        } catch (e: Resources.NotFoundException) {
            Log.d("MAPAS", "Error: ${e.toString()}")
        }

    }

    override fun locationOn() {

    }

    override fun currentLocation(location: Location?) {

    }

    override fun locationCancelled() {

    }

    override fun onDestroy() { // CIERRA APLICACION O PASAMOS A OTRA ACTIVITY
        super.onDestroy()
        easyWayLocation?.endUpdates()
    }

}