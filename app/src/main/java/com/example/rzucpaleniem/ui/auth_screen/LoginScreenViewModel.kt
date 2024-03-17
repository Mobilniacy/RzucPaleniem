package com.example.rzucpaleniem.ui.auth_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginScreenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "To fragment logowania"
    }
    val text: LiveData<String> = _text
}