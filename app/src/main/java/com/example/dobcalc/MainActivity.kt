package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var tvSelectedDate :TextView? = null        // var bcz it will be initialised later
    var tvAgeInMinutes :TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate=findViewById<TextView>(R.id.tvSelectedDate)      // initialised the above created var tvSelectedDate here
        tvAgeInMinutes = findViewById<TextView>(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener{

            clickDatePicker()       // will call the clickDatePicker function when the button is pressed
        }
    }

    private fun clickDatePicker()   // making this function private so that it can be accessed by this class only
    {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //variable storing datepickerdialog function
        val dpd = DatePickerDialog(this,
            /*DatePickerDialog.OnDateSetListener*/ /*<- NOT COMPULSORY TO WRITE*/ { view,SelectedYear,SelectedMonth,SelectedDayOfTheMonth -> //variables used to store the values selected from the calendar.

                Toast.makeText(this,"year is $SelectedYear month is ${SelectedMonth+1} and day is $SelectedDayOfTheMonth ", Toast.LENGTH_LONG).show()
                //   ^ this is what we selected           ^+1 bcz month is calculated starting from 0
                // from the calendar

                //Storing the selected year,month,day in a variable in day/month/year format as a string
                val selectedDate= "$SelectedDayOfTheMonth/${SelectedMonth+1}/$SelectedYear"
                // showing this format on the text view
                tvSelectedDate?.text = selectedDate     // here we have the data in a string format
                // to convert this date into minutes we first need to convert it into a particular date format
                //CONVERTING
                val sdf = SimpleDateFormat("dd/MM/yy",Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)   //using this theDate we will calculate how much minutes have passed/

                // checking that if only theDate is not empty then only calculate so that the app does crashes
                theDate?.let {
                    //calculating minutes
                    //creating a variable to store the minutes calculated from 1st jan 1970 till selected date
                    val selectedDateMinutes = theDate.time/60000     // .time will is a predefined function which gives total time but in milliseconds so dividing it by 1000 to get to seconds then by 60 to get to minutes.

                    //creating a variable to store the milliseconds calculated from 1st jan 1970 till very now
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    // checking that if only currentDate is not empty then only calculate so that the app does crashes
                    currentDate?.let {
                        // converting this millisecond into minutes
                        val currentDateMinutes = currentDate.time/60000

                        //taking the difference
                        val differenceInMinutes = currentDateMinutes - selectedDateMinutes  //this will give us how much minutes are from selected date and till now

                        //printing this calculated minutes on the text view
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        //    ^ ? mark bcz it is of type null
                    }
                }
            },
            year,
            month,
            day
        )
        //now to avoid the date to be selected from the future
        dpd.datePicker.maxDate = System.currentTimeMillis() -  86400000     // subtracting one day worth of milliseconds
        dpd.show()      // using show function only the created dialog will appear on the screen

    }
}