package com.appentus.images_gallery.models

/*
* Model class created according to the response of the API.
* */
data class ImagesData(
    var author: String,
    var download_url: String,
    var height: Int,
    var id: String,
    var url: String,
    var width: Int
)