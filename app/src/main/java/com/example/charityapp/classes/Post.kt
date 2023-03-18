package com.example.charityapp.classes

import com.example.charityapp.R

class Post(
    var title : String = "",
    var category : String = "",
    var location : String = "",
    var amountGoal : Int=0,
    var amountReached : Int=0,
) {

    fun getAmountRemaining(): String {
        return "${amountGoal-amountReached} DA"
    }



}