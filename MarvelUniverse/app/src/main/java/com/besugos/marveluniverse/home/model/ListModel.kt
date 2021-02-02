package com.besugos.marveluniverse.home.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CharacterListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CharacterSummaryModel>
)

data class EventListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<EventSummaryModel>
): Serializable

data class ComicListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ComicSummaryModel>
): Serializable

data class StoryListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<StorySummaryModel>
)