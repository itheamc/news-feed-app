package com.itheamc.newsfeedappnchl.data.models

sealed class ApiResult<out T : Any> {

    object Loading: ApiResult<Nothing>()
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            else -> "Loading"
        }
    }

}
