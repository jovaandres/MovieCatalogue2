package com.example.moviecatalogue.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SortPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "sort_pref"
        private const val MENU_FAVORITE_MOVIE = "menu_favorite_movie"
        private const val SORT_FAVORITE_MOVIE = "sort_favorite_movie"
        private const val MENU_FAVORITE_TV = "menu_favorite_tv"
        private const val SORT_FAVORITE_TV = "sort_favorite_tv"
    }

    private val spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .build()

    private val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setPrefFavoriteMovie(menuId: Int, sort: String) {
        editor.putInt(MENU_FAVORITE_MOVIE, menuId)
            .putString(SORT_FAVORITE_MOVIE, sort)
            .commit()
    }

    fun setPrefFavoriteTv(menuId: Int, sort: String) {
        editor.putInt(MENU_FAVORITE_TV, menuId)
            .putString(SORT_FAVORITE_TV, sort)
            .commit()
    }

    fun getMenuFavoriteMovie(): Int = prefs.getInt(MENU_FAVORITE_MOVIE, 0)
    fun getSortFavoriteMovie(): String? =
        prefs.getString(SORT_FAVORITE_MOVIE, SortUtils.ALPHABET_ASC)

    fun getMenuFavoriteTv(): Int = prefs.getInt(MENU_FAVORITE_TV, 0)
    fun getSortFavoriteTv(): String? = prefs.getString(SORT_FAVORITE_TV, SortUtils.ALPHABET_ASC)
}