package me.lwb.json

import com.google.gson.*
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type
import java.util.*


@Suppress("UNUSED")
object Json {
    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type!!

    private val gson = Gson()

    private val gsonPretty = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

    inline fun <reified T> getObj(json: String?): T {
        return fromJson(json, genericType<T>())
    }

    inline fun <reified T> getList(string: String?): List<T> {
        return fromJson(string, genericType<List<T>>())
    }

    fun toPretty(string: String?): String {
        val jsonObject = JsonParser.parseString(string)
        return gsonPretty.toJson(jsonObject)
    }

    fun toPretty(obj: Any?): String {
        return gsonPretty.toJson(obj)
    }

    fun toJson(obj: Any?): String {
        return if (obj is String) obj else gson.toJson(obj)
    }

    fun <T> fromJson(string: String?, tClass: Class<out T>): T {
        return gson.fromJson(string, tClass)
    }

    fun <T> fromJson(string: String?, typeOfT: Type): T {
        return gson.fromJson(string, typeOfT)
    }

    fun isJsonParseException(e: Throwable) = e is JsonParseException

    fun obj(content: Builder.() -> Unit): JsonObject {
        val builder = Builder()
        builder.content()
        return builder.obj(content)
    }

    fun array(vararg args: Any): JsonArray {
        val array = JsonArray()
        args.forEach {
            when (it) {
                is Char -> array.add(it)
                is Number -> array.add(it)
                is String -> array.add(it)
                is Boolean -> array.add(it)
                is JsonElement -> array.add(it)
                else -> array.add(it.toString())
            }
        }
        return array
    }


    class Builder internal constructor() {
        private val objects = Stack<JsonObject>()
        private val current: JsonObject
            get() = objects.peek()

        fun array(vararg args: Any): JsonArray {
            return Json.array(*args)
        }

        fun obj(content: Builder.() -> Unit): JsonObject {
            val obj = JsonObject()
            objects.push(obj)
            this.content()
            objects.pop()
            return obj
        }

        infix fun String.to(value: Any) {
            when (value) {
                is Char -> current.addProperty(this, value)
                is Number -> current.addProperty(this, value)
                is String -> current.addProperty(this, value)
                is Boolean -> current.addProperty(this, value)
                is JsonElement -> current.add(this, value)
                else -> current.addProperty(this, value.toString())
            }
        }

        init {
            objects.add(JsonObject())
        }
    }

}