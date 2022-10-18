package com.enraher.githublist.common

import retrofit2.Response


sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val errorMessage: String) : Resource<T>() {
        companion object {
            fun getError(response: Response<out Any>): String {
                val errorBody = response.errorBody()?.string()
                return errorBody ?: "something went wrong"
            }
        }
    }
}

sealed class Status {
    object Loading : Status()
    object Success : Status()
    class Error(val errorMessage: String) : Status()
}

