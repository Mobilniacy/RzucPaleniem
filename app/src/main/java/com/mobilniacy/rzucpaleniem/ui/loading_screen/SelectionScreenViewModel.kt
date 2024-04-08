package com.mobilniacy.rzucpaleniem.ui.loading_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SelectionScreenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "To fragment selection screena"
    }
    val text: LiveData<String> = _text
}