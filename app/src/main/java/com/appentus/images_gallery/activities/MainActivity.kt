package com.appentus.images_gallery.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.images_gallery.Constants
import com.appentus.images_gallery.R
import com.appentus.images_gallery.adapters.ImagesAdapter
import com.appentus.images_gallery.interfaces.apisInteface
import com.appentus.images_gallery.models.ImagesData
import com.appentus.images_gallery.showToast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    /*
    * This is the main activity used to show data in recycler view
    * */

    lateinit var dataList: ArrayList<ImagesData>
    lateinit var adapter: ImagesAdapter
    var pageNo: Int = 1
    var pageLimit: Int = 30
    var isLoading: Boolean = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var layManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataList = ArrayList()

        layManager = GridLayoutManager(this@MainActivity, 2)

        imagesRV.layoutManager = layManager
        imagesRV.setHasFixedSize(true)

        if (Constants.isNetworkAvailable(applicationContext)) {
            mainProgressBar.visibility = View.VISIBLE
            getData(pageNo)
            addOnScrollRecyclerView()
        } else {
            applicationContext.showToast("No Internet Connection, Please try again")
        }
    }

    /*
    * This function is used to hit web service.
    * */
    fun getData(pNo: Int) {

        //Log.v("PageNoo", pNo.toString() + " adef")
        var getList: Call<ArrayList<ImagesData>>? = apisInteface().getImages()?.getImage(pNo)
        getList?.enqueue(object : Callback<ArrayList<ImagesData>> {
            override fun onFailure(call: Call<ArrayList<ImagesData>>, t: Throwable) {
                if (mainProgressBar.visibility == View.VISIBLE)
                    mainProgressBar.visibility = View.GONE

                if (bottomLoader.visibility == View.VISIBLE)
                    bottomLoader.visibility = View.GONE

                Log.v("DataValueeeee", t.message.toString() + " srga")
            }

            override fun onResponse(
                call: Call<ArrayList<ImagesData>>,
                response: Response<ArrayList<ImagesData>>
            ) {

                if (pageNo == 1)
                    dataList.isEmpty()

                isLoading = true

                if (mainProgressBar.visibility == View.VISIBLE)
                    mainProgressBar.visibility = View.GONE

                if (bottomLoader.visibility == View.VISIBLE)
                    bottomLoader.visibility = View.GONE

                response.body()?.let {

                    //if (response.body().size)
                    //  dataList.add(response.body()!!.get(0))

                    if (response.body() != null && response.body()?.size!! > 0) {

                        if (pageNo == 1)
                            dataList.clear()

                        for (i in 0..response.body()!!.size - 1) {
                            dataList.add(response.body()!!.get(i))
                        }

                        if (pageNo == 1) {
                            Log.v("PageCounttt", pageNo.toString() + " if")
                            setRecyclerView()
                        } else {
                            Log.v("PageCounttt", pageNo.toString() + " ielse")
                            // adapter = ImagesAdapter(this@MainActivity, dataList)
                            adapter.notifyDataSetChanged()
                        }

                    }
                }
            }
        })
    }

    /*
    * Implementation of code for pagination.
    * */
    fun addOnScrollRecyclerView() {
        imagesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { //check for scroll down

                    visibleItemCount = layManager.getChildCount()

                    totalItemCount = layManager.getItemCount()

                    pastVisiblesItems = layManager.findFirstVisibleItemPosition()

                    Log.v(
                        "ItemsCounttt",
                        "vC = " + visibleItemCount + " PVC = " + pastVisiblesItems + " TIC = " + totalItemCount
                    )

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (isLoading) {
                            pageNo = pageNo + 1
                            loadData(pageNo)
                            isLoading = false
                        }
                    }
                }
            }
        })
    }

    fun loadData(pageNo: Int) {

        if (Constants.isNetworkAvailable(applicationContext)) {
            bottomLoader.visibility = View.VISIBLE
            getData(pageNo)
        } else {
            applicationContext.showToast("No Internet Connection, Please try again")
        }

        /*if (isInitLoad) {
            (0..19).mapTo(movies) { Movie("Movie $it", it.toFloat()) }
            movieAdapter.submitList(movies)
            llLoader.visibility = View.GONE
        } else {
            handler.postDelayed(Runnable {
                val start = movies.size
                val end = start + 10
                val newMovies = ArrayList<Movie>()
                (start..end).mapTo(newMovies) { Movie("Movie $it", it.toFloat()) }
                updateDataList(newMovies)
                isLoading = false
                llLoader.visibility = View.GONE
            }, 2000)
        }*/
    }

/*
    fun updateDataList(newList: List<Movie>) {
        val tempList = movies.toMutableList()
        tempList.addAll(newList)
        movieAdapter.submitList(tempList)
        movies = tempList
    }
*/

    /*
    * This function is used to show data in recycler view.
    * */
    private fun setRecyclerView() {
        adapter = ImagesAdapter(this@MainActivity, dataList)
        imagesRV.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}