package com.besugos.marveluniverse.comic.model

import android.os.Parcelable
import com.besugos.marveluniverse.home.model.CharacterListModel
import com.besugos.marveluniverse.home.model.EventListModel
import com.besugos.marveluniverse.data.model.ImageModel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ComicModel (
    val id: Int?,
    val title: String?,
    val description: String?,
    val resourceURI: String?,
    val type: String?,
    val thumbnail: ImageModel?,
    val events: EventListModel?,
    val characters: CharacterListModel?
): Parcelable