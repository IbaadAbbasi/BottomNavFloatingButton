package com.railway.ncs.model

data class GenerateTicketRequest(
    val QRCode: String,
    val ticketId : String,
    val roleId: Int,
    val userId: Int,
    val mobile: String,
    val cnic: String,
    val fromCity: Int,
    val toCity: Int,
    val trainClass: Int,
    val noOfPassenger: Int,
    val adult: Int,
    val child: Int,
    val totalFare: String,
    val dateAndTime: String,
    val trainId: Int,


    )
