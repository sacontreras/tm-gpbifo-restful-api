package com.sacontreras.spatial.geopackage.bundle.app.ws.shared

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class Utils {
    private val RANDOM: SecureRandom = SecureRandom()
    private val ALPHABET: String

    init {
        val sb_random = StringBuilder()
        // "0" through "9"
        for (i in 48..56)
            sb_random.append(i.toChar())
        // "A" through "Z"
        for (i in 65..90)
            sb_random.append(i.toChar())
        // "a" through "z"
        for (i in 97..122)
            sb_random.append(i.toChar())
        ALPHABET = sb_random.toString()
    }

    fun generateRandomString(length: Int): String {
        val sb_random = StringBuilder(length)
        for (i in 0 until length)
            sb_random.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)])
        return sb_random.toString()
    }
}