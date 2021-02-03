package com.besugos.marveluniverse.character.model

import com.besugos.marveluniverse.home.model.EventListModel
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.home.model.StoryListModel
import com.besugos.marveluniverse.data.model.UrlModel
import com.besugos.marveluniverse.home.model.ComicListModel
import java.io.Serializable
import java.util.*

data class CharacterModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<UrlModel>?,
    val thumbnail: ImageModel?,
    val stories: StoryListModel?,
    val events: EventListModel?,
    val comics: ComicListModel?,
    var fav: Boolean = false
) : Serializable