package com.besugos.marveluniverse.character.model

import com.besugos.marveluniverse.home.model.EventListModel
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.home.model.StoryListModel
import com.besugos.marveluniverse.data.model.UrlModel
import java.io.Serializable
import java.util.*

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
    var fav: Boolean = false
) : Serializable