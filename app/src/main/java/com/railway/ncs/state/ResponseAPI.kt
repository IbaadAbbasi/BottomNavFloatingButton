package com.pha.lahore.state


sealed class ResponseAPI<T>{
    class Success<T>(val data:T,val code: Int) : ResponseAPI<T>()
    class Failed<T>(val message: String,val code: Int) : ResponseAPI<T>()
}

