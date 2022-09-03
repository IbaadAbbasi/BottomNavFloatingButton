package com.kpk.eChallan.database_tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger


@Entity(tableName = "TicketsTable")
data class TicketsTable(
  //  @PrimaryKey(autoGenerate = true)  var ticketId: Int,
    val QrCode: String,
    val adult: Int,
    val child: Int,
    val cnic: String,
    val dateAndTime: String,
    val fromCity: Int,
    val mobile: String,
    val noOfPassenger: Int,
    val roleId: Int,
    val toCity: Int,
    val totalFare: String,
    val trainClass: Int,
    val trainId: Int,
    val userId: Int
){
    @PrimaryKey(autoGenerate = true)  var ticketId: Int=0
}