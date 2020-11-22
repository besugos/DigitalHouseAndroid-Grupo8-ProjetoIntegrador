package com.besugos.marveluniverse.data.model

data class ResponseModel<T> (
    val info: PageInfoModel,
    val data: DataModel<T>
)