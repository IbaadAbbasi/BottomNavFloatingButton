package com.pha.lahore.state

sealed class State<T>{
    class Empty<T>: State<T>()
    class Loading<T> : State<T>()

    data class Success<T>(val data: T,val code: Int) : State<T>()

    data class Error<T>(val message: String,val code: Int) : State<T>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccessful(): Boolean = this is Success
    fun isFailed(): Boolean = this is Error
    companion object {

        /**
         * Returns [State.Loading] instance.
         */
        fun <T> loading() = Loading<T>()

        /**
         * Returns [State.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T,code: Int) =
            Success(data,code)

        /**
         * Returns [State.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(message: String,code: Int) =
            Error<T>(message,code)

        /**
         * Returns [State] from [ResponseAPI]
         */
        fun <T> fromResource(resource: ResponseAPI<T>): State<T> = when (resource) {
            is ResponseAPI.Success -> success(resource.data,resource.code)
            is ResponseAPI.Failed -> error(resource.message,resource.code)

        }

        fun <T> fromLocal(resource: ResponseAPI<T>): State<T> = when (resource) {
            is ResponseAPI.Success -> success(resource.data,resource.code)
            is ResponseAPI.Failed -> error(resource.message,resource.code)

        }


    }

}
