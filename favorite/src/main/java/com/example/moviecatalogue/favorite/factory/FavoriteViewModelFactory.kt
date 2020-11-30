package com.example.moviecatalogue.favorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.favorite.di.FeatureScope
import javax.inject.Inject
import javax.inject.Provider

@FeatureScope
class FavoriteViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw  IllegalArgumentException("Unknown model class ${modelClass.name}")
        return creator.get() as T
    }
}