package com.example.newticketmaster

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterService {
    //https://app.ticketmaster.com/discovery/v2/events.json?apikey=A4c8YMD71sAS9cK6holvMfhePbA8VPpG

    //Base = https://app.ticketmaster.com/discovery/v2/events.json?apikey=A4c8YMD71sAS9cK6holvMfhePbA8VPpG
    @GET("events.json")
    fun getEvents(@Query("keyword") category: String,
                  @Query("city") city: String,
                  @Query("sort") sort: String = "date,asc",
                  @Query("apikey") apikey: String): Call<TicketData>


}
