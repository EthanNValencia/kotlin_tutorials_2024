package com.nt.dobcalc_014

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvDateInMinutes: TextView? = null
    private var tvDateInHours: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvDateInMinutes = findViewById(R.id.tvDateInMinutes)
        tvDateInHours = findViewById(R.id.tvDateInHours)
        btnDatePicker.setOnClickListener {
            onDatePickerClick()
        }
    }

    private fun onDatePickerClick() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
             Log.d("Ethan", "User selected  ${month + 1}-$dayOfMonth-$year.")
            val selectedDateString = "${month + 1}/$dayOfMonth/$year"
            val format = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            val selectedDate = format.parse(selectedDateString) // selectedDate.time / 60000
            val selectedDateInMinutes = (selectedDate?.time ?: 1L) / 60000
            val currentDate = format.parse(format.format(System.currentTimeMillis()))
            val differenceInMinutes = (currentDate?.time ?: 1L) / 60000

            tvSelectedDate?.text = selectedDateString
            tvDateInMinutes?.text = (differenceInMinutes - selectedDateInMinutes).toString()
            tvDateInHours?.text = ((differenceInMinutes - selectedDateInMinutes) / 60).toString()
            // Toast.makeText(this, "In DatePickerDialog!", Toast.LENGTH_LONG).show()
        },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()

        // Toast.makeText(this, "The button date picker was clicked!", Toast.LENGTH_LONG).show()
    }
}

