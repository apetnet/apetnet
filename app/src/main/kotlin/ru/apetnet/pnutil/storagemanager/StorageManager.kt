package ru.apetnet.pnutil.storagemanager

import com.google.gson.Gson
import ru.apetnet.pnutil.storagemanager.exception.StorageException
import ru.apetnet.pnutil.storagemanager.model.StorageData
import java.io.File

class StorageManager {
    private val gson = Gson()

    fun save(file: File, data: StorageData) {
        gson.toJson(data)?.let {
            file.writeText(it)
        } ?: throw StorageException()
    }

    fun read(file: File): StorageData {
        val json = file.readText()
        return gson.fromJson(json, StorageData::class.java) ?: throw StorageException()
    }

    companion object {
        const val VERSION = 2
    }
}