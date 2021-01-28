package com.besugos.marveluniverse.home.model

data class CharacterSummaryModel (
    val resourceURI: String,
    val name: String,
    val role: String
)

data class StorySummaryModel (
    val resourceURI: String,
    val name: String,
    val type: String
)

data class EventSummaryModel (
    val resourceURI: String,
    val name: String
)

data class ComicSummaryModel (
    val resourceURI: String,
    val name: String
)