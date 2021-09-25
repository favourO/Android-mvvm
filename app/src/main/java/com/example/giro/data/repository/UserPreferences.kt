package com.example.giro.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {

    private val mDataStore: DataStore<Preferences> = context.dataStore

    init {
        mDataStore
    }

    //To get token we create flow
    val authToken: Flow<String?>
    get() = mDataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }

    suspend fun clear() {
        mDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveAuthToken(authToken: String){
        mDataStore.edit { preference ->
            preference[KEY_AUTH] = authToken
        }
    }


    /**
     * store user data
     * refer to the data store and using edit
     * we can store values using the keys
     */
    // create some keys for storage
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")
        private val KEY_AUTH = stringPreferencesKey("us")
    }
}