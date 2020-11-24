package com.besugos.marveluniverse.home.view.story

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.StoryModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class StoryViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameStoryCard)
    private val eventTxt = view.findViewById<TextView>(R.id.txtStoryCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarStoryCard)

    lateinit var storyModel: StoryModel

    fun bind(story: StoryModel) {
        storyModel = story
        name.text = storyModel.title
        eventTxt.text = context.getString(R.string.events_short_description, storyModel.id)
        Picasso.get()
            .load(R.drawable.img3)
            .transform(CropCircleTransformation())
            .into(avatar)
    }
}