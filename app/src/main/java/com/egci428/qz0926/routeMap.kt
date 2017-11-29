package com.egci428.qz0926

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.egci428.qz0926.models.mapInfo

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_route_map.*

class routeMap : AppCompatActivity(), OnMapReadyCallback {
    lateinit var dataReference: DatabaseReference
    private lateinit var mMap: GoogleMap
    private var color = "Red"
    var latArray = ArrayList<Double>()
    var lngArray = ArrayList<Double>()
    var colorArray = ArrayList<String>()
    private var mapRoute = mapInfo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_map)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        addBtn.setOnClickListener {
            val intent = Intent(this, routeView::class.java)
            startActivity(intent)
        }
        colorBtn.setOnClickListener {
            if(color == "Red"){
                color = "Green"
                colorBtn.setBackgroundColor(Color.GREEN)
            }else{
                color = "Red"
                colorBtn.setBackgroundColor(Color.RED)
            }
        }
        addBtn.setOnClickListener {
            var routename = intent.getStringExtra("routename")
            var mapRoute = mapInfo(routename, latArray, lngArray , colorArray)
            dataReference = FirebaseDatabase.getInstance().getReference("UserInfo")
            val messageId = dataReference.push().key
            dataReference.child(messageId).setValue(mapRoute).addOnCompleteListener{
                Toast.makeText(this,"User added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

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
        latTextView.text = sydney.latitude.toString()
        lonTextview.text = sydney.longitude.toString()
        mMap.setOnMapLongClickListener {
            latLng -> mMap.addMarker(MarkerOptions().position(latLng).title(latLng.toString()))
            latArray.add(latLng.latitude.toDouble())
            lngArray.add(latLng.longitude.toDouble())
            colorArray.add(color)
            latTextView.text = latLng.latitude.toString()
            lonTextview.text = latLng.longitude.toString()
            if(color == "Red") {
                val line = mMap.addPolyline(PolylineOptions()
                        .add(previousLatLng, latLng)
                        .width(5f)
                        .color(Color.RED))
            }else{
                val line = mMap.addPolyline(PolylineOptions()
                        .add(previousLatLng, latLng)
                        .width(5f)
                        .color(Color.GREEN))
            }
            previousLatLng = latLng
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
