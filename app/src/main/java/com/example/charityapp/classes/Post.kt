package com.example.charityapp.classes

class Post(
    val title : String,
    val category : String,
    val location : String,
    val amountGoal : Int,
    val amountReached : Int,
    val iconCategory: Int
) {
    fun getAmountRemaining(): String {
        return "${amountGoal-amountReached} DA"
    }

}