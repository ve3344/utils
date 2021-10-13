package me.lwb.json

import com.alibaba.fastjson.*
import java.lang.reflect.Type
import java.util.*

@Suppress("NOTHING_TO_INLINE")
object Json {
    inline fun <reified T> genericType() = object : TypeReference<T>(){}.type!!

    inline fun <reified T> getObj(json: String?): T {
        return JSON.parseObject(json, genericType<T>())
    }

    inline fun <reified T> getList(json: String?): List<T> {
        return JSON.parseArray(json, T::class.java)
    }

    inline fun toPretty(string: String?): String {
        return JSON.toJSONString(string, true)
    }

    inline fun toPretty(obj: Any?): String {
        return JSON.toJSONString(obj, true)
    }

    inline fun toJson(obj: Any?): String {
        return JSON.toJSONString(obj)
    }
    fun <T> fromJson(string: String?, tClass: Class<out T>): T {
        return JSON.parseObject(string, tClass)
    }

    fun <T> fromJson(string: String?, typeOfT: Type): T {
        return JSON.parseObject(string, typeOfT)
    }

    fun isJsonParseException(e: Throwable) = e is JSONException

    fun obj(content: Builder.() -> Unit): JsonObject {
        val builder = Builder()
        builder.content()
        return builder.obj(content)
    }

    fun array(vararg args: Any): JsonArray {
        val array = JsonArray()
        array.addAll(args)
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
            current[this] = value
        }

        init {
            objects.add(JsonObject())
        }
    }
}

typealias JsonArray =  JSONArray
typealias JsonObject =  JSONObject