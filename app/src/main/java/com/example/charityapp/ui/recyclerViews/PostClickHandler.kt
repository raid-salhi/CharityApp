package com.example.charityapp.ui.recyclerViews

import com.example.charityapp.classes.Post

interface PostClickHandler {
    fun clickedPostItem(post : Post)
}