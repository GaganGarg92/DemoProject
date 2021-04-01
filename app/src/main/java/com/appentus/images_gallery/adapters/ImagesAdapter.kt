package com.appentus.images_gallery.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.images_gallery.R
import com.appentus.images_gallery.models.ImagesData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.image_list_item.view.*


/*
* Adapter class used to set data in recycler view.
* */
class ImagesAdapter(val context: Activity, private val images: List<ImagesData>) :
    RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(imagesData: List<ImagesData>?, position: Int) {
            imagesData?.let {
                Log.v("ImagesDataaaa", images.get(position).download_url + " sdfvs")
                Glide.with(itemView.context).load(images.get(position).download_url)
                    .listener(object :
                        RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.progressBar.visibility = View.GONE
                            return false
                        }

                    })
                    .into(itemView.ivImage)
            }
        }
    }

    /*
    * It creates the view of each item in recycler view.
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context)
            .inflate(R.layout.image_list_item, parent, false)
        return MyViewHolder(view)
    }

    /*
    * It is used to notify the adapter to create the views accoding to the list size.
    * */

    override fun getItemCount(): Int {
        return images.size
    }


    /*
    * Used to bind data to each view.
    * */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(images, position)
    }

}