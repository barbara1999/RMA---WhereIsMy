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

class NewItemActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener{

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

        setContentView(binding.root)

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

        val item= Item(itemName,person,date, phoneNumber.toInt() ,1,1,0)
        itemsRepository.insert(item)
        finish()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month
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


}