package com.example.jobsearch

data class Favorites (
    val uid: String,
    val vacancy_id: String
) {
    constructor(): this("", "")
}