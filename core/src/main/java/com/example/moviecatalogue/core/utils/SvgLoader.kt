package com.example.moviecatalogue.core.utils

import android.content.Context
import android.widget.ImageView
import com.pixplicity.sharp.Sharp
import okhttp3.*
import java.io.IOException

object SvgLoader {

    fun fetchSvg(context: Context, url: String, target: ImageView) {
        val httpClient = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
            .build()
        val request = Request.Builder().url(url).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                val stream = response.body()?.byteStream()
                Sharp.loadInputStream(stream).into(target)
                stream?.close()
            }
        })
    }
}