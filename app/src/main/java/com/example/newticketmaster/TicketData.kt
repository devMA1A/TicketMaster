package com.example.newticketmaster

data class TicketData(
    val _embedded: Embedded
)
data class Embedded(
    val events: List<Event>
)

data class Event(
    val name: String,
    val url: String,
    val image: List<Image>,
    val dates: Dates,
    val _embedded: Embed,
    val priceRanges: List<Prices>
)
data class Prices(
    val min: Double,
    val max: Double
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int
)

data class Dates(
    val start: Start
)

data class Start(
    val localDate: String,
    val localTime: String
)

data class Embed(
    val venues: List<venues>
)

data class venues(
    val name: String,
    val postalcode: String,
    val city: City,
    val state: State,
    val country: Country,
    val address: Address
)
data class City(
    val name: String

)

data class State(
    val name: String
)

data class Country(
    val countryCode: String
)

data class Address(
    val line1: String
)

