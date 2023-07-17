package com.example.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.maps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


import com.google.android.gms.maps.model.PolylineOptions

private const val LOCATION_PERMISSION_REQUEST_CODE = 2000
private const val DEFAULT_MAP_SCALE = 13.0f

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val hoteles_Misantla = mutableListOf<Hoteles>()
    private lateinit var hotelIcon: BitmapDescriptor
    private val userLocation = Location("")

    //private lateinit var btnLH: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hoteles_Misantla.add(Hoteles("Hotel 5 de mayo", 19.93213052, -96.85067513))
        hoteles_Misantla.add(Hoteles("Hotel Claudia Isabel", 19.9313562, -96.85292508))
        hoteles_Misantla.add(Hoteles("Hotel Delfos", 19.92919286, -96.85367228))
        hoteles_Misantla.add(Hoteles("Hotel Don Pablo", 19.92949798, -96.8528596))
        hoteles_Misantla.add(Hoteles("Hotel el Dorado Amanecer", 19.93553854, -96.85044053))
        hoteles_Misantla.add(Hoteles("Hotel Fidelidad", 19.929416, -96.8577458))
        hoteles_Misantla.add(Hoteles("Hotel Gilbón", 19.93169157, -96.85388367))
        hoteles_Misantla.add(Hoteles("Hotel Imperial", 19.92784958, -96.85340741))
        hoteles_Misantla.add(Hoteles("Hotel Leon", 19.93107407, -96.85234839))
        hoteles_Misantla.add(Hoteles("Hotel Misantla", 19.93522701, -96.84914283))
        hoteles_Misantla.add(Hoteles("Hotel Posada del Ángel", 19.92933054, -96.85677342))
        hoteles_Misantla.add(Hoteles("Hotel Sol", 19.9311131, -96.8505514))
        hoteles_Misantla.add(Hoteles("Auto Hotel Cielo", 19.93311668, -96.85415903))
        hoteles_Misantla.add(Hoteles("Auto Hotel Delicias", 19.93041358, -96.8555852))
        hoteles_Misantla.add(Hoteles("Auto Hotel Romances", 19.93454911, -96.85495609))
        hoteles_Misantla.add(Hoteles("Auto Hotel Tentaciones", 19.91939935, -96.85760005))
        hoteles_Misantla.add(Hoteles("Posada Hotel Claudia Isabel", 19.93125492, -96.85271314))

        hotelIcon = getHotelIcon()
        checkLocationPermission()

    }

    fun mostrarHoteles(view:View) {
        val intent = Intent(this, DatosHoteles::class.java)
        startActivity(intent)
    }

    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            getUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
                setupMap()
            }
        }
    }

    private fun setupMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getUserLocation()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showLocationPermissionRationaleDialog()
            } else {
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLocationPermissionRationaleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.need_location_permission_dialog_title)
            .setMessage(R.string.need_location_permission_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }.setNegativeButton(R.string.no) { _, _ ->
                finish()
            }
        dialog.show()
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /*private val geoApiContext: GeoApiContext by lazy {
        GeoApiContext.Builder().apiKey(getString(R.string.AIzaSyB4VyRaTM595S8oLL4NOUWEH6TBUNcWZpg)).build()
    }*/


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val userLatLng = LatLng(userLocation.latitude, userLocation.longitude)
        val userMarker = MarkerOptions().position(userLatLng)
        mMap.addMarker(userMarker)

        for (hoteleria in hoteles_Misantla){
            val hotelPosition = LatLng(hoteleria.latitud, hoteleria.longitud)
            val hotelLocation = Location("")
            hotelLocation.latitude = hoteleria.latitud
            hotelLocation.longitude = hoteleria.longitud
            val distanceToHotel = hotelLocation.distanceTo(userLocation)

            val hotelMarkerOptions = MarkerOptions()
                .icon(hotelIcon)
                .position(hotelPosition)
                .title(hoteleria.name)
                .snippet(getString(R.string.distance_to_format, distanceToHotel))
            mMap.addMarker(hotelMarkerOptions)

            //fetchAndDrawRoute(userLocation.latitude, userLocation.longitude, hotelPosition)

           /* val polylineOptions = PolylineOptions()
                .add(LatLng(userLocation.latitude, userLocation.longitude))
                .add(hotelPosition)
                .width(5f)
                .color(Color.RED)

            mMap.addPolyline(polylineOptions)*/
        }
        // Add a marker in Sydney and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, DEFAULT_MAP_SCALE))
    }

    /*private fun fetchAndDrawRoute(originLat: Double, originLng: Double, destination: LatLng) {
        val origin = "$originLat,$originLng"
        val destinationStr = "${destination.latitude},${destination.longitude}"

        val directionsResult: DirectionsResult? = DirectionsApi.newRequest(geoApiContext)
            .origin(origin)
            .destination(destinationStr)
            .await()

        directionsResult?.routes?.let { routes ->
            for (route in routes) {
                val polylineOptions = PolylineOptions()
                val points = route.overviewPolyline.decodePath()
                for (point in points) {
                    polylineOptions.add(LatLng(point.lat, point.lng))
                }
                polylineOptions.width(5f) // Set the width of the line
                polylineOptions.color(Color.RED) // Set the color of the line (you can change it to any color)
                mMap.addPolyline(polylineOptions)
            }
        }
    }*/



    private fun getHotelIcon(): BitmapDescriptor {
        val drawable = ContextCompat.getDrawable(this, R.drawable.hotel)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0,
            drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}