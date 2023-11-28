package com.glowsoft.nextquest.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.OutputStreamWriter

fun objectToJson(obj: Any): String {
    val gson = Gson()
    return gson.toJson(obj)
}

fun Context.saveJsonToFile(filename: String, json: String) {
    val fileOutputStream = this.openFileOutput(filename, Context.MODE_PRIVATE)
    val writer = OutputStreamWriter(fileOutputStream)
    writer.use { it.write(json) }
}

fun Context.objectToFile(obj: Any, filename: String) {
    val json = objectToJson(obj)
    this.saveJsonToFile(filename = filename, json = json)
}

fun Context.loadJsonFromFile(filename: String): String {
    return this.openFileInput(filename).bufferedReader().use { it.readText() }
}

inline fun <reified T> jsonToObject(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}