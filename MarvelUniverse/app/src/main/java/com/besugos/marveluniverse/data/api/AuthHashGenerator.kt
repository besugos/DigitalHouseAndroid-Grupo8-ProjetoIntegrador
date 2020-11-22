package com.besugos.marveluniverse.data.api

import java.security.MessageDigest

class AuthHashGenerator {

    val timeStamp = System.currentTimeMillis().toString()

    fun generateHash (timeStamp: String, publicKey: String, privateKey: String ): String {

        val initialValue = timeStamp + privateKey + publicKey
        val md5Encoder = MessageDigest.getInstance("MD5")
        val encoderDigest = md5Encoder.digest(initialValue.toByteArray())
        return encoderDigest.fold ("", {str, it -> str + "%02x".format(it)})

    }
}