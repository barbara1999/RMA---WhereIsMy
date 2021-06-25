package com.mindorks.framework.whereismy.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mindorks.framework.whereismy.R
import com.mindorks.framework.whereismy.databinding.ActivityMainBinding
import com.mindorks.framework.whereismy.databinding.ActivityMapBinding
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder

class MapActivity  : AppCompatActivity(), OnMapReadyCallback {

   private lateinit var mapBinding: ActivityMapBinding

    private lateinit var map: GoogleMap

    private val itemsRepository: ItemDao by lazy {
        ItemsDatabaseBuilder.getInstance().itemDao()
    }

    val items = itemsRepository.getItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        mapBinding.backButton.setOnClickListener{ goBack()}
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setContentView(mapBinding.root)

    }

    private fun goBack(){
        val addNewIntent = Intent(this, MainActivity::class.java)
        startActivity(addNewIntent)
    }

    override fun onMapReady(googleMap: GoogleMap) {


        map = googleMap


        for (i in items){
            val item = LatLng(i.latitude,i.longitude)
            map.addMarker(MarkerOptions().position(item).title(i.name))
            map.mapType=GoogleMap.MAP_TYPE_SATELLITE
            map.uiSettings.isZoomControlsEnabled=true
            val zoomLevel=17.0f
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(item,zoomLevel))
        }

    }
}