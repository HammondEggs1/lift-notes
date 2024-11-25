package com.example.liftnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentSecondBinding
import com.google.gson.Gson
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


object ManageStorage {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "workouts")

    data class LiftInfo(val liftName: String, val sets: Int, val weight: Int, val reps: Int) {
        override fun toString(): String {
            return " $sets sets ($weight lbs for $reps reps)"
        }
    }

    private val workout_key = stringPreferencesKey("workout_list")
    private val gson = Gson()

    // Save workout list to DataStore
    suspend fun saveWorkoutList(context: Context, workoutList: List<LiftInfo>) {
        val workoutJson = gson.toJson(workoutList)
        context.dataStore.edit { preferences ->
            preferences[workout_key] = workoutJson
        }
    }

    // Load workout list from DataStore
    suspend fun loadWorkoutList(context: Context): List<LiftInfo> {
        val preferences = context.dataStore.data.first()
        val workoutJson = preferences[workout_key] ?: return emptyList()
        return gson.fromJson(workoutJson, Array<LiftInfo>::class.java).toList()
    }
}