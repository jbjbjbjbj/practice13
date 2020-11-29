package com.example.jobsearch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vacancy (
    val id: String,
    val job_position: String,
    val description: String,
    val skills: String,
    val city: String,
    val salary: String,
    val company_name: String
):Parcelable {
    constructor(): this("", "", "", "","", "", "")
}