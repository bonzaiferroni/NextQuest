package com.glowsoft.nextquest.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStreamWriter

fun objectToJson(obj: Any): String {
    val gson = Gson()
    return gson.toJson(obj)
}

fun Context.objectToFile(obj: Any, filename: String, path: String? = null) {
    val json = objectToJson(obj)
    this.saveJsonToFile(filename = filename, json = json, path = path)
}

fun Context.saveJsonToFile(filename: String, json: String, path: String? = null) {
    val fileStream = if (path != null) {
        val dir = File(this.filesDir, path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        FileOutputStream(File(dir, filename))
    } else {
        FileOutputStream(filename)
    }
    val writer = OutputStreamWriter(fileStream)
    writer.use { it.write(json) }
}

fun Context.loadJsonFromFile(filename: String, path: String? = null): String {
    val fileStream = if (path != null) {
        val dir = File(this.filesDir, path)
        if (!dir.exists()) {
            throw Exception("Directory does not exist: $path")
        }
        FileInputStream(File(dir, filename))
    } else {
        FileInputStream(filename)
    }
    fileStream.bufferedReader().use { return it.readText() }
}

inline fun <reified T> jsonToObject(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}