package com.mindorks.framework.whereismy.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.mindorks.framework.whereismy.databinding.ActivityMainBinding
import com.mindorks.framework.whereismy.databinding.ActivityNewItemBinding
import com.mindorks.framework.whereismy.model.Item
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder
import java.util.*
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import com.mindorks.framework.whereismy.*
import com.mindorks.framework.whereismy.adapters.NotificationUtils
import java.io.IOException
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date.from


class NewItemActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener{

    private var mNotified = false

    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var savedDay=0
    var savedMonth=0
    var savedYear=0
    var savedHour=0
    var savedMinute=0

    var lat =0.0
    var lon =0.0
    var address=""


    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val locationRequestCode=10
    private lateinit var locationManager : LocationManager

    private lateinit var binding : ActivityNewItemBinding

    private val itemsRepository: ItemDao by lazy{
        ItemsDatabaseBuilder.getInstance().itemDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= ActivityNewItemBinding.inflate(layoutInflater)
        binding.saveItemBtn.setOnClickListener{
            saveItem()
        }
        binding.buttonDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()

        }

        binding.trackLocationAction.setOnClickListener{ trackLocation()}
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContentView(binding.root)
    }

    private val locationListener =  object :LocationListener{

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) { }

        override fun onLocationChanged(location: Location) {
            updateLocationDisplay(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }

    }

    private fun updateLocationDisplay(location:Location?){
        if (location != null) {
            lat = location.latitude
        }
        if (location != null) {
            lon=location.longitude
        }
        binding.locationDisplay.text=""

        if(location!=null && Geocoder.isPresent()){
            val geocoder=Geocoder(this,Locale.getDefault());
            try{
                val nearByAddresses=geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1
                );
                if(nearByAddresses.size>0){
                    val stringBuilder=StringBuilder();
                    val nearestAddress=nearByAddresses[0];
                    address=nearestAddress.getAddressLine(0)
                    stringBuilder.append("\n")
                            .append(nearestAddress.getAddressLine(0))
                            .append("\n");
                    binding.locationDisplay.append(stringBuilder.toString())
                }
            }catch (e:IOException){
                e.printStackTrace();
            }
        }
    }

    private fun getDateTimeCalendar(){
        val cal:Calendar= Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    private fun saveItem(){
        val itemName=binding.etNewItemNameInput.text.toString()
        val person=binding.etNewItemPersonInput.text.toString()
        val date="$savedDay/$savedMonth/$savedYear"
        val phoneNumber=binding.etNewItemPhoneNumberInput.text.toString()


        val calendar : Calendar= Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,savedHour)
        calendar.set(Calendar.MINUTE,savedMinute)
        calendar.set(Calendar.YEAR,savedYear)
        calendar.set(Calendar.MONTH,savedMonth-1)
        calendar.set(Calendar.DAY_OF_MONTH,savedDay)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)


        if(itemName.isEmpty() || person.isEmpty() || date=="0/0/0" || phoneNumber.isEmpty() || lat==0.0 || lon==0.0){
            Toast.makeText(this,R.string.toasMessage,Toast.LENGTH_SHORT).show()

        }
        else {
            val item= Item(itemName,person,date,phoneNumber  ,lon,lat,address,0)
            itemsRepository.insert(item)

            if (!mNotified) {
                NotificationUtils().setNotification(calendar.getTimeInMillis(), itemName, this)
            }

            finish()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month+1
        savedYear=year
        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute=minute
        binding.tvTextDate.text="$savedDay/$savedMonth/$savedYear"
        binding.tvTextTime.text="$savedHour:$savedMinute"
    }

    private fun trackLocation(){
        if(hasPermissionCompact(locationPermission)){
            startTrackingLocation()
        }else{
            requestPermissionCompact(arrayOf(locationPermission),locationRequestCode)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {

        when(requestCode){
            locationRequestCode -> {
                if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    trackLocation()
                else
                    Toast.makeText(this, R.string.permissionNotGranted, Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startTrackingLocation() {
        Log.d("TAG", "Tracking location")

       val criteria:Criteria= Criteria()
        criteria.accuracy=Criteria.ACCURACY_FINE

        val provider = locationManager.getBestProvider(criteria,true)
        val minTime = 1000L
        val minDistance = 10.0F
        try{
            if (provider != null) {
                locationManager.requestLocationUpdates(provider,minTime,minDistance,locationListener)
            }
        }catch (e:SecurityException){
            Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

}