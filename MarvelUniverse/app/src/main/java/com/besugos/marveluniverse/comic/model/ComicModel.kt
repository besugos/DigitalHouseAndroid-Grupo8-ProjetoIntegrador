package com.besugos.marveluniverse.comic.model

import com.besugos.marveluniverse.home.model.CharacterListModel
import com.besugos.marveluniverse.home.model.EventListModel
import com.besugos.marveluniverse.home.model.ImageModel
import java.io.Serializable
import java.util.*

data class ComicModel (
    val id: Int?,
    val title: String?,
    val description: String?,
    val resourceURI: String?,
    val type: String?,
    val thumbnail: ImageModel?,
    val events: EventListModel?,
    val characters: CharacterListModel?
): Serializable