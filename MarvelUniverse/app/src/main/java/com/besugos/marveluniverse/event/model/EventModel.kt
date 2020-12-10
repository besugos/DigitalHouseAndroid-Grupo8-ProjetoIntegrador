package com.besugos.marveluniverse.event.model

import com.besugos.marveluniverse.home.model.*
import java.io.Serializable
import java.util.*

data class EventModel (
    val id: Int?,
    val title: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<UrlModel>?,
    val modified: String?,
    val start: String?,
    val end: String?,
    val thumbnail: ImageModel?,
    val stories: StoryListModel?,
    val characters: CharacterListModel?,
    val next: EventSummaryModel?,
    val previous: EventSummaryModel?
): Serializable