//package com.besugos.marveluniverse.home.datamockup
//
//import com.besugos.marveluniverse.event.model.EventModel
//import com.besugos.marveluniverse.home.model.*
//import java.util.*
//
//class EventDataBase {
//
//    companion object {
//        val events = listOf(
//            EventModel(
//                1,
//                "Age of Apocalypse",
//                "Age of Apocalypse is a X-Men event",
//                "http://gateway.marvel.com/v1/public/characters/1011266",
//                UrlModel(
//                    "detail",
//                    "http://marvel.com/characters/2902/adam_destine?utm_campaign=apiRef&utm_source=001ac6c73378bbfff488a36141458af2"
//                ), Date(), Date(), Date(), ImageModel(
//                    "http://i.annihil.us/u/prod/marvel/i/mg/3/80/4c00358ec7548",
//                    "jpg"
//                ), StoryListModel(
//                    2,
//                    2,
//                    "http://gateway.marvel.com/v1/public/characters/1010903/stories",
//                    listOf(
//                        StorySummaryModel(
//                            "http://gateway.marvel.com/v1/public/stories/26280",
//                            "X-Men: Alpha (1994) #1",
//                            "cover"
//                        ),
//                        StorySummaryModel(
//                            "http://gateway.marvel.com/v1/public/stories/38448",
//                            "X-Facts",
//                            "cover"
//                        )
//                    )
//                ),
//                CharacterListModel(
//                    3,
//                    3,
//                    "http://gateway.marvel.com/v1/public/events/1/characters",
//                    listOf<CharacterSummaryModel>(
//                        CharacterSummaryModel(
//                            "http://gateway.marvel.com/v1/public/characters/1009149",
//                            "Abyss", "Secondary"
//                        ),
//                        CharacterSummaryModel(
//                            "http://gateway.marvel.com/v1/public/characters/1010354",
//                            "Adam Warlock", "Main"
//                        ),
//                        CharacterSummaryModel(
//                            "http://gateway.marvel.com/v1/public/characters/1009150",
//                            "Agent Zero", "Secondary"
//                        )
//                    )
//                ),
//                EventSummaryModel(
//                    "http://gateway.marvel.com/v1/public/events/227",
//                    "Age of Apocalypse"
//                ),
//                EventSummaryModel(
//                    "http://gateway.marvel.com/v1/public/events/227",
//                    "Age of Apocalypse"
//                )
//            )
//        )
//    }
//}