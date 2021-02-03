package com.besugos.marveluniverse.home.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class
CharacterListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CharacterSummaryModel>
): Parcelable

@Parcelize
data class EventListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<EventSummaryModel>
): Parcelable

@Parcelize
data class ComicListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ComicSummaryModel>
): Parcelable

@Parcelize
data class StoryListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<StorySummaryModel>
): Parcelable