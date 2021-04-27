package com.example.movies.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.movies.model.Images
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConfigurationPreferences(var context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Configuration")

    val imageConfig:Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_IMAGES]
        }

    suspend fun saveImageConfiguration(imageConfig: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_IMAGES] = imageConfig
        }
    }

    companion object {
        private val KEY_IMAGES = stringPreferencesKey("Images_config")
    }
}