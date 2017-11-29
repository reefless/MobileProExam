package com.egci428.qz0926

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_route_map.*

class routeView : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var latArray : ArrayList<Double>
    lateinit var lngArray : ArrayList<Double>
    var colorArray = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_view)

        latArray = intent.getSerializableExtra("lat") as ArrayList<Double>
        lngArray = intent.getSerializableExtra("long") as ArrayList<Double>
        colorArray = intent.getStringArrayListExtra("color")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(13.7934, 100.3225)
        var previousLatLng = sydney
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Mahidol"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
        var count = latArray.size
        Log.d("Array size", "${latArray.size}")
        var x = 0
        while(x<count){
            if(colorArray.get(x) == "Red") {
                mMap.addMarker(MarkerOptions().position(LatLng(latArray.get(x), lngArray.get(x))))
                val line = mMap.addPolyline(PolylineOptions()
                        .add(previousLatLng, LatLng(latArray.get(x), lngArray.get(x)))
                        .width(5f)
                        .color(Color.RED))
            }else{
                mMap.addMarker(MarkerOptions().position(LatLng(latArray.get(x), lngArray.get(x))))
                val line = mMap.addPolyline(PolylineOptions()
                        .add(previousLatLng, LatLng(latArray.get(x), lngArray.get(x)))
                        .width(5f)
                        .color(Color.GREEN))
            }
            previousLatLng = LatLng(latArray.get(x), lngArray.get(x))
            x++
        }
    }
}
