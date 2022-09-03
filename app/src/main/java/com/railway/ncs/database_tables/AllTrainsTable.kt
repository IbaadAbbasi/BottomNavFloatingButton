package com.railway.ncs.database_tables

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "AllTrainsTable")
data class AllTrainsTable(
    @PrimaryKey(autoGenerate = true)   val id: Int,
    val trainName: String,
    val trainCode: String,
    val direction: String,
    val status: Int,
    val createdAt: String,
    val updatedAt: String
)/*{
    @PrimaryKey(autoGenerate = true)  var primaryId: Int = 0
}*/

