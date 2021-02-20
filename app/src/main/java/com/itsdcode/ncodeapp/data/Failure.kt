package com.itsdcode.ncodeapp.data

sealed class Failure(val errorMsg: String? = null) {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    object FileException : FeatureFailure()
    object RoomException : FeatureFailure()
    object TimeoutException : FeatureFailure()
}