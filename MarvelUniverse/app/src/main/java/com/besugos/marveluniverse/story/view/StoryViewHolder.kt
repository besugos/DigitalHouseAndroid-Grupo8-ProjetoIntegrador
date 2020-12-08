package com.besugos.marveluniverse.story.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.story.model.StoryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
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


    init {
        view.setOnClickListener {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.stories_detail, null)
            val alertaDialog = BottomSheetDialog(context)

            layoutView.findViewById<TextView>(R.id.txtNameStoriesDetails).text =
                storyModel.title

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }
        }
    }

}