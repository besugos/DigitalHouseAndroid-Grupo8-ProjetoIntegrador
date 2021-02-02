package com.besugos.marveluniverse.home.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CharacterSummaryModel (
    val resourceURI: String?,
    val name: String?,
    val role: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterSummaryModel> {
        override fun createFromParcel(parcel: Parcel): CharacterSummaryModel {
            return CharacterSummaryModel(parcel)
        }

        override fun newArray(size: Int): Array<CharacterSummaryModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class StorySummaryModel (
    val resourceURI: String?,
    val name: String?,
    val type: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StorySummaryModel> {
        override fun createFromParcel(parcel: Parcel): StorySummaryModel {
            return StorySummaryModel(parcel)
        }

        override fun newArray(size: Int): Array<StorySummaryModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class EventSummaryModel (
    val resourceURI: String?,
    val name: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventSummaryModel> {
        override fun createFromParcel(parcel: Parcel): EventSummaryModel {
            return EventSummaryModel(parcel)
        }

        override fun newArray(size: Int): Array<EventSummaryModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class ComicSummaryModel (
    val resourceURI: String?,
    val name: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicSummaryModel> {
        override fun createFromParcel(parcel: Parcel): ComicSummaryModel {
            return ComicSummaryModel(parcel)
        }

        override fun newArray(size: Int): Array<ComicSummaryModel?> {
            return arrayOfNulls(size)
        }
    }
}