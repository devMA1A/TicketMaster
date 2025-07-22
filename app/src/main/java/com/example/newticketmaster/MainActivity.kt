package com.example.newticketmaster

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {

    private val TAG = "MainActivity"

    private var selectedCategory: String = ""


    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: ticketAdapter
    private val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"

    //events.json?apikey=A4c8YMD71sAS9cK6holvMfhePbA8VPpG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        recyclerView = findViewById(R.id.Recycler_view)
         myAdapter = ticketAdapter(emptyList())
         recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val myList = listOf(
            "Choose an event category", "Music",
            "Sports",
            "Theater",
            "Family",
            "Arts & Theater",
            "Concerts",
            "Comedy",
            "Dance"
        )
        val spinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myList)

        val eventSpinner = findViewById<Spinner>(R.id.spinner)

        eventSpinner.adapter = spinnerAdapter

        eventSpinner.onItemSelectedListener = this


    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(pos).toString()
        Log.d(TAG, "Selected category: $selectedCategory")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun searchButton( view: View) {


          val cityInput = findViewById<EditText>(R.id.cityEditText).text.toString().trim()

        if (view.id == R.id.button && (selectedCategory == "Choose an event category" && cityInput.isEmpty())) {

             // myAdapter.updateEvents(emptyList())
            //  recyclerView.visibility = View.GONE

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Missing Fields")
            builder.setMessage("Please insure to BOTH select a category and enter a city!")
            builder.create().show()
            return
        } else if (view.id == R.id.button && (selectedCategory == "Choose an event category" && cityInput.isNotEmpty())) {

            //  myAdapter.updateEvents(emptyList())
            //  recyclerView.visibility = View.GONE

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Missing Fields")
            builder.setMessage("Please insure to select a category!")
            builder.create().show()
            return
        } else if (view.id == R.id.button && (cityInput.isEmpty())) {

            //  myAdapter.updateEvents(emptyList())
            //  recyclerView.visibility = View.GONE

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Missing Fields")
            builder.setMessage("Please insure to type a City!")
            builder.create().show()
            return
        }


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val ticketAPI = retrofit.create(TicketMasterService::class.java)

        ticketAPI.getEvents(
            category = selectedCategory,
            city = cityInput.lowercase(),
            apikey = "A4c8YMD71sAS9cK6holvMfhePbA8VPpG"
        ).enqueue(object : Callback<TicketData>{

            override fun onResponse(call: Call<TicketData>, response: Response<TicketData>) {
                Log.d(TAG, "OnResponse: $response")

                val body = response.body()

                if (body == null){
                    Log.d(TAG, "valid response was NOT recieved")
                    return
                }
            }

            override fun onFailure(call: Call<TicketData>, t: Throwable) {
                Log.d(TAG, "OnFailure: $t")
            }
        })

    }

}