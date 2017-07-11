package com.android.marco.firstdecision.helpers

import java.io.*

/**
 * Created by gen on 11.07.15.

 */

class ListDataHelper {

    @Throws(IOException::class)
    fun serialize(obj: Serializable?): String {
        if (obj == null) return ""
        try {
            val serialObj = ByteArrayOutputStream()
            val objStream = ObjectOutputStream(serialObj)
            objStream.writeObject(obj)
            objStream.close()
            return encodeBytes(serialObj.toByteArray())
        } catch (e: Exception) {
            throw IOException()
        }

    }

    @Throws(IOException::class)
    fun deserialize(str: String?): Any? {
        if (str == null || str.isEmpty()) return null
        try {
            val serialObj = ByteArrayInputStream(decodeBytes(str))
            val objStream = ObjectInputStream(serialObj)
            return objStream.readObject()
        } catch (e: Exception) {
            throw IOException()
        }

    }

    fun encodeBytes(bytes: ByteArray): String {
        val strBuf = StringBuilder()
        for (i in bytes.indices) {
//            strBuf.append(((bytes[i] shr 4 and 0xF) + 'a'.toInt()).toChar())  todo
//            strBuf.append(((bytes[i] and 0xF) + 'a'.toInt()).toChar())
        }
        return strBuf.toString()
    }

    fun decodeBytes(str: String): ByteArray {
        val bytes = ByteArray(str.length / 2)
        var i = 0
        while (i < str.length) {
            var c = str[i]
            bytes[i / 2] = (c - 'a' shl 4).toByte()
            c = str[i + 1]
//            bytes[i / 2] += (c - 'a').toByte()  todo
            i += 2
        }
        return bytes
    }
}