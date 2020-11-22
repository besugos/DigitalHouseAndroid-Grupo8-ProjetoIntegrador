package com.besugos.marveluniverse.home.model

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
)

data class StoryListModel (
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<StorySummaryModel>
)