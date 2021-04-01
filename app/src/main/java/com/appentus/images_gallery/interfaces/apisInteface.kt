package com.appentus.images_gallery.interfaces

import com.appentus.images_gallery.models.ImagesData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

public class apisInteface {
    var BASE_URL = "https://picsum.photos/"

    var imageService: TheImageDBInterface? = null

    /*
    * This function uses retrofit network call & assigns the operational work to The ImageDBInterface
    * */
    public fun getImages(): TheImageDBInterface? {
        if (imageService == null) {
            var retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            imageService = retrofit.create(TheImageDBInterface::class.java)

        }
        return imageService
    }

    interface TheImageDBInterface {

        /*
        * getImage() function represents the particular api call with the page parameter.
        * */
        @GET("v2/list?")
        fun getImage(@Query("page") page: Int): Call<ArrayList<ImagesData>>
    }

}