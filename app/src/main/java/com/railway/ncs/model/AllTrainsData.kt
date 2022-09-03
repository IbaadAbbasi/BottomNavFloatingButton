package com.railway.ncs.model

data class AllTrainsData(
    val createdAt: String,
    val direction: String,
    val id: Int,
    val status: Int,
    val trainCode: String,
    val trainName: String,
    val updatedAt: String
)