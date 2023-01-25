package com.example.albertsons.model

import com.google.gson.annotations.SerializedName

data class CatFactInfo(
    @SerializedName("data")
    val catFact: List<String>
)