package com.mindorks.framework.whereismy.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mindorks.framework.whereismy.R
import com.mindorks.framework.whereismy.activities.ItemListActivity
import com.mindorks.framework.whereismy.activities.MainActivity
import com.mindorks.framework.whereismy.databinding.FragmentItemDetailsBinding
import com.mindorks.framework.whereismy.listeners.ItemDetailsFragmentOnClickListener
import com.mindorks.framework.whereismy.model.Item
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder

class ItemDetailsFragment(private val item:Item) :Fragment(), OnMapReadyCallback{

    private lateinit var itemDetailsBinding: FragmentItemDetailsBinding
    private lateinit var itemDetailsFragmentClickListener: ItemDetailsFragmentOnClickListener

    private lateinit var map : GoogleMap

    internal var number:String?=""

    private val itemsRepository: ItemDao by  lazy {
        ItemsDatabaseBuilder.getInstance().itemDao()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    : View? {
        itemDetailsBinding= FragmentItemDetailsBinding.inflate(
                inflater,
                container,
                false
        )

        arguments?.let {
            val item=it.getSerializable(KEY_ITEM) as Item
            itemDetailsBinding.itemName.text=item.name
            itemDetailsBinding.person.text=item.personName
            itemDetailsBinding.phoneNumber.text=item.phoneNumber.toString()
            itemDetailsBinding.date.text=item.date


        }

        itemDetailsBinding.deleteButton.setOnClickListener{
            itemsRepository.delete(item)
            itemDetailsFragmentClickListener.onDeleteButtonClick()
        }

        itemDetailsBinding.callButtom.setOnClickListener{
            number=itemDetailsBinding.phoneNumber.text.toString().trim()

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(number)))
            startActivity(intent)
        }


        val mapFragment = childFragmentManager.findFragmentById(R.id.gpsView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        itemDetailsBinding.backButton.setOnClickListener{
            goBack()
        }


        return itemDetailsBinding.root

    }

    private fun goBack() {
        requireActivity().run {
            startActivity(Intent(this, ItemListActivity::class.java))
            finish()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ItemDetailsFragmentOnClickListener){
            itemDetailsFragmentClickListener=context

        }
    }

    companion object{
        private const val KEY_ITEM="Item"
        const val TAG="Details item"

        fun create(item: Item): ItemDetailsFragment {
            val args = Bundle()
            args.putSerializable(KEY_ITEM, item)
            val fragment = ItemDetailsFragment(item)
            fragment.arguments = args
            return fragment
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        Log.e("test","hhhhh")

        val location = LatLng(item.latitude,item.longitude)
        map.addMarker(MarkerOptions().position(location).title(item.address))
        map.mapType=GoogleMap.MAP_TYPE_SATELLITE
        map.uiSettings.isZoomControlsEnabled=true
        val zoomLevel=16.0f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,zoomLevel))

    }

}