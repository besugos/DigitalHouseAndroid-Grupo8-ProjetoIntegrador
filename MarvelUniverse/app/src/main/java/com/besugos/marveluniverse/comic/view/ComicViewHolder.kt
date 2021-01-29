package com.besugos.marveluniverse.comic.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersEventsAdapter
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

const val IMG_RESOLUTION_SMALL = "standard_small"
const val IMG_RESOLUTION_FANTASTIC = "standard_fantastic"

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val _imgComicCard = view.findViewById<ImageView>(R.id.imgComicCard)
    private var _txtTitleComicCard = view.findViewById<TextView>(R.id.txtTitleComicCard)
    private val _txtDescriptionComicCard = view.findViewById<TextView>(R.id.txtDescriptionComicCard)

//    private lateinit var _comic: ComicModel
//    private lateinit var eventsAdapter: ComicEventsAdapter
//    private lateinit var charactersAdapter: ComicCharactersAdapter

    fun bind(comic: ComicModel) {

        _txtTitleComicCard.text = comic.title

        Picasso.get()
            .load(comic.thumbnail?.getThumb(IMG_RESOLUTION_SMALL))
            .transform(CropCircleTransformation())
            .into(_imgComicCard)

        if (comic.description.isNullOrEmpty()){
            _txtDescriptionComicCard.text  = itemView.context.getString(R.string.comic_description_not_found)
        } else {
            _txtDescriptionComicCard.text = comic.description
        }

    }


//    init {
//        view.setOnClickListener {
//
//            val inflater = itemView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layoutView = inflater.inflate(R.layout.comic_detail, null)
//            val alertDialog = BottomSheetDialog(itemView.context)
//
//            val txtTitleComicDetail = layoutView.findViewById<TextView>(R.id.txtTitleComicDetail)
//            val imgComicDetail = layoutView.findViewById<ImageView>(R.id.imgComicDetail)
//            val txtDescriptionComicDetail = layoutView.findViewById<TextView>(R.id.txtDescriptionComicDetail)
//
//            txtTitleComicDetail.text = _comic.title
//
//            Picasso.get()
//                .load(_comic.thumbnail?.getThumb(IMG_RESOLUTION_FANTASTIC))
//                .into(imgComicDetail)
//
//            val recyclerViewEvents = layoutView.findViewById<RecyclerView>(R.id.comicDetailsEventList)
//            val eventsManager = LinearLayoutManager(alertDialog.context, LinearLayoutManager.HORIZONTAL, false)
//
//            val listEvents = mutableListOf<EventSummaryModel>()
//            val eventsDetails = _comic.events?.items
//            eventsDetails?.forEach() {
//                listEvents.add(it)
//            }
//
//            eventsAdapter = ComicEventsAdapter(listEvents)
//
//            recyclerViewEvents?.apply {
//                setHasFixedSize(true)
//                layoutManager = eventsManager
//                adapter = eventsAdapter
//            }
//
//            val recyclerViewCharacters = layoutView.findViewById<RecyclerView>(R.id.comicDetailsEventList)
//            val charactersManager = LinearLayoutManager(alertDialog.context, LinearLayoutManager.HORIZONTAL, false)
//
//            val listCharacters = mutableListOf<CharacterSummaryModel>()
//            val charactersDetails = _comic.characters?.items
//            charactersDetails?.forEach() {
//                listCharacters.add(it)
//            }
//
//            charactersAdapter = ComicCharactersAdapter(listCharacters)
//
//            recyclerViewCharacters?.apply {
//                setHasFixedSize(true)
//                layoutManager = charactersManager
//                adapter = charactersAdapter
//            }
//
//            alertDialog.apply {
//                setContentView(layoutView)
//                show()
//            }
//
//
//
//            if (_comic.description.isNullOrEmpty()){
//                txtDescriptionComicDetail.text  = itemView.context.getString(R.string.comic_description_not_found)
//            } else {
//                txtDescriptionComicDetail.text = _comic.description
//            }
//
//        }
//    }

}