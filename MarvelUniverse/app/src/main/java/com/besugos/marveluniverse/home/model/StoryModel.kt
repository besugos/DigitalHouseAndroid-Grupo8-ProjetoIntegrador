package com.besugos.marveluniverse.home.model

import java.io.Serializable
import java.util.*

data class StoryModel (
    val id: Int?,
    val title: String?,
    val description: String?,
    val resourceURI: String?,
    val type: String?,
    val modified: Date?,
    val thumbnail: ImageModel?,
    val events: EventListModel?,
    val characters: CharacterListModel?
): Serializable