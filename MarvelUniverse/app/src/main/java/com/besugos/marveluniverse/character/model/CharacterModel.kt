package com.besugos.marveluniverse.character.model

import android.os.Parcelable
import com.besugos.marveluniverse.home.model.EventListModel
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.home.model.StoryListModel
import com.besugos.marveluniverse.data.model.UrlModel
import com.besugos.marveluniverse.home.model.ComicListModel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class CharacterModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val url: UrlModel?,
    val thumbnail: ImageModel?,
    val stories: StoryListModel?,
    val events: EventListModel?,
    val comics: ComicListModel?,
    var fav: Boolean = false
) : Parcelable