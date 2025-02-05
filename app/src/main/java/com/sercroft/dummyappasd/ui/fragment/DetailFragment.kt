package com.sercroft.dummyappasd.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.sercroft.dummyappasd.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonToTextDescription = gson.toJson(args)

        val imageView           = view.findViewById<ImageView>(R.id.detailImage)
        val titleView           = view.findViewById<TextView>(R.id.detailTitle)
        val descriptionView     = view.findViewById<TextView>(R.id.detailDescription)

        titleView.text          = args.postTitle
        descriptionView.text    = jsonToTextDescription

        Log.d("TAG-1", "UR;: ${args.postUrl}")

        Glide.with(this)
            .load(args.postUrl)
            .override(150, 150)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }
}