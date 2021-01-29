package com.besugos.marveluniverse.event.model

import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.data.model.UrlModel
import com.besugos.marveluniverse.home.model.CharacterListModel
import com.besugos.marveluniverse.home.model.ComicListModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.besugos.marveluniverse.home.model.StoryListModel
import java.io.Serializable

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
    val comics: ComicListModel?,
    val next: EventSummaryModel?,
    val previous: EventSummaryModel?
): Serializable