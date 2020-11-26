package com.example.moviecatalogue.core.utils

import android.util.Log
import com.example.moviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

object DummyData {

    private var gson = Gson()

    fun generateDummyDetailMovie(): MovieDetailEntity =
        gson.fromJson(jsonToSearchData("movie_detail.json"), MovieDetailEntity::class.java)

    fun generateDummyDetailTvShow(): TvShowDetailEntity =
        gson.fromJson(jsonToSearchData("tv_show_detail.json"), TvShowDetailEntity::class.java)

    private fun jsonToSearchData(fileSource: String): String? {
        var json: String? = null
        try {
            val input: InputStream? = this.javaClass.classLoader?.getResourceAsStream(fileSource)
            val size = input?.available()
            val buffer = size?.let { ByteArray(it) }
            input?.read(buffer)
            input?.close()
            json = buffer?.let { String(it, charset("UTF-8")) }

        } catch (ex: IOException) {
            Log.e("Dummy", ex.toString())
        }

        return json
    }
}