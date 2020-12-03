package com.example.moviecatalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val RATING_DESC = "rating_desc"
    const val RATING_ASC = "rating_asc"
    const val ALPHABET_DESC = "alphabet_desc"
    const val ALPHABET_ASC = "alphabet_asc"
    const val RANDOM = "random"

    fun getSortedQuery(simpleQuery: String, filter: String): SimpleSQLiteQuery {
        val query: String =  when (filter) {
            RATING_DESC -> simpleQuery + "ORDER BY voteAverage DESC"
            RATING_ASC -> simpleQuery + "ORDER BY voteAverage ASC"
            ALPHABET_DESC -> simpleQuery + "ORDER BY title DESC"
            ALPHABET_ASC -> simpleQuery + "ORDER BY title ASC"
            RANDOM -> simpleQuery + "ORDER BY RANDOM()"
            else -> simpleQuery
        }
        return SimpleSQLiteQuery(query)
    }
}