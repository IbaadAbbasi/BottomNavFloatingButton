package com.kpk.eChallan.database_tables

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CitiesTable")
data class CitiesTable(
    @PrimaryKey(autoGenerate = false)  val id: Int,
   val cityName: String,
    val divisionId: Int,
    val cityCode: String,
    val status: Int,
    val createdAt: String,
    val updatedAt: String
)/*{
    @PrimaryKey(autoGenerate = true)  var primaryid: Int = 0
}*/

