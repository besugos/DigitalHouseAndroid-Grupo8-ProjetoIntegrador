package com.besugos.marveluniverse.home.view.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.EventModel
import com.besugos.marveluniverse.home.view.character.CharactersFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class EventViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameEventCard)
    private val eventTxt = view.findViewById<TextView>(R.id.txtEventCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarEventCard)

    lateinit var eventModel: EventModel

    fun bind(event: EventModel) {
        eventModel = event
        name.text = eventModel.title
        eventTxt.text = context.getString(R.string.events_short_description, eventModel.id)
        Picasso.get()
            .load(R.drawable.img2)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

    init {
        view.setOnClickListener {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.events_detail, null)
            val alertaDialog = BottomSheetDialog(context)


            layoutView.findViewById<TextView>(R.id.txtNameEventrDetails).text =
                eventModel.title

            val character = layoutView.findViewById<TextView>(R.id.txtCharacterEventDetails)
            val story = layoutView.findViewById<TextView>(R.id.txtStoryEventDetails)
//            Picasso.get().load(R.drawable.img1).into(layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails))

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }

            character.setOnClickListener {
                alertaDialog.dismiss()
                val activity = view.context as AppCompatActivity
                val myFragment: Fragment = CharactersFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, myFragment).addToBackStack(null).commit()

//                activity.supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, CollectionFragment()).commit()

            }
            story.setOnClickListener {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
