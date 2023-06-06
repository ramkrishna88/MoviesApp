package com.example.moviesapp.utils

sealed class UIState<out T>{
    data class LOADING(val isLoading:Boolean) : UIState<Nothing>()
    data class ERROR(val error: String?) :UIState<Nothing>()
    data class SUCCESS<T>(val data: T) : UIState<T>()
}