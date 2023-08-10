package com.bup.foodyv2.data.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bup.foodyv2.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.bup.foodyv2.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.bup.foodyv2.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.bup.foodyv2.util.Constants.Companion.PREFERENCES_NAME
import com.bup.foodyv2.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.bup.foodyv2.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.bup.foodyv2.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.bup.foodyv2.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val  Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context){

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val datastore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        datastore.edit {preferences ->
        preferences[PreferenceKeys.selectedMealType] = mealType
        preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
        preferences[PreferenceKeys.selectedDietType] = dietType
        preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId

        }
    }

    suspend fun saveBackOnline(backOnline : Boolean) {
        datastore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline

        }

}
data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId:Int
)