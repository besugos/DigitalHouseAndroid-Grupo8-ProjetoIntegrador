package com.besugos.marveluniverse.home.model

import java.util.*

data class EventModel (
    val id: Int,
    val title: String,
    val description: String,
    val resourceURI: String,
    val urls: UrlModel,
    val modified: Date,
    val start: Date,
    val end: Date,
    val thumbnail: ImageModel,
    val stories: StoryListModel,
    val characters: CharacterListModel
//    val next: EventSummaryModel,
//    val previous: EventSummaryModel
)