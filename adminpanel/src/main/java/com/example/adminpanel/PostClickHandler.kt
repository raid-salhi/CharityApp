package com.example.adminpanel

import com.example.adminpanel.classes.Post

interface PostClickHandler {
    fun clickedPostItem(post : Post)
    fun deletePostItem(post : Post)
    fun callAction(post: Post)
    fun editPostItem(post: Post)
     fun acceptPostItem(post: Post)
}