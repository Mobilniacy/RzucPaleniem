package com.mobilniacy.rzucpaleniem.ui.title_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleScreenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "To fragment ekranu tytułowego"
    }
    val text: LiveData<String> = _text
}