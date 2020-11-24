package com.besugos.marveluniverse.home.view.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.FavoriteModel
import com.besugos.marveluniverse.home.view.character.CharactersFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class FavoriteViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameFavoriteCard)
    private val favTxt = view.findViewById<TextView>(R.id.txtFavoriteCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarFavoriteCard)

    lateinit var favoriteModel: FavoriteModel

    fun bind(favorite: FavoriteModel) {
        favoriteModel = favorite
        name.text = favoriteModel.name
        favTxt.text = context.getString(R.string.favorites_short_description, favoriteModel.id)
        Picasso.get()
            .load(R.drawable.img4)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

    init {
        view.setOnClickListener {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.favorites_detail, null)
            val alertaDialog = BottomSheetDialog(context)


            layoutView.findViewById<TextView>(R.id.txtNameFavoritesDetails).text =
                favoriteModel.name


            val character = layoutView.findViewById<TextView>(R.id.txtCharacterFavoritesDetails)
            val event = layoutView.findViewById<TextView>(R.id.txtEventFavoritesDetails)
            val story = layoutView.findViewById<TextView>(R.id.txtEventStoriesDetails)
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
            event.setOnClickListener {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
            story.setOnClickListener {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }


}