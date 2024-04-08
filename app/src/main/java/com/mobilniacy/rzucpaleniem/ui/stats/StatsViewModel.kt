package com.mobilniacy.rzucpaleniem.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "To fragment statystyk"
    }
    val text: LiveData<String> = _text
}