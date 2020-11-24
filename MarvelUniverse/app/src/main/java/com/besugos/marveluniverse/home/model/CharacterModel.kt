package com.besugos.marveluniverse.home.model

import java.io.Serializable
import java.util.*

data class CharacterModel (
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val url: UrlModel?,
    val thumbnail: ImageModel?,
    val stories: StoryListModel?,
    val events: EventListModel?
): Serializable