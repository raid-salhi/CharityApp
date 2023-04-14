package com.example.charityapp.classes

import com.example.charityapp.R

class Post(
    var title : String = "",
    var category : String = "",
    var location : String = "",
    var amountGoal : Int=1,
    var amountReached : Int=1,
    var subCategory: String= "",
    var description: String="",
) {

    fun getAmountReachedPer(): String {
        return "${amountReached*100/(amountGoal)} %"
    }
    fun getAmountGoalCash():String{
        return "$amountGoal DA"
    }
    fun getAmountGoalString():String{
        return "$amountGoal"
    }



}