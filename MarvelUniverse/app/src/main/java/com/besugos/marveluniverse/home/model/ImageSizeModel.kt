package com.besugos.marveluniverse.home.model

enum class ImageSizeModel (private val size: String) {
    /** 50x75px  */
    PORTRAIT_SMALL("portrait_small"),
    /** 100x150px  */
    PORTRAIT_MEDIUM("portrait_medium"),
    /** 150x225px  */
    PORTRAIT_XLARGE("portrait_xlarge"),
    /** 168x252px  */
    PORTRAIT_FANTASTIC("portrait_fantastic"),
    /** 300x450px  */
    PORTRAIT_UNCANNY("portrait_uncanny"),
    /** 216x324px  */
    PORTRAIT_INCREDIBLE("portrait_incredible"),
    /** 65x45px  */
    STANDARD_SMALL("standard_small"),
    /** 100x100px  */
    STANDARD_MEDIUM("standard_medium"),
    /** 140x140px  */
    STANDARD_LARGE("standard_large"),
    /** 200x200px  */
    STANDARD_XLARGE("standard_xlarge"),
    /** 250x250px  */
    STANDARD_FANTASTIC("standard_fantastic"),
    /** 180x180px  */
    STANDARD_AMAZING("standard_amazing"),
    /** 120x90px  */
    LANDSCAPE_SMALL("landscape_small"),
    /** 175x130px  */
    LANDSCAPE_MEDIUM("landscape_medium"),
    /** 190x140px  */
    LANDSCAPE_LARGE("landscape_large"),
    /** 270x200px  */
    LANDSCAPE_XLARGE("landscape_xlarge"),
    /** 250x156px  */
    LANDSCAPE_AMAZING("landscape_amazing"),
    /** 464x261px  */
    LANDSCAPE_INCREDIBLE("landscape_incredible"),
    DETAIL("detail"),
    FULLSIZE("fullsize")
}