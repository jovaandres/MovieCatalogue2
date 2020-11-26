package com.example.moviecatalogue.core.utils

import android.content.Context
import androidx.core.content.edit

class SortPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "sort_pref"
        private const val MENU_POPULAR_MOVIE = "menu_popular_movie"
        private const val SORT_POPULAR_MOVIE = "sort_popular_movie"
        private const val MENU_POPULAR_TV = "menu_popular_tv"
        private const val SORT_POPULAR_TV = "sort_popular_tv"
        private const val MENU_FAVORITE_MOVIE = "menu_favorite_movie"
        private const val SORT_FAVORITE_MOVIE = "sort_favorite_movie"
        private const val MENU_FAVORITE_TV = "menu__favorite_tv"
        private const val SORT_FAVORITE_TV = "sort_favorite_tv"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setPrefPopularMovie(menuId: Int, sort: String) {
        preferences?.edit {
            putInt(MENU_POPULAR_MOVIE, menuId)
            putString(SORT_POPULAR_MOVIE, sort)
        }
    }
    fun setPrefPopularTv(menuId: Int, sort: String) {
        preferences?.edit {
            putInt(MENU_POPULAR_TV, menuId)
            putString(SORT_POPULAR_TV, sort)
        }
    }

    fun setPrefFavoriteMovie(menuId: Int, sort: String) {
        preferences?.edit {
            putInt(MENU_FAVORITE_MOVIE, menuId)
            putString(SORT_FAVORITE_MOVIE, sort)
        }
    }
    fun setPrefFavoriteTv(menuId: Int, sort: String) {
        preferences?.edit {
            putInt(MENU_FAVORITE_TV, menuId)
            putString(SORT_FAVORITE_TV, sort)
        }
    }

    fun getMenuPopularMovie(): Int = preferences.getInt(MENU_POPULAR_MOVIE, 0)
    fun getSortPopularMovie(): String? = preferences.getString(SORT_POPULAR_MOVIE, SortUtils.ALPHABET_ASC)

    fun getMenuPopularTV(): Int = preferences.getInt(MENU_POPULAR_TV, 0)
    fun getSortPopularTv(): String? = preferences.getString(SORT_POPULAR_TV, SortUtils.ALPHABET_ASC)

    fun getMenuFavoriteMovie(): Int = preferences.getInt(MENU_FAVORITE_MOVIE, 0)
    fun getSortFavoriteMovie(): String? = preferences.getString(SORT_FAVORITE_MOVIE, SortUtils.ALPHABET_ASC)

    fun getMenuFavoriteTv(): Int = preferences.getInt(MENU_FAVORITE_TV, 0)
    fun getSortFavoriteTv(): String? = preferences.getString(SORT_FAVORITE_TV, SortUtils.ALPHABET_ASC)
}