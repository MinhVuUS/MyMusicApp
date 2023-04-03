//package com.example.myapplication.other
//
//import android.content.Context
//import android.widget.ImageView
//import androidx.databinding.BindingAdapter
//import androidx.swiperefreshlayout.widget.CircularProgressDrawable
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.example.myapplication.R
//import com.google.android.exoplayer2.offline.DownloadService.start
//
////class GlideUtil {
////}
//fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
//
//    val options = RequestOptions()
//        .placeholder(progressDrawable)
//        .error(R.mipmap.ic_launcher_round)
//
//    Glide.with(context)
//        .setDefaultRequestOptions(options)
//        .load(url)
//        .into(this)
//
//}
//
//fun placeholderProgressBar(context: Context): CircularProgressDrawable {
//    return CircularProgressDrawable(context).apply {
//        strokeWidth = 8f
//        centerRadius = 40f
//        start()
//    }
//}
//
//@BindingAdapter("android:downloadUrl")
//fun downloadImage(view: ImageView, url: String?) {
//    view.downloadFromUrl(url, placeholderProgressBar(view.context))
//}